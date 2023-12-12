package com.github.voxxin.cape_cacher.config.model;

public class CapeSettingsI {
    public String colour;
    public String key;

    public CapeSettingsI(String value, String key) {
        this.colour = value;
        this.key = key;
    }

    public enum CapeSettingsITemplate {
        CAPE_COLOUR(
                0x5865F2,
                "config.cape_cacher.cape.cape_color"
        );
        public int value;
        public String key;

        CapeSettingsITemplate(int value, String key) {
            this.value = value;
            this.key = key;
        }
    }
}
