package com.github.voxxin.cape_cacher.task.util;

import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.model.CapesObject;

import java.util.ArrayList;

public class ProcessCapes {

    public static ArrayList<CapesObject> processCapes() {
        ArrayList<CapesObject> websiteCapes = CustomJsonReader.getCapes();
        ArrayList<CapesObject> processedCapes = new ArrayList<>(StaticValues.settingCapes);

        boolean flaggedChange = false;

        for (CapesObject websiteCape : websiteCapes) {
            boolean found = false;

            for (CapesObject processedCape : processedCapes) {
                if (websiteCape.type.equals(processedCape.type) && websiteCape.alts != processedCape.alts) {
                    for (String alt : websiteCape.alts) {
                        if (!processedCape.alts.contains(alt)) {
                            processedCape.alts.add(alt);
                            flaggedChange = true;
                        }
                    }
                }

                if (websiteCape.URL.equals(processedCape.URL) || websiteCape.alts.contains(processedCape.URL)) {
                    websiteCape.colour = processedCape.colour;
                    websiteCape.settingsB = processedCape.settingsB;

                    for (String key : processedCape.settingColour.keySet()) {
                        websiteCape.setSettingI(key, processedCape.getSettingI(key).colour);
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                websiteCape.colour = 0x5865F2;
                processedCapes.add(websiteCape);
                flaggedChange = true;
            }
        }

        if (flaggedChange) {
            StaticValues.settingCapes = processedCapes;
            ModConfig.exportCapeConfig();
        }

        return websiteCapes;
    }

}
