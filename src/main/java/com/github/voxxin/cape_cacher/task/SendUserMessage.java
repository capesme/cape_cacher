package com.github.voxxin.cape_cacher.task;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class SendUserMessage {

    public static void sendMessage(String username, String capeName) {
        MinecraftClient.getInstance().player.sendMessage(
                Text.of("§f§l[§cCAPES.ME§f§l] §e-> " + "§fFound User §a" + username + " §e: " + capeName),
                false);

        if (ModConfig.getNotifyMessageAll()) {
            MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_CAT_PURREOW, ModConfig.getNotifySoundStrength(), 1f);
        }
    }
}
