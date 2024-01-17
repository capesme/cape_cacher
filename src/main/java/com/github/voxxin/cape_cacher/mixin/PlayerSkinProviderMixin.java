package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.model.CapeSettingsB;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.config.model.ModSettingsModel;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.SendUserMessage;
import com.github.voxxin.cape_cacher.task.util.UserObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerListEntry.class)
public abstract class PlayerSkinProviderMixin {
    @Shadow public abstract GameProfile getProfile();

    @Inject(at = @At("HEAD"), method = "method_2956")
    private void loadSkin(MinecraftProfileTexture.Type type, Identifier id, MinecraftProfileTexture texture, CallbackInfo ci) {
        MinecraftProfileTexture.Type CAPE = MinecraftProfileTexture.Type.CAPE;
        String textureURL = texture.getUrl();

        if (!type.equals(CAPE)) return;
        if (MinecraftClient.getInstance().player == null) return;

        final String playerUUID = getProfile().getId().toString();
        if (playerUUID.endsWith("0000-000000000000")) return;

        final String playerName = getProfile().getName();
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


