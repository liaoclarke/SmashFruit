package com.freemotion.smashfruit.android.Misc;

/**
 * Created by liaoclark on 2016/3/18.
 */
public class TransitionConfig extends JsonConfig {

    private int positionX, positionY;
    private float duration;
    private boolean visible;

    public int getPositionX() {
        return positionX;
    }

    public TransitionConfig setPositionX(int x) {
        positionX = x;
        return this;
    }

    public int getPositionY() {
        return positionY;
    }

    public TransitionConfig setPositionY(int y) {
        positionY = y;
        return this;
    }

    public float getDuration() {
        return duration;
    }

    public TransitionConfig setDuration(float time) {
        duration = time;
        return this;
    }

    public boolean getVisible() {
        return visible;
    }
}
