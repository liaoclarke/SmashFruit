package com.freemotion.smashfruit.android.Utils;

import com.freemotion.smashfruit.android.Sprites.Widget.TransitionActor;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class Bundle {

    private int iData;
    private String sData;
    private TransitionActor aData;
    private MessageDispatch mCallback;

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

    public TransitionActor getActor() {
        return aData;
    }

    public void putActor(TransitionActor value) {
        aData = value;
    }

    public void putCallback(MessageDispatch cb) {
        mCallback = cb;
    }

    public MessageDispatch getCallback() {
        return mCallback;
    }
}
