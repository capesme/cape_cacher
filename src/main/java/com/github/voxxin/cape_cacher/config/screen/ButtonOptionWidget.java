package com.github.voxxin.cape_cacher.config.screen;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import com.github.voxxin.cape_cacher.config.model.ButtonClickAction;
import com.github.voxxin.cape_cacher.config.model.ButtonOptionType;
import com.github.voxxin.cape_cacher.task.util.KeyboardManager;
import com.github.voxxin.cape_cacher.task.util.WidgetUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ButtonOptionWidget extends ClickableWidget {
    private final ButtonClickAction action;
    public final Text title;
    private final int[] pos;
    private final int[] size;
    private final ButtonOptionType buttonOptionType;

    private final Identifier texture;
    private final Identifier overlayTexture;

    private boolean buttonWasClicked = false;
    private final KeyboardManager KEYBOARD_MANAGER = new KeyboardManager();

    public ButtonOptionWidget(int x, int y, int width, int height, Text title, ButtonClickAction action, ButtonOptionType buttonOptionType) {
        super(x, y, width, height, title);
        this.action = action;
        this.title = title;
        this.pos = new int[]{x, y};
        this.size = new int[]{width, height};
        if (buttonOptionType == null) {
            this.buttonOptionType = null;
            this.texture = null;
            this.overlayTexture = null;
        } else {
            this.buttonOptionType = buttonOptionType;
            this.texture = this.buttonOptionType.getState() ? this.buttonOptionType.getType().recourseLocation[1] : this.buttonOptionType.getType().recourseLocation[0];
            this.overlayTexture = this.buttonOptionType.getState() ? this.buttonOptionType.getType().recourseLocationOverlay[1] : this.buttonOptionType.getType().recourseLocationOverlay[0];
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int sizeX = this.size[0] / 2;
        int sizeY = this.size[1] / 2;
        boolean overlaying = WidgetUtil.overlaying(mouseX, mouseY, this.pos[0] - sizeX, this.pos[1] - sizeY, this.pos[0] + sizeX, this.pos[1] + sizeY);

        if (KEYBOARD_MANAGER.isMouseKey(GLFW.GLFW_MOUSE_BUTTON_LEFT)
                && overlaying) {
            clicked();
        }

        if (this.buttonOptionType != null) {
            if (!overlaying) {
                context.drawTexture(this.texture, this.pos[0] - sizeX, this.pos[1] - sizeY, 0, 0, 20, 20, 20, 20);
            } else {
                context.drawTexture(this.overlayTexture, this.pos[0] - sizeX, this.pos[1] - sizeY, 0, 0, 20, 20, 20, 20);
            }
        }

        // Text
        context.drawTextWithShadow(CapeCacher.client().textRenderer, title.getString(), this.pos[0] + sizeX + 10, this.pos[1] - sizeY / 2, 0xFFFFFF);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    private void clicked() {
        this.action.run();
        this.buttonWasClicked = true;
        this.playDownSound(CapeCacher.client().getSoundManager());
    }

    public boolean buttonWasClicked() {
        if (this.buttonWasClicked) {
            this.buttonWasClicked = false;
            return true;
        }
        return false;
    }
}
