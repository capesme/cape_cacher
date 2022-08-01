package com.github.voxxin.cape_cacher.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = CapeCacher.MODID)
public class ModConfig implements ConfigData {
    public boolean notifyCapeCached = true;
    public boolean notifyWhenCacheSelf = false;
    public boolean notifySound = true;
}