package com.github.voxxin.cape_cacher.config.model;

public enum ModSettingsModel {

    NOTIFY_WHEN_ANY (
            "true",
            "config.cape_cacher.general.notify_when_any"
    ),
    NOTIFY_WITH_SOUND (
            "true",
            "config.cape_cacher.general.notify_with_sound"
    ),
    NOTIFY_WHEN_SELF (
            "true",
            "config.cape_cacher.general.notify_when_self"
    ),
    NOTIFY_SOUND_STRENGTH(
            "0.5",
            "config.cape_cacher.general.notify_sound_strength"
    );

    public String value;
    public String key;

    ModSettingsModel(String value, String key) {
        this.value = value;
        this.key = key;
    }

}
