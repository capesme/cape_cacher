package com.github.voxxin.cape_cacher.config.model;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import net.minecraft.util.Identifier;

public enum ButtonOptionTypeModel {
    DROPDOWN(
            "dropdown",
            new Identifier[]{new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/dropdown.png"),
                    new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/dropdown_open.png")},

            new Identifier[]{new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/dropdown_overlay.png"),
                    new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/dropdown_open_overlay.png")}

    ),
    CHECKBOX(
            "checkbox",
            new Identifier[]{new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/checkbox.png"),
                    new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/checkbox_selected.png")},

            new Identifier[]{new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/checkbox_overlay.png"),
                    new Identifier(CapeCacher.MODID, "textures/gui/sprites/container/checkbox_selected_overlay.png")}
    );

    public final String type;
    public final Identifier[] recourseLocation;
    public final Identifier[] recourseLocationOverlay;

    ButtonOptionTypeModel(String type, Identifier[] recourseLocation, Identifier[] recourseLocationOverlay) {
        this.type = type;
        this.recourseLocation = recourseLocation;
        this.recourseLocationOverlay = recourseLocationOverlay;
    }
}
