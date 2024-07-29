package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;

public class SendUserMessage {

    public static void sendMessage(String username, Component capeName) {
        if (CapeCacher.manager.getModConfigOption("notify_when_self").getAsBoolean().getValue())
        Minecraft.getInstance().player.displayClientMessage(
                Component.literal("")
                        .append(Component.literal("[").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF)).withBold(true)))

                        .append(Component.translatable("cape_cacher.name").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))
                                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/cape-cacher"))))

                        .append(Component.literal("]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))
                                .withBold(true)))

                        .append(Component.literal(" => ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))
                                .withBold(false)))

                        .append(Component.translatable("text.cape_cacher.notify.found_user",
                                Component.literal(username).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x00FF00))
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://capes.me/" + username)))))

                        .append(Component.literal(" :").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFF00))))

                        .append(capeName),
                false);

        if (CapeCacher.manager.getModConfigOption("notify_with_sound").getAsBoolean().getValue())
            Minecraft.getInstance().player.playSound(SoundEvents.CAT_PURR, CapeCacher.manager.getModConfigOption("notify_sound_strength").getAsNumber().getValueAsFloat(), 1f);
    }
}
