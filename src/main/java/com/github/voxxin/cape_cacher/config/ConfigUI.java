package com.github.voxxin.cape_cacher.config;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.client.StaticValues;
import com.github.voxxin.cape_cacher.config.model.*;
import com.github.voxxin.cape_cacher.config.screen.ButtonOptionWidget;
import com.github.voxxin.cape_cacher.config.screen.ColourDisplayWidget;
import com.github.voxxin.cape_cacher.config.screen.OptionBackgroundWidget;
import com.github.voxxin.cape_cacher.config.screen.OutlineAreaWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.github.voxxin.cape_cacher.client.StaticValues.capeMap;

public class ConfigUI extends Screen {
    private final Screen parent;
    private double scrollAmount = 0;
    private double screenSpacing = 0;
    private int minScrollY = 0;
    private int maxScrollY = 0;
    private boolean resetAndClear = false;

    private static ButtonCategoryOptions currentCategory = ButtonCategoryOptions.CAPE;

    private final Map<CapesObject, Pair<ColourDisplayWidget, TextFieldWidget>> textWidgets = new HashMap<>();
    private final ArrayList<ButtonOptionWidget> buttonWidgets = new ArrayList<>();
    protected ConfigUI(Screen parent) {
        super(Text.translatable("config.cape_cacher.general.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int padding = 60;
        int buttonSize = 20;
        int textBarLength = sizeWithText(Text.translatable("config.cape_cacher.cape.cape_color").getString()) >= 60 ? (int) sizeWithText(Text.translatable("config.cape_cacher.cape.cape_colour").getString()) : 60;
        int optionSpacing = CapeSettingsB.CapeSettingsBTemplate.values().length * 60 + CapeSettingsI.CapeSettingsITemplate.values().length * 60;

        int spacingY = 0;
        int previousOptionsOpened = 0;

        screenSpacing += scrollAmount;
        scrollAmount = 0;

        maxScrollY = -maxScrollY;

        if (screenSpacing >= minScrollY) screenSpacing = minScrollY;
//        if (screenSpacing <= maxScrollY) screenSpacing = maxScrollY;

        // Categories

        int spacingHorizontal0 = this.width / ButtonCategoryOptions.values().length;
        int screenSpacingVertical0 = 20;
        int screenHorz0 = 0;
        int screenHorz1 = spacingHorizontal0;

        handleCategories(spacingHorizontal0, screenSpacingVertical0, screenHorz0, screenHorz1, buttonSize);

        if (currentCategory == ButtonCategoryOptions.CAPE) {
            handleCapes(padding, buttonSize, textBarLength, optionSpacing, spacingY, previousOptionsOpened);
        }

        if (currentCategory == ButtonCategoryOptions.GENERAL) {
            handleGeneralSettings(padding, buttonSize, textBarLength, optionSpacing, spacingY, previousOptionsOpened);
        }


        if (resetAndClear) {
            resetAndClear = false;
            this.clearAndInit();
        }
    }

    private void handleCategories(int spacingHorizontal0, int screenSpacingVertical0, int screenHorz0, int screenHorz1, int buttonSize) {
        for (ButtonCategoryOptions category : ButtonCategoryOptions.values()) {

            ButtonOptionWidget buttonWidget = new ButtonOptionWidget(screenHorz0 + screenHorz1/2, (int) (screenSpacingVertical0 + screenSpacing), screenHorz1 , buttonSize, Text.literal(""), () -> {
                currentCategory = category;
                resetAndClear = true;
                screenSpacing = 0;
                minScrollY = 0;
                maxScrollY = 0;
            }, null);
            OptionBackgroundWidget backgroundWidget = new OptionBackgroundWidget(screenHorz0, (int) (screenSpacingVertical0 + screenSpacing - (buttonSize/2 + buttonSize/4)), spacingHorizontal0, buttonSize);

            if (!buttonWidgets.contains(buttonWidget)) buttonWidgets.add(buttonWidget);
            this.addDrawable(backgroundWidget);
            this.addDrawable(buttonWidget);
            this.addDrawable(new com.github.voxxin.cape_cacher.config.screen.TextFieldWidget(screenHorz0, (int) (screenSpacingVertical0 + screenSpacing - buttonSize/2), spacingHorizontal0, buttonSize, Text.translatable(category.key), Color.WHITE.getRGB()));
            if (currentCategory == category) this.addDrawable(new OutlineAreaWidget(screenHorz0, (int) (screenSpacingVertical0 + screenSpacing - (buttonSize/2 + buttonSize/4)), spacingHorizontal0, buttonSize, Color.WHITE.getRGB()));

            screenHorz0 = screenHorz1;
            screenHorz1 += screenHorz1;
        }
    }
    private void handleCapes(int padding, int buttonSize, int textBarLength, int optionSpacing, int spacingY, int previousOptionsOpened) {
        for (CapesObject configModel : StaticValues.settingCapes) {
            assert CapeCacher.client().currentScreen != null;

            capeMap.putIfAbsent(configModel, false);
            boolean optionsOpened = capeMap.get(configModel);

            int screenSpacingHorizontal = 20;
            int screenSpacingVertical = (int) (padding + (30 * spacingY) + screenSpacing + (previousOptionsOpened * optionSpacing));

            // Button
            ButtonOptionWidget buttonWidget = new ButtonOptionWidget(screenSpacingHorizontal, screenSpacingVertical, buttonSize, buttonSize, Text.translatable(configModel.title), () -> {
                for (CapesObject configModel0 : StaticValues.settingCapes) {

                    if (configModel0.title.equals(Text.translatable(configModel.title).getString())) {
                        capeMap.replace(configModel0, !capeMap.get(configModel0));
                    }
                }
            }, new ButtonOptionType(ButtonOptionTypeModel.DROPDOWN, capeMap.get(configModel)));

            if (!buttonWidgets.contains(buttonWidget)) buttonWidgets.add(buttonWidget);

            // Options
            if (optionsOpened) {
                float largestText = Arrays.stream(CapeSettingsB.CapeSettingsBTemplate.values())
                        .map(option -> sizeWithText(Text.translatable(option.key).getString()))
                        .max(Float::compare)
                        .orElse(0f);

                float largestTextBtnPlace = sizeWithText(Text.translatable("config.cape_cacher.cape.cape_color").getString());

                largestText += 20 + buttonSize + 10; // Adjustments for padding
                largestTextBtnPlace += 20 + 10;

                if (largestText < largestTextBtnPlace) largestText = largestTextBtnPlace;

                this.addDrawable(new OptionBackgroundWidget(screenSpacingHorizontal / 2, screenSpacingVertical - buttonSize, (int) Math.max(largestText, 60), optionSpacing + buttonSize));

                maxScrollY += optionSpacing + buttonSize;

                int optionIndex = 0;
                for (CapeSettingsB.CapeSettingsBTemplate option : CapeSettingsB.CapeSettingsBTemplate.values()) {
                    optionIndex++;
                    ButtonOptionWidget optionWidget = new ButtonOptionWidget(screenSpacingHorizontal + 10, screenSpacingVertical + (optionIndex * 40), buttonSize, buttonSize, Text.translatable(option.key),
                            () -> {
                                for (CapesObject configModel0 : StaticValues.settingCapes) {
                                    if (configModel0.title.equals(configModel.title)) {
                                        configModel0.setSettingB(option.key, !configModel0.getSettingB(option.key).value);
                                        resetAndClear = true;
                                    }
                                }
                            }, new ButtonOptionType(ButtonOptionTypeModel.CHECKBOX, StaticValues.settingCapes.stream().filter(configModel0 -> configModel0.title.equals(configModel.title)).findFirst().orElse(null).getSettingB(option.key).value));


                    if (buttonWidgets.stream().noneMatch(widget -> widget.title.getString().equals(optionWidget.title.getString()))) {
                        buttonWidgets.add(optionWidget);
                    }

                    this.addDrawable(optionWidget);
                }


                for (CapeSettingsI.CapeSettingsITemplate option0 : CapeSettingsI.CapeSettingsITemplate.values()) {
                    optionIndex++;
                    CapeSettingsI option = configModel.getSettingI(option0.key);

                    TextFieldWidget colourValue = new TextFieldWidget(CapeCacher.client().textRenderer, screenSpacingHorizontal / 2 + 10, screenSpacingVertical + (optionIndex * 40), textBarLength, buttonSize,
                            Text.literal("").append(Text.translatable(option.colour)));
                    colourValue.setText(String.valueOf(option.colour));
                    colourValue.setMaxLength(8);

                    ColourDisplayWidget colourDisplayWidget = new ColourDisplayWidget(screenSpacingHorizontal / 2 + textBarLength + buttonSize, screenSpacingVertical + (optionIndex * 40) + buttonSize / 4, textBarLength, buttonSize / 2, Text.empty(), option.colour);

                    Pair<ColourDisplayWidget, TextFieldWidget> combinedMap = Pair.of(colourDisplayWidget, colourValue);

                    if (textWidgets.keySet().stream().anyMatch(key -> key.title.equals(configModel.title))) {
                        textWidgets.replace(configModel, combinedMap);
                    } else {
                        textWidgets.put(configModel, combinedMap);
                    }

                    colourValue.setChangedListener(textChangedForWidget -> editText(textChangedForWidget, colourDisplayWidget, configModel));

                    this.addDrawable(colourDisplayWidget);
                    this.addDrawable(colourValue);
                    this.addDrawable(new com.github.voxxin.cape_cacher.config.screen.TextFieldWidget(screenSpacingHorizontal, screenSpacingVertical + (optionIndex * 40) - buttonSize / 2, textBarLength, buttonSize / 2,
                            Text.translatable(option.key), Color.WHITE.getRGB()));
                    this.addSelectableChild(colourValue);
                }
            }

            this.addDrawable(buttonWidget);

            if (optionsOpened) previousOptionsOpened++;
            spacingY++;
        }
    }

    private void handleGeneralSettings(int padding, int buttonSize, int textBarLength, int optionSpacing, int spacingY, int previousOptionsOpened) {
        for (ModSettingsModel setting : ModSettingsModel.values()) {
            assert CapeCacher.client().currentScreen != null;

            int screenSpacingHorizontal = 20;
            int screenSpacingVertical = (int) (padding + (30 * spacingY) + screenSpacing + (previousOptionsOpened * optionSpacing));
            Object value = null;

            int textBarLengthLocal = 60;

            boolean isBoolean = setting.value.equals("true") || setting.value.equals("false");
            boolean isNumber = !isBoolean && setting.value.matches("-?\\d+(\\.\\d+)?");

            if (isBoolean) value = Boolean.parseBoolean(setting.value);
            if (isNumber) value = Float.valueOf(setting.value);
            if (value == null) return;

            if (isBoolean) {
                ButtonOptionWidget optionWidget = new ButtonOptionWidget(screenSpacingHorizontal, screenSpacingVertical, buttonSize, buttonSize, Text.translatable(setting.key),
                        () -> {
                            for (ModSettingsModel setting1 : ModSettingsModel.values()) {
                                if (setting1.key.equals(setting.key)) {
                                    setting1.value = String.valueOf(!Boolean.parseBoolean(setting1.value));
                                }
                            }

                            ModConfig.exportModConfig();

                            resetAndClear = true;
                        }, new ButtonOptionType(ButtonOptionTypeModel.CHECKBOX, Boolean.parseBoolean(setting.value)));


                buttonWidgets.add(optionWidget);
                this.addDrawable(optionWidget);
            }

            if (isNumber) {
                TextFieldWidget optionWidget = new TextFieldWidget(CapeCacher.client().textRenderer, screenSpacingHorizontal - buttonSize/2, screenSpacingVertical, textBarLengthLocal, buttonSize,
                        Text.translatable(setting.key));
                optionWidget.setText(setting.value);
                optionWidget.setMaxLength(3);

                optionWidget.setChangedListener(textChangedForWidget -> {
                    textChangedForWidget = textChangedForWidget.replaceAll("[^0-9.]", "");
                    if (!textChangedForWidget.matches("[0-9.]")) return;
                    for (ModSettingsModel setting1 : ModSettingsModel.values()) {
                        if (setting1.key.equals(setting.key)) {
                            setting1.value = textChangedForWidget;
                        }
                    }
                });

                this.addDrawable(new com.github.voxxin.cape_cacher.config.screen.TextFieldWidget(screenSpacingHorizontal + (textBarLengthLocal*2) - buttonSize, screenSpacingVertical + buttonSize/4, textBarLengthLocal, buttonSize,
                        Text.translatable(setting.key), Color.WHITE.getRGB()));
                this.addDrawable(optionWidget);
                this.addSelectableChild(optionWidget);
            }

            spacingY++;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackgroundTexture(context);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        super.tick();

        for (ButtonOptionWidget buttonWidget : buttonWidgets) {
            if (buttonWidget.buttonWasClicked()) {
                resetAndClear = true;
            }
        }

        if (resetAndClear) {
            resetAndClear = false;
            this.clearAndInit();
        }
    }

    @Override
    public void close() {
        ModConfig.exportCapeConfig();
        CapeCacher.client().setScreen(parent);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        double accumulatedDelta = 0.0;
        double smoothingFactor = 10;

        accumulatedDelta += verticalAmount;
        double smoothedDelta = accumulatedDelta * smoothingFactor;
        accumulatedDelta += smoothedDelta;

        scrollAmount = accumulatedDelta;
        this.clearAndInit();
        return super.mouseScrolled(mouseX, mouseY, mouseY, verticalAmount);
    }


//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
//        double accumulatedDelta = 0.0;
//        double smoothingFactor = 10;
//
//        accumulatedDelta += amount;
//        double smoothedDelta = accumulatedDelta * smoothingFactor;
//        accumulatedDelta += smoothedDelta;
//
//        scrollAmount = accumulatedDelta;
//        this.clearAndInit();
//        return super.mouseScrolled(mouseX, mouseY, amount);
//    }

    private float sizeWithText(String text) {
        return CapeCacher.client().textRenderer.getTextHandler().getWidth(text);
    }

    private void editText(String textChangedForWidget, ColourDisplayWidget colourDisplayWidget, CapesObject configModel) {
        if (!textChangedForWidget.startsWith("0x")) textChangedForWidget = "0x";
        String hexPart = textChangedForWidget.substring(2).replaceAll("[^0-9A-Fa-f]", "");

        while (hexPart.length() < 6) {
            hexPart = "0" + hexPart;
        }
        hexPart = hexPart.substring(0, Math.min(hexPart.length(), 6));
        textChangedForWidget = "0x" + hexPart;

        configModel.colour = Integer.parseInt(hexPart, 16);
        for (CapeSettingsI capeSettingsIMap : configModel.settingColour.values()) {
            configModel.setSettingI(capeSettingsIMap.key, textChangedForWidget);
        }
        colourDisplayWidget.setColour(textChangedForWidget);
    }
}
