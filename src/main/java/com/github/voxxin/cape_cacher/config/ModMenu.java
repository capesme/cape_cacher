package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

@Config(name = CapeCacher.MODID)
public class ModMenu implements ConfigData {
    @ConfigEntry.Gui.Excluded

    public static ModMenu INSTANCE;

    public static void init() {

        AutoConfig.register(ModMenu.class, JanksonConfigSerializer::new);

        INSTANCE = AutoConfig.getConfigHolder(ModMenu.class).getConfig();

    }
}
