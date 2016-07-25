package com.freemotion.smashfruit.android.Misc;

/**
 * Created by clarkliao on 2016/7/25.
 */
public class TouchRegionConfig extends JsonConfig {

    private int centerX, centerY;
    private int width, height;

    public TouchRegionConfig() {
        centerX = centerY = width = height = 0;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getWith() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
