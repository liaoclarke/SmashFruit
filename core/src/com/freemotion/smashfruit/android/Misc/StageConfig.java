package com.freemotion.smashfruit.android.Misc;

import java.util.ArrayList;

/**
 * Created by liaoclark on 2016/3/12.
 */
public class StageConfig extends JsonConfig {

    private String dclass;
    private int origPositionX, origPositionY;
    private int positionX, positionY;
    private int width, height;
    private float scaleX, scaleY;
    private float rotation;
    private String atlas, region, selected;
    private String configFile, configName;
    private ArrayList<TransitionConfig> transition;
    private boolean active;

    public int getOrigPositionX() {
        return origPositionX;
    }

    public StageConfig setOrigPositionX(int x) {
        origPositionX = x;
        return this;
    }

    public int getOrigPositionY() {
        return origPositionY;
    }

    public StageConfig setOrigPositionY(int y) {
        origPositionY = y;
        return this;
    }

    public int getPositionX() {
        return positionX;
    }

    public StageConfig setPositionX(int x) {
        positionX = x;
        return this;
    }

    public int getPositionY() {
        return positionY;
    }

    public StageConfig setPositionY(int y) {
        positionY = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public StageConfig setWidth(int w) {
        width = w;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public StageConfig setHeight(int h) {
        height = h;
        return this;
    }

    public float getScaleX() {
        return scaleX;
    }

    public StageConfig setScaleX(float x) {
        scaleX = x;
        return this;
    }

    public float getScaleY() {
        return scaleY;
    }

    public StageConfig setScaleY(float y) {
        scaleY = y;
        return this;
    }

    public String getAtlas() {
        return atlas;
    }

    public StageConfig setAtlas(String atlas) {
        this.atlas = atlas;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public StageConfig setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getSelected() {
        return selected;
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

    public StageConfig setActive(boolean isActive) {
        active = isActive;
        return this;
    }

    public ArrayList<TransitionConfig> getTransition() {
        return transition;
    }
}
