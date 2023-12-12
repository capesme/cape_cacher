package com.github.voxxin.cape_cacher.task.util;

import com.github.voxxin.cape_cacher.client.CapeCacher;
import org.lwjgl.glfw.GLFW;

public class KeyboardManager {
    private boolean rightBtnPressedLastTick = false;

    public boolean isMouseKey(int button) {
        boolean isPressed = GLFW.glfwGetMouseButton(CapeCacher.client().getWindow().getHandle(), button) == GLFW.GLFW_PRESS;

        boolean result = !isPressed && rightBtnPressedLastTick;
        rightBtnPressedLastTick = isPressed;

        return result;
    }
}

