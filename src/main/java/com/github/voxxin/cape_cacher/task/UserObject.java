package com.github.voxxin.cape_cacher.task;

import java.util.Objects;

public class UserObject {
    private final String userUUID;
    private final String capeTexture;

    public UserObject(String playerUUID, String capeURL) {
        this.userUUID = playerUUID;
        this.capeTexture = capeURL;
    }

    public boolean compare(UserObject userObject) {
        if (Objects.equals(this.userUUID, userObject.userUUID) && Objects.equals(this.capeTexture, userObject.capeTexture)) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserUUID() {
        return userUUID;
    }

    public String getCapeTexture() {
        return capeTexture;
    }
}
