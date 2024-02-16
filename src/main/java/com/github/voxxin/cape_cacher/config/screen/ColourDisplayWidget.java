package com.github.voxxin.cape_cacher.config.screen;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class ColourDisplayWidget extends ClickableWidget {

    private String colour;
    private final int[] pos;
    private final int[] size;
    public ColourDisplayWidget(int x, int y, int width, int height, Text message, String colour) {
        super(x, y, width, height, message);
        this.colour = colour;
        this.pos = new int[]{x, y};
        this.size = new int[]{width, height};
    }


//    @Override
//    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
//        context.drawTextWithShadow(CapeCacher.client().textRenderer, Text.translatable("config.cape_cacher.cape.general.color_example"), this.pos[0], this.pos[1], Integer.parseInt(this.colour.substring(2),16));
//    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(CapeCacher.client().textRenderer, Text.translatable("config.cape_cacher.cape.general.color_example"), this.pos[0], this.pos[1], Integer.parseInt(this.colour.substring(2),16));
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
