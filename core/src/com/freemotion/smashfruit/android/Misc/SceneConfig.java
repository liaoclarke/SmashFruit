package com.freemotion.smashfruit.android.Misc;

/**
 * Created by liaoclark on 2016/3/4.
 */
public class SceneConfig extends JsonConfig {

    private float origX, origY;
    private float positionX, positionY;
    private float scaleX, scaleY;
    private float rotation;
    private String atlas, region;

    public SceneConfig() {
        origX = 0f;
        origY = 0f;
        positionX = 0f;
        positionY = 0f;
        scaleX = 0f;
        scaleY = 0f;
        rotation = 0f;
        atlas = null;
        region = null;
    }

    public float getOrigX() {
        return origX;
    }

    public float getOrigY() {
        return origY;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getRotation() {
        return rotation;
    }

    public String getAtlas() {
        return atlas;
    }

    public String getRegion() {
        return region;
    }
}
