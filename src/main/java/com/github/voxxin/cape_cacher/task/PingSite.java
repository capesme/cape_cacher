package com.github.voxxin.cape_cacher.task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class PingSite {

    public void pingCapesme(String playerUUID) throws IOException {

        URL url = new URL("https://capes.me/capes?user=" + playerUUID);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        reader.close();
    }
}
