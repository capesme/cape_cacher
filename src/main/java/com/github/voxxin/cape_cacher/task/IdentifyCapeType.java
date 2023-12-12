package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.task.util.CustomJsonReader;
import com.github.voxxin.cape_cacher.task.util.ProcessCapes;

import java.util.ArrayList;
import java.util.List;

public class IdentifyCapeType {
    public static CapesObject CapeIdentifier(String CapeURL) {
        CapesObject capeInfo = new CapesObject(CapeURL, "unknown", 0, "unknown", new ArrayList<>());

        if (!CapeURL.contains("http://textures.minecraft.net/texture/")) return capeInfo;

        ArrayList<CapesObject> processedCapes = ProcessCapes.processCapes();

        for (CapesObject capesObject : processedCapes) {
            if (capesObject.URL.equals(CapeURL) || capesObject.alts.contains(CapeURL)) {
                return capesObject;
            }
        }

        return capeInfo;
    }
}
