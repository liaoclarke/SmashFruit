package com.freemotion.smashfruit.android.Misc;

import java.util.ArrayList;

/**
 * Created by liaoclark on 2016/3/12.
 */
public class StageConfig extends JsonConfig {

    private String dclass;
    private int positionX, positionY;
    private int width, height;
    private float scaleX, scaleY;
    private float rotation;
    private String atlas, region;


    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public String getAtlas() {
        return atlas;
    }

    public String getRegion() {
        return region;
    }

    public String getDclass() {
        return dclass;
    }
}
