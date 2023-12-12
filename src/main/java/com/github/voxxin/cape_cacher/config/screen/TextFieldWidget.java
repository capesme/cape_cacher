package com.github.voxxin.cape_cacher.config.screen;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextFieldWidget extends ClickableWidget {

    private int colour;
    private Text text;
    private final int[] pos;
    private final int[] size;
    public TextFieldWidget(int x, int y, int width, int height, Text message, int colour) {
        super(x, y, width, height, message);
        this.colour = colour;
        this.text = message;
        this.pos = new int[]{x, y};
        this.size = new int[]{width, height};
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(CapeCacher.client().textRenderer, text, this.pos[0] + this.size[0]/2, this.pos[1], this.colour);
    }
}
