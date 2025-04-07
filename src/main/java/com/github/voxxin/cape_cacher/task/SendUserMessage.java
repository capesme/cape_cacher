package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.config.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;

import java.net.URI;

public class SendUserMessage {

    public static void sendMessage(String username, Component capeName) {

        Minecraft.getInstance().player.displayClientMessage(
                Component.literal("")
                        .append(Component.literal("[")
                                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))
                        .append(Component.translatable("cape_cacher.name")
                                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))
                                        .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://modrinth.com/mod/cape-cacher")))))
                        .append(Component.literal("]")
                                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))
                        .append(Component.literal(" \uD83E\uDC7A ")
                                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))
                        .append(Component.translatable("text.cape_cacher.notify.found_user",
                                Component.literal(username)
                                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))
                                                .withClickEvent(new ClickEvent.OpenUrl(URI.create("https://capes.me/" + username))))))
                        .append(Component.literal(" : ")
                                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))
                        .append(capeName),
                false
        );

        if (Manager.HANDLER.instance().playNotifySound)
            Minecraft.getInstance().player.playSound(SoundEvents.CAT_PURREOW, (float) Manager.HANDLER.instance().notifySoundStrength / 100, 1f);
    }
}
