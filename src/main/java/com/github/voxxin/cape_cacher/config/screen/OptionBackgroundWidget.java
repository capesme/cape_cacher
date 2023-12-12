package com.github.voxxin.cape_cacher.config.screen;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.task.util.WidgetUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class OptionBackgroundWidget extends ClickableWidget {
    private final int[] pos;
    private final int[] size;

    public OptionBackgroundWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.pos = new int[]{x, y};
        this.size = new int[]{width, height};
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(this.pos[0], this.pos[1], this.pos[0] + this.size[0], this.pos[1] + this.size[1], 0x80000000);
    }
}
