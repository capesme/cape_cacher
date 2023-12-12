package com.github.voxxin.cape_cacher.task.util;

public class WidgetUtil {
    public static boolean overlaying(int mouseX, int mouseY, int aX, int aY, int bX, int bY) {
        return mouseX >= aX && mouseX <= bX && mouseY >= aY && mouseY <= bY;
    }
}
