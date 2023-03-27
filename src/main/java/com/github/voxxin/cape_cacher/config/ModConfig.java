package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static com.github.voxxin.cape_cacher.config.ModMenu.*;

@Config(name = CapeCacher.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject()
    public Default aDefault = new Default();

    @ConfigEntry.Gui.CollapsibleObject()
    public Capes capes = new Capes();

    // General - ALL
    public class Default {
        boolean notifyWhenCacheSelf = false;
        boolean notifyMessageAll = true;
        boolean notifySoundAll = true;
        @ConfigEntry.BoundedDiscrete(max = 5L, min = 0L)
        long notifySoundStrength = 2;
    }

    // Capes - ALL
    public class Capes {
        @ConfigEntry.Gui.CollapsibleObject
        public boolean migratorOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean vanillaOptions = true;

        // Capes - MineCons

        @ConfigEntry.Gui.CollapsibleObject
        public boolean mineCon2011Options = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mineCon2012Options = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mineCon2013Options = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mineCon2015Options = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mineCon2016Options = true;

        // Capes - "SPECIALS"

        @ConfigEntry.Gui.CollapsibleObject
        public boolean cobaltOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean scrollsChampOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean moderatorOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean translatorOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mapMakerOptions = true;

        // Capes - "UNIQUES"
        @ConfigEntry.Gui.CollapsibleObject
        public boolean translatorCNOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mojangOldOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mojangOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mojangStudiosOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean turtleOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean prismarineOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean dannyBStyleOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean julianClarkOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean cheapsh0tOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean mrMessiahOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean birthdayOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean valentineOptions = true;
        @ConfigEntry.Gui.CollapsibleObject
        public boolean millionthSaleOptions = true;

        @ConfigEntry.Gui.CollapsibleObject()
        public boolean unknownCapeOptions = true;
    }

    public static boolean getNotifyWhenCacheSelf() {
        return CapeCacher.config.aDefault.notifyWhenCacheSelf;
    }

    public static boolean getNotifyMessageAll() {
        return CapeCacher.config.aDefault.notifyMessageAll;
    }

    public static boolean getNotifySoundAll() {
        return CapeCacher.config.aDefault.notifySoundAll;
    }

    public static long getNotifySoundStrength() {
        return CapeCacher.config.aDefault.notifySoundStrength;
    }
}