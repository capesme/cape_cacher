package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.config.model.CapeSettingsB;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.config.model.ModSettingsModel;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.SendUserMessage;
import com.github.voxxin.cape_cacher.task.util.UserObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTextures;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.client.MinecraftClient;
<<<<<<< Updated upstream
import net.minecraft.client.network.PlayerListEntry;
=======
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.SkinTextures;
>>>>>>> Stashed changes
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import java.util.HashMap;
import java.util.Map;


@Mixin(PlayerSkinProvider.class)
public abstract class PlayerSkinProviderMixin {
    @Inject(at = @At("TAIL"), method = "fetchSkinTextures(Ljava/util/UUID;Lcom/mojang/authlib/minecraft/MinecraftProfileTextures;)Ljava/util/concurrent/CompletableFuture;", locals = LocalCapture.CAPTURE_FAILHARD)
    private void loadSkin(
            UUID uuid, MinecraftProfileTextures textures,
            CallbackInfoReturnable<CompletableFuture<SkinTextures>> cir,
            MinecraftProfileTexture minecraftProfileTexture,
            CompletableFuture completableFuture,
            SkinTextures.Model model, String string,
            MinecraftProfileTexture minecraftProfileTexture2,
            CompletableFuture completableFuture2, MinecraftProfileTexture minecraftProfileTexture3,
            CompletableFuture completableFuture3) {

        if (textures.cape() == null) return;
        if (textures.cape().getUrl() == null) return;

        ProfileResult profileResult = MinecraftClient.getInstance().getSessionService().fetchProfile(uuid, true);
        if (profileResult == null) return;
        GameProfile profile = profileResult.profile();

        String textureURL = textures.cape().getUrl();

        if (MinecraftClient.getInstance().player == null) return;

        final String playerUUID = String.valueOf(profile.getId());
        if (playerUUID.endsWith("0000-000000000000")) return;

        final String playerName = profile.getName();
        CapesObject capeInfo = IdentifyCapeType.CapeIdentifier(textureURL);
        PingSite.pingCapesmeAsync(playerUUID)
                .exceptionally(e -> {
                    CapeCacher.LOGGER.error("Request failed: " + e.getMessage());
                    return null;
                });

        UserObject thisUserObject = new UserObject(playerUUID, textureURL);
        if (StaticValues.userCapeMap.contains(thisUserObject)) return;

        StaticValues.userCapeMap.add(thisUserObject);

        assert MinecraftClient.getInstance().player != null;
        if (capeInfo.type.equals("unknown")) PingSite.pingFoundNewAsync(playerUUID, textureURL.replace("http://textures.minecraft.net/texture/", ""));

        if (playerUUID.equals(MinecraftClient.getInstance().player.getUuid().toString()) && !Boolean.parseBoolean(ModSettingsModel.NOTIFY_WHEN_SELF.value)) return;

        if (!capeInfo.getSettingB(CapeSettingsB.CapeSettingsBTemplate.NOTIFY.key).value) return;
        if (capeInfo.getSettingB(CapeSettingsB.CapeSettingsBTemplate.NOTIFY_IN_CONSOLE.key).value) {
            CapeCacher.LOGGER.info("Found cape for " + playerName + " (" + playerUUID + ")" + " : " + capeInfo.title);
        }


        Text foundCape = Text.literal(" ")
                .append(capeInfo.title).fillStyle(
                        Style.EMPTY.withColor(TextColor.fromRgb(capeInfo.colour))
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, capeInfo.URL)));

        SendUserMessage.sendMessage(playerName, foundCape);
    }
}


