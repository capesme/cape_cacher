package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.task.util.CustomJsonReader;
import com.github.voxxin.cape_cacher.task.util.ResourceLocationHandler;
import com.google.gson.JsonObject;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Manager {
    public static final ConfigClassHandler<Manager> HANDLER = ConfigClassHandler.createBuilder(Manager.class)
            .id(ResourceLocationHandler.make(CapeCacher.MODID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("cape_cacher.json5"))
                    .setJson5(true)
                    .build())
            .build();

    static {
        HANDLER.load();
    }

    @SerialEntry public boolean minimizedMessage = false;
    @SerialEntry public boolean notifyWhenAny = true;
    @SerialEntry public boolean notifyInConsole = false;
    @SerialEntry public boolean notifyWhenSelf = false;
    @SerialEntry public boolean playNotifySound = true;
    @SerialEntry public int notifySoundStrength = 100;
    @SerialEntry public JsonObject capesJsonObject = CustomJsonReader.capesForSettings();

    public YetAnotherConfigLib createScreen() {
        ConfigCategory.Builder capeCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("config.cape_cacher.category.cape"));
        CustomJsonReader.getCapes().forEach(capeCategory::group);

        ConfigCategory generalCategory = ConfigCategory.createBuilder()
                .name(Component.translatable("config.cape_cacher.category.general"))
                .option(createBooleanOption(
                        "config.cape_cacher.general.minimized_message",
                        false,
                        () -> minimizedMessage,
                        val -> minimizedMessage = val
                ))
                .option(createBooleanOption(
                        "config.cape_cacher.general.notify_when_any",
                        true,
                        () -> notifyWhenAny,
                        val -> notifyWhenAny = val
                ))
                .option(createBooleanOption(
                        "config.cape_cacher.general.notify_in_console",
                        true,
                        () -> notifyInConsole,
                        val -> notifyInConsole = val
                ))
                .option(createBooleanOption(
                        "config.cape_cacher.general.notify_when_self",
                        false,
                        () -> notifyWhenSelf,
                        val -> notifyWhenSelf = val
                ))
                .option(createBooleanOption(
                        "config.cape_cacher.general.notify_with_sound",
                        true,
                        () -> playNotifySound,
                        val -> playNotifySound = val
                ))
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.cape_cacher.general.notify_sound_strength"))
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                .range(0, 100)
                                .step(1)
                                .formatValue(value -> Component.translatable("config.cape_cacher.general.notify_sound_strength.value", value)))
                        .binding(100, () -> notifySoundStrength, value -> notifySoundStrength = value)
                        .build())
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("cape_cacher.name"))
                .category(capeCategory.build())
                .category(generalCategory)
                .save(HANDLER::save)
                .build();
    }

    private Option<Boolean> createBooleanOption(String translationKey, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable(translationKey))
                .controller(BooleanControllerBuilder::create)
                .binding(defaultValue, getter, setter)
                .description(
                        OptionDescription.createBuilder()
                                .text(Component.translatable(translationKey + ".tooltip"))
                                .build()
                )
                .build();
    }
}