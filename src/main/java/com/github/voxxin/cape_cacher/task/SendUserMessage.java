package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.config.model.ModSettingsModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class SendUserMessage {

    public static void sendMessage(String username, Text capeName) {
        if (Boolean.parseBoolean(ModSettingsModel.NOTIFY_WHEN_ANY.value))
        MinecraftClient.getInstance().player.sendMessage(
                Text.literal("")
                        .append(Text.literal("[").fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))

                        .append(Text.translatable("cape_cacher.name").fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/cape-cacher"))))

                        .append(Text.literal("]").fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))
                                .withBold(true)))

                        .append(Text.literal(" -> ").fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))
                                .withBold(false)))

                        .append(Text.translatable("text.cape_cacher.notify.found_user",
                                Text.literal(username).fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://capes.me/" + username)))))

                        .append(Text.literal(" :").fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))

                        .append(capeName),
                false);

        if (Boolean.parseBoolean(ModSettingsModel.NOTIFY_WITH_SOUND.value))
            MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_CAT_PURREOW, Float.parseFloat(ModSettingsModel.NOTIFY_SOUND_STRENGTH.value), 1f);
    }
}
