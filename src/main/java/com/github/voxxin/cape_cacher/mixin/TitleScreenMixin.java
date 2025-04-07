package com.github.voxxin.cape_cacher.mixin;

import com.github.voxxin.cape_cacher.task.PingSite;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(at = @At("HEAD"), method = "init")
    private void init(CallbackInfo ci) {
        PingSite.cacheBaseCapeImages();
    }
}
