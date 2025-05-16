package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.config.Manager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.SoundEvents;

import java.net.URI;

public class SendUserMessage {

    public static void sendMessage(String username, Component capeName) {

        Minecraft.getInstance().player.displayClientMessage(
                formatMessage(username, capeName),
                false
        );

        if (Manager.HANDLER.instance().playNotifySound)
            Minecraft.getInstance().player.playSound(SoundEvents.CAT_PURREOW, (float) Manager.HANDLER.instance().notifySoundStrength / 100, 1f);
    }

    private static Style addClickEvent(Style style, String url) {
        //? if >=1.21.5 {
         /*style = style.withClickEvent(new ClickEvent.OpenUrl(URI.create(url)));
        *///?} else if >=1.20 {
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        //?}

        return style;
    }

    private static Component formatMessage(String username, Component capeName) {
        Component modNameComponent = !Manager.HANDLER.instance().minimizedMessage
                ? Component.translatable("cape_cacher.name")
                .withStyle(addClickEvent(Style.EMPTY.withColor(TextColor.fromRgb(0x386070)), "https://modrinth.com/mod/cape-cacher")) :
                Component.translatable("cape_cacher.name_minimized")
                        .withStyle(addClickEvent(Style.EMPTY.withColor(TextColor.fromRgb(0x386070)), "https://modrinth.com/mod/cape-cacher"));

        Component componentText = !Manager.HANDLER.instance().minimizedMessage
                ? Component.literal(" \uD83E\uDC7A ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00)))
                .append(Component.translatable("text.cape_cacher.notify.found_user",
                        Component.literal(username)
                                .withStyle(addClickEvent(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)), "https://capes.me/" + username)))
                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)))) :
                Component.literal(" ")
                        .append(Component.literal(username)
                                .withStyle(addClickEvent(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00)), "https://capes.me/" + username)));

        return Component.literal("")
                .append(Component.literal("[")
                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))
                .append(modNameComponent)
                .append(Component.literal("]")
                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))
                .append(componentText)
                .append(Component.literal(" : ")
                        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))
                .append(capeName);

    }
}
