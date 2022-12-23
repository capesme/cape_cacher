package com.github.voxxin.cape_cacher.task;

import java.util.regex.Pattern;

public class ValidateUUID {

    public static Boolean validateUUID(String userUUID) {
        boolean validUUID = false;

        Pattern UUID_REGEX =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        validUUID = (UUID_REGEX.matcher(userUUID).matches());

        return validUUID;
    }
}
