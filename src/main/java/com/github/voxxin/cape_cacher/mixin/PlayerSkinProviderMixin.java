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
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.SkinTextures;
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
    private void loadSkin(UUID uuid, MinecraftProfileTextures textures, CallbackInfoReturnable<CompletableFuture<SkinTextures>> cir, MinecraftProfileTexture minecraftProfileTexture, CompletableFuture completableFuture, SkinTextures.Model model, String string, MinecraftProfileTexture minecraftProfileTexture2, CompletableFuture completableFuture2, MinecraftProfileTexture minecraftProfileTexture3, CompletableFuture completableFuture3) {

        // Check if the player's skin has a cape, return early if not
        if (textures.cape() == null || textures.cape().getUrl() == null) {
            return;
        }

        // Retrieve the player's profile
        ProfileResult profileResult = MinecraftClient.getInstance().getSessionService().fetchProfile(uuid, true);
        // Return early if profile doesn't exist (Fake NPCs)
        if (profileResult == null) return;

        // Retrieve the player's profile and texture URL
        GameProfile profile = profileResult.profile();
        String textureURL = textures.cape().getUrl();

        // Make sure the client player exists. If not, return
        if (MinecraftClient.getInstance().player == null) return;

        // Retrieve cape information
        CapesObject capeInfo = IdentifyCapeType.CapeIdentifier(textureURL);

        // Ping a site asynchronously with the player's UUID
        PingSite.pingCapesmeAsync(profile.getId().toString())
                .exceptionally(e -> {
                    CapeCacher.LOGGER.error("Request failed: " + e.getMessage());
                    return null;
                });

        // Create a UserObject with player's UUID and texture URL
        UserObject thisUserObject = new UserObject(profile.getId().toString(), textureURL);

        // Check if userCapeMap already contains thisUserObject, return early if so
        if (StaticValues.userCapeMap.contains(thisUserObject)) {
            return;
        }

        // Add thisUserObject to userCapeMap
        StaticValues.userCapeMap.add(thisUserObject);

        // Check if cape type is unknown, ping site with the discovery
        if ("unknown".equals(capeInfo.type)) {
            PingSite.pingFoundNewAsync(profile.getId().toString(), textureURL.replace("http://textures.minecraft.net/texture/", ""));
        }

        // Check if current player is not the same as the discovered player and notifications for self-discovered capes are disabled, return early if so
        if (profile.getId().toString().equals(MinecraftClient.getInstance().player.getUuid().toString()) &&
                !Boolean.parseBoolean(ModSettingsModel.NOTIFY_WHEN_SELF.value)) {
            return;
        }

        // Check if notifications for the discovered cape type are disabled, return early if so
        if (!capeInfo.getSettingB(CapeSettingsB.CapeSettingsBTemplate.NOTIFY.key).value) {
            return;
        }

        // Check if console notifications for the discovered cape type are enabled, log a message if so
        if (capeInfo.getSettingB(CapeSettingsB.CapeSettingsBTemplate.NOTIFY_IN_CONSOLE.key).value) {
            CapeCacher.LOGGER.info("Found cape for " + profile.getName() + " (" + profile.getId().toString() + ")" + " : " + capeInfo.title);
        }

        // Construct a Text object representing the discovered cape and send a message to the player
        Text foundCape = Text.literal(" ")
                .append(capeInfo.title).fillStyle(
                        Style.EMPTY.withColor(TextColor.fromRgb(capeInfo.colour))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, capeInfo.URL)));

        SendUserMessage.sendMessage(profile.getName(), foundCape);
    }
}




