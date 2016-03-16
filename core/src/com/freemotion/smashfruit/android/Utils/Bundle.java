package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class Bundle {

    private int iData;
    private String sData;
    private Actor aData;

    public Bundle() {
        iData = 0;
        sData = null;
    }

    public int getInteger() {
        return iData;
    }

    public void putInteger(int value) {
        iData = value;
    }

    public String getString() {
        return sData;
    }

    public void putString(String value) {
        sData = value;
    }

    public Actor getActor() {
        return aData;
    }

    public void putActor(Actor value) {
        aData = value;
    }
}
