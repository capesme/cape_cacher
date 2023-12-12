package com.github.voxxin.cape_cacher.client;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.util.KeyboardManager;
import com.github.voxxin.cape_cacher.task.util.UserObjectList;
import com.google.gson.JsonArray;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Environment(EnvType.CLIENT)
public class CapeCacher implements ClientModInitializer {
    public static final String MODID = "cape_cacher";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final String MODVERSION = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MODID).orElse(null)).getMetadata().getVersion().getFriendlyString();

    @Override
    public void onInitializeClient() {
        try {
            StaticValues.capesJsonObject = PingSite.fetchCapes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new ModConfig();
    }

    public static MinecraftClient client() { return MinecraftClient.getInstance(); }

}

