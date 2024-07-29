package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.api.config.option.*;
import com.github.voxxin.cape_cacher.client.CapeCacher;

public class IdentifyCapeType {
    public static ConfigOption CapeIdentifier(String CapeURL) {
        ConfigOption.Builder capeInfoB = new ConfigOption.Builder("unknown")
                .addString(new StringConfigOption("name", "unknown"))
                .addString(new StringConfigOption("url", CapeURL))
                .addString(new StringConfigOption("type", "migrator_cape"))
                .addNumber(new NumberConfigOption("colour", 0xFFFFFF))
                .addConfigOption(
                        new ConfigOption.Builder("settings")
                                .addBoolean(new BooleanConfigOption("config.cape_cacher.cape.notify_when_found", true))
                                .addBoolean(new BooleanConfigOption("config.cape_cacher.cape.notify_in_console", true))
                                .build()
                )
                .addArray(new ArrayConfigOption("alts"));

        if (!CapeURL.contains("http://textures.minecraft.net/texture/")) return capeInfoB.build();

        for (AbstractOption capesObject : CapeCacher.manager.getCapesConfig().getOptions()) {
            if (capesObject instanceof ConfigOption object) {
                String url = object.getOption("url").getAsString().getValue();
                boolean containsInAlts = object.getOption("alts").getAsArray().getElements().contains(CapeURL);
                if (CapeURL.equals(url) || containsInAlts) {
                    return object;
                }
            }
        }

        return capeInfoB.build();
    }

}
