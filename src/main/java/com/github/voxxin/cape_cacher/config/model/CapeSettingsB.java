package com.github.voxxin.cape_cacher.config.model;

public class CapeSettingsB {
    public Boolean value;
    public String key;

    public CapeSettingsB(Boolean value, String key) {
        this.value = value;
        this.key = key;
    }

    public enum CapeSettingsBTemplate {
        NOTIFY(
                true,
                "config.cape_cacher.cape.notify_when_found"
        ),
        NOTIFY_IN_CONSOLE(
                true,
                "config.cape_cacher.cape.notify_in_console"
        ),;
        public Boolean value;
        public String key;

        CapeSettingsBTemplate(Boolean value, String key) {
            this.value = value;
            this.key = key;
        }
    }
}
