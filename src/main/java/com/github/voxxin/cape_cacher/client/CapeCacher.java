package com.github.voxxin.cape_cacher.client;
import com.github.voxxin.cape_cacher.config.Manager;
import com.github.voxxin.cape_cacher.task.PingSite;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;


@Environment(EnvType.CLIENT)
public class CapeCacher implements ClientModInitializer {
    public static final String MODID = "cape_cacher";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final String MODVERSION = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MODID).orElse(null)).getMetadata().getVersion().getFriendlyString();
    public static Manager manager;

    @Override
    public void onInitializeClient() {
        try {
            StaticValues.capesJsonObject = PingSite.fetchCapes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        manager = new Manager();
    }

    public static Minecraft client() { return Minecraft.getInstance(); }

}

