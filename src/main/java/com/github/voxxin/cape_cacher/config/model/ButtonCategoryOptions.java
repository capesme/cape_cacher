package com.github.voxxin.cape_cacher.config.model;

public enum ButtonCategoryOptions {
    CAPE(
            "config.cape_cacher.category.cape"
    ),
    GENERAL(
            "config.cape_cacher.category.general"
    );

    public final String key;

    ButtonCategoryOptions(String key) {
        this.key = key;
    }
}
