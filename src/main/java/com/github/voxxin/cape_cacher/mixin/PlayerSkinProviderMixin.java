package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.api.config.option.ConfigOption;
import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.SendUserMessage;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTextures;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Mixin(SkinManager.class)
public abstract class PlayerSkinProviderMixin {
    @Inject(at = @At("TAIL"), method = "registerTextures")
    private void loadSkin(UUID uuid, MinecraftProfileTextures textures, CallbackInfoReturnable<CompletableFuture<PlayerSkin>> cir) {

        // Check if the player's skin has a cape, return early if not
        if (textures.cape() == null || textures.cape().getUrl() == null) {
            return;
        }

        // Retrieve the player's profile
        ProfileResult profileResult = Minecraft.getInstance().getMinecraftSessionService().fetchProfile(uuid, true);
        // Return early if profile doesn't exist (Fake NPCs)
        if (profileResult == null) return;

        // Retrieve the player's profile and texture URL
        GameProfile profile = profileResult.profile();
        String textureURL = textures.cape().getUrl();

        // Make sure the client player exists. If not, return
        if (Minecraft.getInstance().player == null) return;

        // Retrieve cape information
        ConfigOption capeInfo = IdentifyCapeType.CapeIdentifier(textureURL);

        // Ping a site asynchronously with the player's UUID
        PingSite.pingCapesmeAsync(profile.getId().toString())
                .exceptionally(e -> {
                    CapeCacher.LOGGER.error("Request failed: " + e.getMessage());
                    return null;
                });

        String userIdString = profile.getId().toString();

        if (!StaticValues.userCapeMap.containsKey(userIdString) ||
                !StaticValues.userCapeMap.get(userIdString).equals(textureURL)) {
            StaticValues.userCapeMap.put(userIdString, textureURL);
        } else return;

        // Check if cape type is unknown, ping site with the discovery
        if (capeInfo.getOptions().stream().anyMatch(o -> o.getTranslationKey().equals("unknown"))) {
            PingSite.pingFoundNewAsync(profile.getId().toString(), textureURL.replace("http://textures.minecraft.net/texture/", ""));
        }

        // Check if current player is not the same as the discovered player and notifications for self-discovered capes are disabled, return early if so
        if (profile.getId().toString().equals(Minecraft.getInstance().player.getUUID().toString()) &&
                CapeCacher.manager.getModConfigOption("notify_when_self").getAsBoolean().getValue()) {
            return;
        }

        // Check if notifications for the discovered cape type are disabled, return early if so
        if (!CapeCacher.manager.getModConfigOption("notify_when_any").getAsBoolean().getValue()) {
            return;
        }

        // Check if console notifications for the discovered cape type are enabled, log a message if so
        if (CapeCacher.manager.getModConfigOption("notify_in_console").getAsBoolean().getValue()) {
            CapeCacher.LOGGER.info("Found cape for {} ({}) : {}", profile.getName(), profile.getId().toString(), capeInfo.getOption("name").getAsString().getValue());
        }

        // Construct a Text object representing the discovered cape and send a message to the player
        Component foundCape = Component.literal(" ")
                .append(capeInfo.getOption("name").getAsString().getValue()).withStyle(
                        Style.EMPTY.withColor(TextColor.fromRgb(capeInfo.getOption("colour").getAsNumber().getValueAsInteger()))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, capeInfo.getOption("url").getAsString().getValue())));

        SendUserMessage.sendMessage(profile.getName(), foundCape);
    }
}




