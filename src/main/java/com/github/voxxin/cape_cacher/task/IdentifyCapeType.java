package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.config.Manager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class IdentifyCapeType {
    public static JsonObject CapeIdentifier(String CapeURL) {
        JsonObject capeInfoB = new JsonObject();
        JsonObject baseB = new JsonObject();

        baseB.addProperty("url", CapeURL);
        baseB.addProperty("title", "???");
        baseB.addProperty("type", "unknown");
        capeInfoB.add("base", baseB);

        capeInfoB.addProperty("notify_when_found", true);
        capeInfoB.addProperty("notify_in_console", false);
        capeInfoB.addProperty("colour", -9335852);

        if (!CapeURL.contains("http://textures.minecraft.net/texture/")) return capeInfoB;

        for (Map.Entry<String, JsonElement> capesObject : Manager.HANDLER.instance().capesJsonObject.entrySet()) {
            JsonObject base = capesObject.getValue().getAsJsonObject().get("base").getAsJsonObject();
            String url = base.get("url").getAsString();

            List<String> alts;
            JsonElement altsElement = base.get("alts");
            if (altsElement != null && altsElement.isJsonArray()) {
                alts = altsElement.getAsJsonArray().asList().stream()
                        .map(JsonElement::getAsString)
                        .toList();
            } else {
                alts = List.of();
            }

            if (CapeURL.equals(url) || alts.contains(CapeURL)) {
                return capesObject.getValue().getAsJsonObject();
            }
        }

        return capeInfoB;
    }
}