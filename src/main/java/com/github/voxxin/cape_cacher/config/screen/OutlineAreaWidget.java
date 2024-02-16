package com.github.voxxin.cape_cacher.config.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class OutlineAreaWidget extends ClickableWidget {
    private final int[] pos;
    private final int[] size;
    private final int colour;

    public OutlineAreaWidget(int x, int y, int width, int height, int colour) {
        super(x, y, width, height, Text.empty());
        this.pos = new int[]{x, y};
        this.size = new int[]{width, height};
        this.colour = colour;
    }

//    @Override
//    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
//        context.drawHorizontalLine(this.pos[0], this.pos[0] + this.size[0]-1, this.pos[1], this.colour);
//        context.drawHorizontalLine(this.pos[0], this.pos[0] + this.size[0]-1, this.pos[1] + this.size[1], this.colour);
//        context.drawVerticalLine(this.pos[0], this.pos[1] + this.size[1], this.pos[1], this.colour);
//        context.drawVerticalLine(this.pos[0] + this.size[0]-1, this.pos[1] + this.size[1], this.pos[1], this.colour);
//    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawHorizontalLine(this.pos[0], this.pos[0] + this.size[0]-1, this.pos[1], this.colour);
        context.drawHorizontalLine(this.pos[0], this.pos[0] + this.size[0]-1, this.pos[1] + this.size[1], this.colour);
        context.drawVerticalLine(this.pos[0], this.pos[1] + this.size[1], this.pos[1], this.colour);
        context.drawVerticalLine(this.pos[0] + this.size[0]-1, this.pos[1] + this.size[1], this.pos[1], this.colour);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
