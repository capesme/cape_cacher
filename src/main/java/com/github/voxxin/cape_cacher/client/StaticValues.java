package com.github.voxxin.cape_cacher.client;

import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.task.util.UserObjectList;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaticValues {
    public static UserObjectList userCapeMap = new UserObjectList();
    public static JsonArray capesJsonObject = null;
    public static ArrayList<CapesObject> settingCapes = new ArrayList<>();

    public static Map<CapesObject, Boolean> capeMap = new HashMap<>();
}
