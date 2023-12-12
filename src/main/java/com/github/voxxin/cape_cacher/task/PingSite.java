package com.github.voxxin.cape_cacher.task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.*;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import static com.github.voxxin.cape_cacher.client.CapeCacher.MODVERSION;

public class PingSite {
    // Use a connection pool to manage connections to the server
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER =
            new PoolingHttpClientConnectionManager();
    private static final CloseableHttpClient HTTP_CLIENT =
            HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();

    // Pre-construct the URL as a URL object
    private static final URL URL;
    private static final URL API_URL;

    private static final URL API_FIND_URL;

    static {
        try {
            URL = new URL("https://capes.me/api/user/ping/");
            API_URL = new URL("https://capes.me/api/capes");
            API_FIND_URL = new URL("https://capes.me/api/found/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    // Cache the results of requests locally
    private static final ConcurrentMap<String, Boolean> CACHE = new ConcurrentHashMap<>();

    public static CompletableFuture<Void> pingCapesmeAsync(String playerUUID) {
        // Check the cache for a previous result
        Boolean cachedResult = CACHE.get(playerUUID);
        if (cachedResult != null) {
            return CompletableFuture.completedFuture(null);
        }

        // Return a CompletableFuture that performs the request asynchronously
        return CompletableFuture.runAsync(() -> {
            try {
                // Send a HEAD request to the server
                HttpHead request = new HttpHead(URL + playerUUID);
                request.addHeader("Cape-Cacher-Fabric", "Version: " + MODVERSION);
                HTTP_CLIENT.execute(request);

                // Cache the result
                CACHE.put(playerUUID, true);
            } catch (IOException e) {
                // Handle the exception as appropriate
            }
        });
    }

    public static JsonArray fetchCapes() throws IOException, JsonSyntaxException {
        try {
            String jsonString = new Scanner(API_URL.openStream(), "UTF-8").useDelimiter("\\A").next();
            return new Gson().fromJson(jsonString, JsonArray.class);
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompletableFuture<Void> pingFoundNewAsync(String thisUUID, String capeTextureID) {
        // Check the cache for a previous result
        Boolean cachedResult = CACHE.get(capeTextureID);
        if (cachedResult != null) {
            return CompletableFuture.completedFuture(null);
        }

        // Return a CompletableFuture that performs the request asynchronously
        return CompletableFuture.runAsync(() -> {
            try {
                // Send a HEAD request to the server
                HttpHead request = new HttpHead(API_FIND_URL + thisUUID + "/" + capeTextureID);
                request.addHeader("Cape-Cacher-Fabric", "Version: " + MODVERSION);
                HTTP_CLIENT.execute(request);

                // Cache the result
                CACHE.put(capeTextureID, true);
            } catch (IOException e) {
                // Handle the exception as appropriate
            }
        });
    }
}


