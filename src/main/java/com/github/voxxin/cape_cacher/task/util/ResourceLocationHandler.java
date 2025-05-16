package com.github.voxxin.cape_cacher.task.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHandler {
    public static ResourceLocation make(String namespace, String path) {
        //? if >=1.21 {
         /*return ResourceLocation.fromNamespaceAndPath(namespace, path);
        *///?} else if >=1.20 {
        return new ResourceLocation(namespace, path);
        //?}
    }
}
