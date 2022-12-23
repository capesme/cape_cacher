package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;

import static com.github.voxxin.cape_cacher.config.ModMenu.*;

@Config(name = CapeCacher.MODID)
public class ModConfig implements ConfigData {

    // General - ALL
    @Category("default")
    public static boolean notifyWhenCacheSelf = false;
    public static boolean notifyMessageAll = true;
    public static boolean notifySoundAll = true;
    @ConfigEntry.BoundedDiscrete(max = 5L, min = 0L)
    public static long notifySoundStrength = 2;

    // Capes - ALL
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings migratorOptions = new ModMenu.capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings vanillaOptions = new capeSettings();

    // Capes - MineCons

    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mineCon2011Options = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mineCon2012Options = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mineCon2013Options = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mineCon2015Options = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mineCon2016Options = new capeSettings();

    // Capes - "SPECIALS"

    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings cobaltOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings scrollsChampOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings moderatorOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings translatorOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mapMakerOptions = new capeSettings();

    // Capes - "UNIQUES"
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings translatorCNOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mojangOldOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mojangOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mojangStudiosOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings turtleOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings prismarineOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings dannyBStyleOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings julianClarkOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings cheapsh0tOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings mrMessiahOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings birthdayOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject
    public static capeSettings valentineOptions = new capeSettings();
    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject()
    public static capeSettings millionthSaleOptions = new capeSettings();

    @Category("Capes")
    @ConfigEntry.Gui.CollapsibleObject()
    public static capeSettings unknownCapeOptions = new capeSettings();

}