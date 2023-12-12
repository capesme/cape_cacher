package com.github.voxxin.cape_cacher.config.model;

public class ButtonOptionType {

    private final ButtonOptionTypeModel type;
    private final Boolean state;

    public ButtonOptionType(ButtonOptionTypeModel type, Boolean state) {
        this.type = type;
        this.state = state;
    }

    public ButtonOptionTypeModel getType() {
        return type;
    }

    public Boolean getState() {
        return state;
    }
}
