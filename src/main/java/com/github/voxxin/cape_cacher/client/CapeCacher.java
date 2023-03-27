package com.github.voxxin.cape_cacher.client;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.ModMenu;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CapeCacher implements ClientModInitializer {
    public static final String MODID = "cape_cacher";

    public static ModConfig config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        config  = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
