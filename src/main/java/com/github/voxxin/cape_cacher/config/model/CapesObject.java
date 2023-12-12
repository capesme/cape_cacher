package com.github.voxxin.cape_cacher.config.model;

import net.minecraft.text.TextColor;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CapesObject {
    public final String URL;
    public final String type;
    public int colour;
    public final String title;
    public final ArrayList<String> alts;
    public HashMap<String, CapeSettingsB> settingsB;
    public final HashMap<String, CapeSettingsI> settingColour;

    public CapesObject(@NotNull String URL, @NotNull String type, int colour, @NotNull String title, ArrayList<String> alts) {
        this.URL = URL;
        this.type = type;
        this.title = title;

        if (colour == 0) {
            this.colour = 0x5865F2;
        } else {
            int r = (colour >> 16) & 0xFF;
            int g = (colour >> 8) & 0xFF;
            int b = colour & 0xFF;
            this.colour = ColorHelper.Argb.getArgb(255, r, g, b);
        }

        if (alts != null) {
            this.alts = alts;
        } else this.alts = new ArrayList<>();

        this.settingsB = new HashMap<>();
        this.settingColour = new HashMap<>();
    }

    public CapeSettingsB getSettingB(String key) {
        if (settingsB.containsKey(key)) return settingsB.get(key);
        else return new CapeSettingsB(true, key);
    }

    public CapeSettingsI getSettingI(String key) {
        return settingColour.get(key);
    }

    public void setSettingB(String key, Boolean value) {
        if (settingsB.containsKey(key)) settingsB.get(key).value = value;
        else settingsB.put(key, new CapeSettingsB(value, key));
    }

    public void setSettingI(String key, String value) {
        if (settingColour.containsKey(key)) settingColour.get(key).colour = value;
        else settingColour.put(key, new CapeSettingsI(value, key));
    }
}

