package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.Manager;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.SendUserMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import net.minecraft.client.Minecraft;

//? if >=1.20.2 {
/*import com.mojang.authlib.minecraft.MinecraftProfileTextures;
import com.mojang.authlib.yggdrasil.ProfileResult; 
*///?} else if >=1.20 {
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
//?}

//? if >=1.20.2 {
 /*import net.minecraft.client.resources.PlayerSkin; 
*///?}

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

//? if 1.20.1 {
@Mixin(PlayerInfo.class)
//?} else {
/*@Mixin(SkinManager.class)
*///?}

public abstract class PlayerSkinProviderMixin {
    @Unique
    private static final ExecutorService ASYNC_EXECUTOR = Executors.newFixedThreadPool(2);

    //? if 1.20.1 {
    @Shadow
    public abstract GameProfile getProfile();

    @Inject(at = @At("HEAD"), method = "method_2956")
    private void loadSkin(MinecraftProfileTexture.Type type, ResourceLocation id, MinecraftProfileTexture texture, CallbackInfo ci) {
        if (type != MinecraftProfileTexture.Type.CAPE || texture == null || texture.getUrl() == null) {
            return;
        }

        CompletableFuture.supplyAsync(() -> {
            try {

                GameProfile fakeProfile = new GameProfile(getProfile().getId(), null);
                GameProfile returnProfile = Minecraft.getInstance().getMinecraftSessionService().fillProfileProperties(fakeProfile, true);
                if (returnProfile == null || returnProfile == fakeProfile) return null;
                return getProfile();
            } catch (Exception ignored) {
                return null;
            }
        }, ASYNC_EXECUTOR).thenComposeAsync(profile -> {
            if (profile == null) return CompletableFuture.completedFuture(null);

            final String textureUrl = texture.getUrl();
            final String userIdString = profile.getId().toString();

            AtomicBoolean wasUpdated = new AtomicBoolean(false);
            StaticValues.userCapeMap.compute(userIdString, (k, v) -> {
                if (v == null || !v.equals(textureUrl)) {
                    wasUpdated.set(true);
                    return textureUrl;
                }
                return v;
            });
            boolean shouldUpdate = wasUpdated.get();

            if (!shouldUpdate) return CompletableFuture.completedFuture(null);

            return CompletableFuture.supplyAsync(() -> IdentifyCapeType.CapeIdentifier(textureUrl), ASYNC_EXECUTOR)
                    .thenComposeAsync(capeInfo -> {
                        JsonObject base = capeInfo.getAsJsonObject("base");

                        return PingSite.pingCapesmeAsync(userIdString)
                                .exceptionally(e -> {
                                    CapeCacher.LOGGER.error("Ping failed for {}: {}", userIdString, e.getMessage());
                                    return null;
                                })
                                .thenApplyAsync(v -> {
                                    handleCapeNotifications(profile, capeInfo, base);
                                    return null;
                                }, ASYNC_EXECUTOR);
                    });
        }, ASYNC_EXECUTOR).handleAsync((result, ex) -> {
            if (ex != null) {
                CapeCacher.LOGGER.error("Async cape processing failed", ex);
            }
            return result;
        }, ASYNC_EXECUTOR);
    }
    //?} else {
     /*@Inject(at = @At("TAIL"), method = "registerTextures")
    private void loadSkin(UUID uuid, MinecraftProfileTextures textures, CallbackInfoReturnable<CompletableFuture<PlayerSkin>> cir) {
        if (textures.cape() == null || textures.cape().getUrl() == null) {
            return;
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                ProfileResult profileResult = Minecraft.getInstance().getMinecraftSessionService().fetchProfile(uuid, true);
                return profileResult != null ? profileResult.profile() : null;
            } catch (Exception e) {
                CapeCacher.LOGGER.error("Failed to fetch profile for {}", uuid, e);
                return null;
            }
        }, ASYNC_EXECUTOR).thenComposeAsync(profile -> {
            if (profile == null) return CompletableFuture.completedFuture(null);

            final String textureUrl = textures.cape().getUrl();
            final String userIdString = profile.getId().toString();

            AtomicBoolean wasUpdated = new AtomicBoolean(false);
            StaticValues.userCapeMap.compute(userIdString, (k, v) -> {
                if (v == null || !v.equals(textureUrl)) {
                    wasUpdated.set(true);
                    return textureUrl;
                }
                return v;
            });
            boolean shouldUpdate = wasUpdated.get();

            if (!shouldUpdate) return CompletableFuture.completedFuture(null);

            return CompletableFuture.supplyAsync(() -> IdentifyCapeType.CapeIdentifier(textureUrl), ASYNC_EXECUTOR)
                    .thenComposeAsync(capeInfo -> {
                        JsonObject base = capeInfo.getAsJsonObject("base");

                        return PingSite.pingCapesmeAsync(userIdString)
                                .exceptionally(e -> {
                                    CapeCacher.LOGGER.error("Ping failed for {}: {}", userIdString, e.getMessage());
                                    return null;
                                })
                                .thenApplyAsync(v -> {
                                    handleCapeNotifications(profile, capeInfo, base);
                                    return null;
                                }, ASYNC_EXECUTOR);
                    });
        }, ASYNC_EXECUTOR).handleAsync((result, ex) -> {
            if (ex != null) {
                CapeCacher.LOGGER.error("Async cape processing failed", ex);
            }
            return result;
        }, ASYNC_EXECUTOR);
    } 
    *///?}

    @Unique
    private void handleCapeNotifications(GameProfile profile, JsonObject capeInfo, JsonObject base) {
        if (isSelfProfile(profile)) return;

        if (shouldSkipNotification(capeInfo)) return;

        logCapeDiscovery(profile, base);

        Minecraft.getInstance().execute(() -> {
            Component foundCape = createCapeComponent(capeInfo, base);
            SendUserMessage.sendMessage(profile.getName(), foundCape);
        });
    }

    @Unique
    private boolean isSelfProfile(GameProfile profile) {
        String profileId = profile.getId().toString().replaceAll("-", "");
        String playerId = Minecraft.getInstance().player != null ?
                Minecraft.getInstance().player.getUUID().toString().replaceAll("-", "") :
                "";
        boolean isSelf = profileId.equals(playerId);

        return isSelf && !Manager.HANDLER.instance().notifyWhenSelf;
    }

    @Unique
    private boolean shouldSkipNotification(JsonObject capeInfo) {
        JsonElement notifyFound = capeInfo.get("notify_when_found");
        return notifyFound != null &&
                notifyFound.isJsonPrimitive() &&
                !notifyFound.getAsBoolean();
    }

    @Unique
    private void logCapeDiscovery(GameProfile profile, JsonObject base) {
        if (Manager.HANDLER.instance().notifyInConsole) {
            CapeCacher.LOGGER.info("Discovered cape for {} ({}): {}",
                    profile.getName(),
                    profile.getId(),
                    base.get("title").getAsString()
            );
        }
    }

    @Unique
    private Component createCapeComponent(JsonObject capeInfo, JsonObject base) {
        Style capeStyle = Style.EMPTY
                .withColor(TextColor.fromRgb(capeInfo.get("colour").getAsInt()));

        System.out.println(addClickEvent(capeStyle, base.get("url").getAsString()));

        return Component.literal("")
                .append(base.get("title").getAsString())
                .withStyle(addClickEvent(capeStyle, base.get("url").getAsString()));
    }

    @Unique
    private Style addClickEvent(Style style, String url) {
        //? if >=1.21.5 {
        /*style = style.withClickEvent(new ClickEvent.OpenUrl(URI.create(url)));
        *///?} else if >=1.20 {
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        //?}

        return style;
    }
}