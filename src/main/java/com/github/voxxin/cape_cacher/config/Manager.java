package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.api.config.ConfigManager;
import com.github.voxxin.api.config.option.AbstractOption;
import com.github.voxxin.api.config.option.BooleanConfigOption;
import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.api.config.option.NumberConfigOption;
import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.task.util.CustomJsonReader;
import net.fabricmc.loader.api.FabricLoader;

public class Manager {

    ConfigManager config = new ConfigManager(FabricLoader.getInstance().getConfigDir().resolve(CapeCacher.MODID).toFile(), "config");
    private final ConfigOption capesCategory;
    public final ConfigOption modCategory;


    public ConfigOption getCapesConfig() {
        return capesCategory;
    }

    public AbstractOption getCapesConfig(String translatedKey) {
        return capesCategory.getOptions().stream().filter(option -> option.getTranslationKey().equals("config.cape_cacher.general."+translatedKey)).findFirst().orElse(null);
    }

    public ConfigOption getModConfig() {
        return modCategory;
    }

    public AbstractOption getModConfigOption(String translatedKey) {
        return modCategory.getOption("config.cape_cacher.mod."+translatedKey);
    }

    public Manager() {
        // Initialize configs
        ConfigOption.Builder capesCategoryB = new ConfigOption.Builder(CapeCacher.MODID + ".category.capes");
        CustomJsonReader.getCapes().forEach(capesCategoryB::addConfigOption);
        capesCategory = capesCategoryB.build();

        modCategory = new ConfigOption.Builder(CapeCacher.MODID + ".category.mod")
                .addBoolean(new BooleanConfigOption("config.cape_cacher.mod.notify_when_any", true))
                .addBoolean(new BooleanConfigOption("config.cape_cacher.mod.notify_with_sound", true))
                .addBoolean(new BooleanConfigOption("config.cape_cacher.mod.notify_when_self", true))
                .addBoolean(new BooleanConfigOption("config.cape_cacher.mod.notify_in_console", true))
                .addNumber(new NumberConfigOption("config.cape_cacher.mod.notify_sound_strength", 0.5f))
                .build();

        config.addOption(modCategory);
        config.addOption(capesCategory);

        config.runOptions();
    }
}
