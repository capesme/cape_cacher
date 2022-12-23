package com.github.voxxin.cape_cacher.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class PingSite {
    // Use a connection pool to manage connections to the server
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER =
            new PoolingHttpClientConnectionManager();
    private static final CloseableHttpClient HTTP_CLIENT =
            HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).build();

    // Pre-construct the URL as a URL object
    private static final URL URL;

    static {
        try {
            URL = new URL("https://capes.me/capes?user=");
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
                HTTP_CLIENT.execute(request);

                // Cache the result
                CACHE.put(playerUUID, true);
            } catch (IOException e) {
                // Handle the exception as appropriate
            }
        });
    }
}


