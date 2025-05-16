package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.task.util.ResourceLocationHandler;
import com.google.gson.*;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.resources.ResourceLocation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.*;

import static com.github.voxxin.cape_cacher.client.CapeCacher.LOGGER;
import static com.github.voxxin.cape_cacher.client.CapeCacher.MODID;

public class PingSite {
    private static final int MAX_THREADS = 4;
    private static final ExecutorService ASYNC_EXECUTOR = Executors.newFixedThreadPool(MAX_THREADS);
    private static final ScheduledExecutorService CACHE_CLEANER = Executors.newSingleThreadScheduledExecutor();

    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setConnectionManager(CONNECTION_MANAGER)
            .build();

    private static final URL API_URL, API_FIND_URL, MOJANG_SESSION_SERVERS_URL, LIVZMC_SKIN_IMAGE_URL;

    static {
        try {
            API_URL = new URI("https://capes.me/api/capes").toURL();
            API_FIND_URL = new URI("https://capes.me/").toURL();
            MOJANG_SESSION_SERVERS_URL = new URI("https://sessionserver.mojang.com/session/minecraft/profile/").toURL();
            LIVZMC_SKIN_IMAGE_URL = new URI("https://livzmc.net/api/v1/capes_me/skin-cape/image.png").toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Failed to initialize URLs", e);
        }
    }

    private static final ConcurrentMap<String, Boolean> REQUEST_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, CompletableFuture<Optional<NativeImage>>> IMAGE_CACHE = new ConcurrentHashMap<>();

    static {
        CACHE_CLEANER.scheduleAtFixedRate(REQUEST_CACHE::clear, 1, 1, TimeUnit.HOURS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ASYNC_EXECUTOR.shutdown();
            CACHE_CLEANER.shutdown();
        }));
    }

    public static CompletableFuture<Void> pingCapesmeAsync(String playerUUID) {
        if (REQUEST_CACHE.containsKey(playerUUID)) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> {
            try {
                String formattedUUID = playerUUID.replace("-", "");

                String apiPath = API_FIND_URL.toString() + formattedUUID;
                URI requestUri = new URI(apiPath);

                HttpGet request = new HttpGet(requestUri);
                request.addHeader("User-Agent", "CapeCacher-Fabric/" + CapeCacher.MODVERSION);

                LOGGER.debug("Requesting cape for UUID: {}", formattedUUID);

                try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        REQUEST_CACHE.put(playerUUID, true);
                    } else {
                        LOGGER.warn("Ping failed for {}: HTTP {}", formattedUUID, status);
                    }
                } catch (IOException e) {
                    LOGGER.error("Ping error for {}", formattedUUID, e);
                }
            } catch (URISyntaxException e) {
                LOGGER.error("Invalid URI for UUID: {}", playerUUID, e);
            }
        }, ASYNC_EXECUTOR);
    }

    public static CompletableFuture<JsonArray> fetchCapesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try (InputStream stream = API_URL.openStream()) {
                String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                return JsonParser.parseString(json).getAsJsonArray();
            } catch (IOException | JsonSyntaxException e) {
                LOGGER.error("Failed to fetch capes", e);
                return new JsonArray();
            }
        }, ASYNC_EXECUTOR);
    }

    public static void cacheBaseCapeImages() {
        if (StaticValues.capesJsonObject == null) return;

        for (JsonElement element : StaticValues.capesJsonObject) {
            JsonObject cape = element.getAsJsonObject();
            String type = cape.get("type").getAsString().toLowerCase();
            String url = cape.get("url").getAsString().replace("http://textures.minecraft.net/texture/", "");

            IMAGE_CACHE.computeIfAbsent(type, k ->
                    loadCapeImageAsync(url, type)
                            .thenApply(imageOpt -> {
                                imageOpt.ifPresent(image -> registerTexture(type, image));
                                return imageOpt;
                            })
                            .exceptionally(e -> {
                                LOGGER.error("Failed to load cape image for {}", type, e);
                                return Optional.empty();
                            })
            );
        }
    }

    private static CompletableFuture<Optional<NativeImage>> loadCapeImageAsync(String capeHash, String type) {
        return fetchUserSkinAsync().thenComposeAsync(userSkin -> {
            try {
                URI imageUri = buildImageUri(userSkin, capeHash);
                return downloadImageAsync(imageUri);
            } catch (Exception e) {
                return CompletableFuture.failedFuture(e);
            }
        }, ASYNC_EXECUTOR);
    }

    private static CompletableFuture<Optional<NativeImage>> downloadImageAsync(URI uri) {
        return CompletableFuture.supplyAsync(() -> {
            try (InputStream stream = uri.toURL().openStream()) {
                byte[] data = stream.readAllBytes();
                NativeImage image = NativeImage.read(data);
                return Optional.of(image);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }, ASYNC_EXECUTOR);
    }

    private static CompletableFuture<JsonObject> fetchUserSkinAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String uuid = Optional.of(Minecraft.getInstance().getUser())
                        .map(user -> user.getProfileId().toString().replace("-", ""))
                        .orElseThrow(() -> new IllegalStateException("User not logged in"));

                URI profileUri = new URI(MOJANG_SESSION_SERVERS_URL + uuid);
                String json = new String(profileUri.toURL().openStream().readAllBytes(), StandardCharsets.UTF_8);
                JsonObject properties = JsonParser.parseString(json).getAsJsonObject()
                        .getAsJsonArray("properties").get(0).getAsJsonObject();

                String decoded = new String(Base64.getDecoder().decode(properties.get("value").getAsString()));
                return JsonParser.parseString(decoded).getAsJsonObject()
                        .getAsJsonObject("textures").getAsJsonObject("SKIN");
            } catch (Exception e) {
                throw new CompletionException("Failed to fetch user skin", e);
            }
        }, ASYNC_EXECUTOR);
    }

    private static URI buildImageUri(JsonObject userSkin, String capeHash) throws URISyntaxException {
        String skinModel = userSkin.getAsJsonObject("metadata").get("model").getAsString();
        String skinHash = userSkin.get("url").getAsString().replace("http://textures.minecraft.net/texture/", "");

        return new URIBuilder(LIVZMC_SKIN_IMAGE_URL.toURI())
                .addParameter("skin_model", skinModel)
                .addParameter("skin_hash", skinHash)
                .addParameter("cape_hash", capeHash)
                .build();
    }

    public static void registerTexture(String type, NativeImage image) {
        Minecraft.getInstance().execute(() -> {
            ResourceLocation location = ResourceLocationHandler.make(MODID, type.toLowerCase());

            try {
                DynamicTexture texture = null;

                //? if >=1.21.5 {
                /*texture = new DynamicTexture(location::toString, image);
                *///?} else if >=1.20 {
                texture = new DynamicTexture(image);
                //?}

                Minecraft.getInstance().getTextureManager().register(location, texture);
            } catch (Exception e) {
                LOGGER.error("Failed to register texture {}", location, e);
            }
        });
    }
}