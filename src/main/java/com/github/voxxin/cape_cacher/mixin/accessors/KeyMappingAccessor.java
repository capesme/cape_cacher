package com.github.voxxin.cape_cacher.mixin.accessors;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
    @Accessor("CATEGORY_SORT_ORDER")
    static Map<String, Integer> cc_getCategoryMap() {
        throw new AssertionError();
    }

    @Accessor("key")
    InputConstants.Key cc_getBoundKey();
}
