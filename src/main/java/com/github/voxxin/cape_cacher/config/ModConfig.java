package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.model.CapeSettingsB;
import com.github.voxxin.cape_cacher.config.model.CapeSettingsI;
import com.github.voxxin.cape_cacher.config.model.CapesObject;
import com.github.voxxin.cape_cacher.config.model.ModSettingsModel;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.ColorHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ModConfig {

    private static final File modDir = FabricLoader.getInstance().getConfigDir().resolve(CapeCacher.MODID).toFile();
    private static final File  configDir = new File(modDir, "config");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ModConfig() { init(); }

    public static void init() {
        try {
            configDir.mkdirs();

            File[] configs = configDir.listFiles();
            assert configs != null;
            Optional<File> capeConfigFile = Arrays.stream(configs).filter(file -> file.getName().equals("capes.json")).findFirst();
            Optional<File> modConfigFile = Arrays.stream(configs).filter(file -> file.getName().equals("mod.json")).findFirst();

            if (capeConfigFile.isPresent()) {
                importCapeConfig(capeConfigFile.get()); }
            else { exportCapeConfig(); }

            if (modConfigFile.isPresent()) {
                importModConfig(modConfigFile.get()); }
            else { exportModConfig(); }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void importCapeConfig(File file) {
        try {
            JsonElement JSONedElement1;

            if (file != null) {
                FileReader fileReader = new FileReader(file);
                JSONedElement1 = gson.fromJson(fileReader, JsonElement.class);
            } else return;

            JsonArray JSONedArray = JSONedElement1.getAsJsonArray();

            for (JsonElement JSONedElement2 : JSONedArray) {
                JsonObject JSONedFile = JSONedElement2.getAsJsonObject();

                JsonArray altsArray = JSONedFile.get("alts").getAsJsonArray();
                ArrayList<String> alts = altsArray == null ? new ArrayList<>() : new Gson().fromJson(altsArray, new TypeToken<ArrayList<String>>() {}.getType());


                CapesObject cape = new CapesObject(
                        JSONedFile.get("url").getAsString(),
                        JSONedFile.get("type").getAsString(),
                        JSONedFile.get("colour").getAsInt(),
                        JSONedFile.get("name").getAsString(),
                        alts
                );

                for (CapeSettingsI.CapeSettingsITemplate setting : CapeSettingsI.CapeSettingsITemplate.values()) {
                    String value = String.format("0x%06X",(0xFFFFFF & JSONedFile.get("colour").getAsInt()));
                    cape.setSettingI(setting.key, value);
                }

                JsonArray settingsArray = JSONedFile.get("settings").getAsJsonArray();
                for (int i = 0; i < settingsArray.size(); i++) {
                    JsonObject setting = settingsArray.get(i).getAsJsonObject();
                    String key = setting.entrySet().iterator().next().getKey();
                    boolean value = setting.entrySet().iterator().next().getValue().getAsBoolean();

                    cape.setSettingB(key, value);
                }

                StaticValues.settingCapes.add(cape);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading cape config file: " + e.getMessage());
        }
    }

        public static void exportCapeConfig() {
        ArrayList<CapesObject> capes = StaticValues.settingCapes;

        if (capes.isEmpty()) {
            setupCapeConfig();
            return;
        }

        JsonArray capesObject = new JsonArray();
        for (CapesObject cape : capes) {
            String capeName = cape.title;
            String capeURL = cape.URL;
            String capeType = cape.type;
            int capeColour = cape.colour;
            ArrayList<String> capeAlts = cape.alts;

            JsonObject capeObject = new JsonObject();
            capeObject.addProperty("name", capeName);
            capeObject.addProperty("url", capeURL);
            capeObject.addProperty("type", capeType);
            capeObject.addProperty("colour", capeColour);

            JsonArray capeSettingsArray = new JsonArray();

            for (CapeSettingsB.CapeSettingsBTemplate temp0 : CapeSettingsB.CapeSettingsBTemplate.values()) {
                JsonObject settingObject = new JsonObject();
                if (cape.getSettingB(temp0.key) != null) {
                    CapeSettingsB setting = cape.getSettingB(temp0.key);
                    settingObject.addProperty(setting.key, setting.value);
                } else {
                    settingObject.addProperty(temp0.key, true);
                }
                capeSettingsArray.add(settingObject);
            }

            JsonArray altsArray = new JsonArray();
            for (String alt : capeAlts) {
                altsArray.add(alt);
            }

            capeObject.add("settings", capeSettingsArray);
            capeObject.add("alts", altsArray);

            capesObject.add(capeObject);
        }

        String capesJson = gson.toJson(capesObject);
        File capesFile = new File(configDir, "capes.json");

        try {
            capesFile.createNewFile();
            FileWriter writer = new FileWriter(capesFile);
            writer.write(capesJson);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupCapeConfig() {
        for (JsonElement capeObj : StaticValues.capesJsonObject) {
            JsonObject cape = capeObj.getAsJsonObject();
            String capeName = cape.get("title").getAsString();
            String capeURL = cape.get("url").getAsString();
            String capeType = cape.get("type").getAsString();
            int capeColour;
            if (cape.has("colour") && cape.get("colour").getAsInt() != 0) {
                capeColour = cape.get("colour").getAsInt();
            } else {
                int hexColor = 0x718bd4;
                int red = (hexColor >> 16) & 0xFF;
                int green = (hexColor >> 8) & 0xFF;
                int blue = hexColor & 0xFF;
                capeColour = ColorHelper.Argb.getArgb(255, red, green, blue);
            }
            ArrayList<String> capeAlts = new ArrayList<>();

            JsonObject capeObject = new JsonObject();
            capeObject.addProperty("name", capeName);
            capeObject.addProperty("url", capeURL);
            capeObject.addProperty("type", capeType);
            capeObject.addProperty("colour", capeColour);

            JsonArray capeSettingsArray = new JsonArray();
            for (CapeSettingsB.CapeSettingsBTemplate setting : CapeSettingsB.CapeSettingsBTemplate.values()) {
                JsonObject settingObject = new JsonObject();
                settingObject.addProperty(setting.key, setting.value);
                capeSettingsArray.add(settingObject);
            }

            JsonArray altsArray = new JsonArray();
            for (String alt : capeAlts) {
                altsArray.add(alt);
            }

            capeObject.add("settings", capeSettingsArray);
            capeObject.add("alts", altsArray);

            StaticValues.settingCapes.add(gson.fromJson(capeObject, CapesObject.class));
        }

        exportCapeConfig();
    }

    public static void importModConfig(File file) {
        try {
            JsonElement JSONedElement1;

            if (file != null) {
                FileReader fileReader = new FileReader(file);
                JSONedElement1 = gson.fromJson(fileReader, JsonElement.class);
            } else return;

            JsonObject JSONedObject = JSONedElement1.getAsJsonObject();

            for (ModSettingsModel setting : ModSettingsModel.values()) {
                if (JSONedObject.has(setting.key)) {
                    setting.value = JSONedObject.get(setting.key).getAsString();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading mod config file: " + e.getMessage());
        }
    }

    public static void exportModConfig() {
        JsonObject modConfig = new JsonObject();

        for (ModSettingsModel setting : ModSettingsModel.values()) {
            modConfig.addProperty(setting.key, setting.value);
        }

        String modConfigJson = gson.toJson(modConfig);
        File modConfigFile = new File(configDir, "mod.json");

        try {
            modConfigFile.createNewFile();
            FileWriter writer = new FileWriter(modConfigFile);
            writer.write(modConfigJson);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}