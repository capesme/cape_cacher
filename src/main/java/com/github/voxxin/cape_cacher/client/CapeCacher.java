package com.github.voxxin.cape_cacher.client;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CapeCacher implements ClientModInitializer {
    public static final String MODID = "cape_cacher";

    @Override
    public void onInitializeClient() {
    }
}
