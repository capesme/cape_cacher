package com.github.voxxin.cape_cacher.task.util;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class CustomJsonReader {
    public static ArrayList<CapesObject> getCapes() {
        JsonArray capesJsonArray = StaticValues.capesJsonObject;
        ArrayList<CapesObject> capes = new ArrayList<>();

        for (int i = 0; i < capesJsonArray.size(); i++) {
            JsonObject capeJsonObject = capesJsonArray.get(i).getAsJsonObject();
            String capeURL = capeJsonObject.get("url").getAsString();
            String capeTitle = capeJsonObject.get("title").getAsString();
            String capeClass = capeJsonObject.get("class").getAsString();

            JsonArray altsObject = new JsonArray();
            if (capeJsonObject.has("alts"))
                altsObject = capeJsonObject.get("alts").getAsJsonArray();

            ArrayList<String> alts = new ArrayList<>();

            for (int j = 0; j < altsObject.size(); j++) {
                alts.add(altsObject.get(j).getAsString());
            }

            capes.add(new CapesObject(capeURL, capeClass, 0, capeTitle, alts));
        }

        return capes;
    }
}
