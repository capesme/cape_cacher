package com.github.voxxin.cape_cacher.client;
import com.github.voxxin.cape_cacher.task.PingSite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


@Environment(EnvType.CLIENT)
public class CapeCacher implements ModInitializer {
    public static final String MODID = "cape_cacher";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final String MODVERSION = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MODID).orElse(null)).getMetadata().getVersion().getFriendlyString();

    private static final String KEYBIND_CATEGORY = "cape_cacher.name";
    public static final KeyMapping OPEN_CONFIG = new KeyMapping("key.cape_cacher.open_config_menu", GLFW.GLFW_KEY_I, KEYBIND_CATEGORY);

    @Override
    public void onInitialize() {
        try {
            StaticValues.capesJsonObject = PingSite.fetchCapesAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        KeybindingRegistry.addCategory(Component.translatable(KEYBIND_CATEGORY).toString());
        KeybindingRegistry.registerKeyBinding(OPEN_CONFIG);
    }
}

