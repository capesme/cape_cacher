package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.config.ModConfig;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.PingSite;
import com.github.voxxin.cape_cacher.task.SendUserMessage;
import com.github.voxxin.cape_cacher.task.UserObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;


@Mixin(PlayerListEntry.class)
public abstract class PlayerSkinProviderMixin {
    private static final Map<String, String> capeTypeToColor = new HashMap<>();
    static {
        capeTypeToColor.put("0", "§9");
        capeTypeToColor.put("1", "§d");
        capeTypeToColor.put("2", "§c");
        capeTypeToColor.put("3", "§a");
        capeTypeToColor.put("4", "§b");
        capeTypeToColor.put("6", "§5");
        capeTypeToColor.put("unknown", "§b");
    }

    private static final String Broken2016Url = "http://textures.minecraft.net/texture/5c3ca7ee2a498f1b5d258d5fa927e63e433143add5538cf63b6a9b78ae735";
    private static final String Broken2012Url  = "http://textures.minecraft.net/texture/c3af7fbwww821254664558f28361158ca73303c9a85e96e5251102958d7ed60c4a3";

    @Shadow public abstract GameProfile getProfile();

    @Inject(at = @At("HEAD"), method = "method_2956 ")
    private void loadSkin(MinecraftProfileTexture.Type type, Identifier id, MinecraftProfileTexture texture, CallbackInfo ci) {
        MinecraftProfileTexture.Type CAPE = MinecraftProfileTexture.Type.CAPE;
        String textureURL = texture.getUrl();

        if (!type.equals(CAPE)) return;
        if (textureURL.equalsIgnoreCase(Broken2016Url)) return;
        if (textureURL.equalsIgnoreCase(Broken2012Url)) return;

        if (MinecraftClient.getInstance().player == null) return;

        final String playerUUID = getProfile().getId().toString();
        if (playerUUID.endsWith("0000-000000000000")) return;

        final String playerName = getProfile().getName();
        String[] capeInfo = IdentifyCapeType.CapeIdentifier(textureURL);
        PingSite.pingCapesmeAsync(playerUUID)
                .exceptionally(e -> {
                    System.out.println("Request failed: " + e.getMessage());
                    return null;
                });

        if (capeInfo.length > 3 || capeInfo[0].equals("") || capeInfo[1].equals("") || capeInfo[2].equals("")) return;
        if (capeInfo[1].equalsIgnoreCase("unknown")) {
            System.out.println(textureURL);
            return;
        }

        UserObject thisUserObject = new UserObject(playerUUID, textureURL);
        if (CapeCacher.userCapeMap.contains(thisUserObject)) return;

        CapeCacher.userCapeMap.add(thisUserObject);

        assert MinecraftClient.getInstance().player != null;
        if (playerUUID.equals(MinecraftClient.getInstance().player.getUuid().toString()) && !ModConfig.getNotifyWhenCacheSelf()) return;

        String foundCape = capeTypeToColor.get(capeInfo[2]) + capeInfo[0];
        SendUserMessage.sendMessage(playerName, foundCape);
    }
}


