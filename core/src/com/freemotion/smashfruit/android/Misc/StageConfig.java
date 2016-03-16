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
    private String configFile, configName;
    private boolean active;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int x) {
        positionX = x;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int y) {
        positionY = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        width = w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        height = h;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float x) {
        scaleX = x;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float y) {
        scaleY = y;
    }

    public String getAtlas() {
        return atlas;
    }

    public void setAtlas(String atlas) {
        this.atlas = atlas;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDclass() {
        return dclass;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getConfigName() {
        return configName;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        active = isActive;
    }
}
