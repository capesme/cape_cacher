package com.github.voxxin.cape_cacher.task.util;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.Manager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomJsonReader {
    private static final JsonArray capesJsonArray = StaticValues.capesJsonObject;

    public static List<OptionGroup> getCapes() {
        List<OptionGroup> capesOptions = new ArrayList<>();
        refreshExistingCapes();

        if (capesJsonArray == null) return capesOptions;

        for (var capeElement : capesJsonArray) {
            JsonObject capeJson = capeElement.getAsJsonObject();
            String configKey = capeJson.get("class").getAsString();
            JsonObject configObj = Manager.HANDLER.instance().capesJsonObject.getAsJsonObject(configKey);

            OptionDescription.Builder tooltip = createTooltipImage(capeJson);

            OptionGroup group = OptionGroup.createBuilder()
                    .name(Component.literal(capeJson.get("title").getAsString()))
                    .description(tooltip.build())
                    .option(createBooleanOption(
                            "config.cape_cacher.cape.notify_when_found",
                            capeJson.get("cache").getAsBoolean(),
                            configObj,
                            "notify_when_found",
                            tooltip
                                    .text(Component.translatable("config.cape_cacher.cape.notify_when_found.tooltip"))
                    ))
                    .option(createBooleanOption(
                            "config.cape_cacher.cape.notify_in_console",
                            false,
                            configObj,
                            "notify_in_console",
                            tooltip
                                    .text(Component.translatable("config.cape_cacher.cape.notify_in_console.tooltip"))
                    ))
                    .option(createColorOption(
                            new Color(-9335852),
                            configObj,
                            tooltip
                                    .text(Component.translatable("config.cape_cacher.cape.cape_color.tooltip"))
                    ))
                    .build();

            capesOptions.add(group);
        }

        return capesOptions;
    }

    public static JsonObject capesForSettings() {
        JsonObject capes = new JsonObject();
        if (capesJsonArray == null) return capes;

        for (var capeElement : capesJsonArray) {
            JsonObject capeJson = capeElement.getAsJsonObject();
            String configKey = capeJson.get("class").getAsString();
            capes.add(configKey, createCapeConfig(capeJson));
        }

        return capes;
    }

    private static void refreshExistingCapes() {
        JsonObject currentCapes = Manager.HANDLER.instance().capesJsonObject;

        for (var capeElement : capesJsonArray) {
            JsonObject capeJson = capeElement.getAsJsonObject();
            String configKey = capeJson.get("class").getAsString();

            if (currentCapes.has(configKey)) {
                currentCapes.getAsJsonObject(configKey).remove("base");
                currentCapes.getAsJsonObject(configKey).add("base", capeJson);
            } else {
                currentCapes.add(configKey, createCapeConfig(capeJson));
            }
        }
    }

    private static OptionDescription.Builder createTooltipImage(JsonObject capeJson) {
        String capeType = capeJson.get("type").getAsString().toLowerCase();
        ResourceLocation imagePath = ResourceLocation.fromNamespaceAndPath(CapeCacher.MODID, capeType);
        return OptionDescription.createBuilder()
                .image(imagePath, 256, 256);
    }

    private static Option<Boolean> createBooleanOption(String translationKey, boolean defaultValue,
                                                       JsonObject configObj, String propertyName,
                                                       OptionDescription.Builder description) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable(translationKey))
                .binding(defaultValue,
                        () -> configObj.get(propertyName).getAsBoolean(),
                        value -> updateJsonProperty(configObj, propertyName, value))
                .controller(BooleanControllerBuilder::create)
                .description(description.build())
                .build();
    }

    private static Option<Color> createColorOption(Color defaultValue,
                                                   JsonObject configObj,
                                                   OptionDescription.Builder description) {
        return Option.<Color>createBuilder()
                .name(Component.translatable("config.cape_cacher.cape.cape_color"))
                .binding(defaultValue,
                        () -> new Color(configObj.get("colour").getAsInt()),
                        value -> updateJsonProperty(configObj, "colour", value.getRGB()))
                .controller(ColorControllerBuilder::create)
                .description(description.build())
                .build();
    }

    private static void updateJsonProperty(JsonObject json, String property, Object value) {
        json.remove(property);
        if (value instanceof Boolean) {
            json.addProperty(property, (Boolean) value);
        } else if (value instanceof Integer) {
            json.addProperty(property, (Integer) value);
        }
    }

    private static JsonObject createCapeConfig(JsonObject base) {
        JsonObject config = new JsonObject();
        config.add("base", base);

        config.addProperty("notify_when_found", !base.has("cache") || !base.get("cache").isJsonPrimitive() || base.getAsJsonPrimitive("cache").getAsBoolean());
        config.addProperty("notify_in_console", false);
        config.addProperty("colour", -9335852);
        return config;
    }
}