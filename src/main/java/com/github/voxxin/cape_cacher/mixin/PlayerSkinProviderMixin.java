package com.github.voxxin.cape_cacher.mixin;



import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.task.IdentifyCapeType;
import com.github.voxxin.cape_cacher.task.ValidateUUID;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;


@Mixin(PlayerListEntry.class)
public abstract class PlayerSkinProviderMixin {

    @Shadow public abstract GameProfile getProfile();

    @Shadow @Final private GameProfile profile;

    @Inject(at = @At("HEAD"), method = "method_2956 ")
    private void loadSkin(MinecraftProfileTexture.Type type, Identifier id, MinecraftProfileTexture texture, CallbackInfo ci) {
        MinecraftProfileTexture.Type CAPE = MinecraftProfileTexture.Type.CAPE;
        String textureURL = texture.getUrl();

        String Broken2012Url = "http://textures.minecraft.net/texture/c3af7fb821254664558f28361158ca73303c9a85e96e5251102958d7ed60c4a3";
        String Broken2016Url = "http://textures.minecraft.net/texture/5c3ca7ee2a498f1b5d258d5fa927e63e433143add5538cf63b6a9b78ae735";

        if (!type.equals(CAPE) && textureURL.equals(Broken2012Url) || textureURL.equals(Broken2016Url)) return;
        if (!ValidateUUID.validateUUID(getProfile().getId().toString())) return;

        String Username = profile.getName();
        UUID Uuid = getProfile().getId();

        assert MinecraftClient.getInstance().player != null;
        boolean self = Boolean.parseBoolean(String.valueOf(Username.equals(MinecraftClient.getInstance().player.getName().getString())));
        //boolean notMe = (!self && !CapeCacher.config.notifyWhenCacheSelf);

        String[] capeInfo = IdentifyCapeType.CapeIdentifier(textureURL);



            /*
            if (!(capeName.equals("Not a vanilla cape!")) && CapeCacher.config.notifyCapeCached && notNull) {
                if (notMe){
                    MinecraftClient.getInstance().player.sendMessage(Text.of(("§f§l[§cCAPES.ME§f§l] §e-> " + "§fCached User §a" + Username) + " §e: §f" + capeName),false);
                    if (CapeCacher.config.notifySound) {
                        MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_CAT_PURREOW, 2f, 1f);
                    }
                }

                System.out.println("Looked up " + Username + ". Which has the UUID of : " + Uuid);
                System.out.println(capeURL);
            }
             */
    }
}
