package com.github.voxxin.cape_cacher.task.util;

import com.github.voxxin.api.config.option.*;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class CustomJsonReader {
    public static ArrayList<ConfigOption> getCapes() {
        JsonArray capesJsonArray = StaticValues.capesJsonObject;
        ArrayList<ConfigOption> capesOptions = new ArrayList<>();

        if (capesJsonArray == null) return capesOptions;
        for (int i = 0; i < capesJsonArray.size(); i++) {
            JsonObject capeJsonObject = capesJsonArray.get(i).getAsJsonObject();
            String capeClass = capeJsonObject.get("class").getAsString();
            ConfigOption.Builder cape = new ConfigOption.Builder(capeClass);

            cape.addString(new StringConfigOption("name", capeJsonObject.get("title").getAsString()));
            cape.addString(new StringConfigOption("url", capeJsonObject.get("url").getAsString()));
            cape.addString(new StringConfigOption("type", capeJsonObject.get("type").getAsString()));
            cape.addNumber(new NumberConfigOption("colour", -9335852));

            JsonArray altsObject = new JsonArray();
            if (capeJsonObject.has("alts"))
                altsObject = capeJsonObject.get("alts").getAsJsonArray();

            ArrayConfigOption altsOption = new ArrayConfigOption("alts");

            for (int j = 0; j < altsObject.size(); j++) {
                altsOption.addElement(altsObject.get(j).getAsString());
            }

            cape.addArray(altsOption);

            cape.addConfigOption(
                    new ConfigOption.Builder("settings")
                            .addBoolean(new BooleanConfigOption("config.cape_cacher.cape.notify_when_found", true))
                            .addBoolean(new BooleanConfigOption("config.cape_cacher.cape.notify_in_console", true))
                            .build()
            );

            capesOptions.add(cape.build());
        }

        return capesOptions;
    }
}
