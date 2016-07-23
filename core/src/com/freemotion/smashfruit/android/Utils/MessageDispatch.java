package com.freemotion.smashfruit.android.Utils;

/**
 * Created by liaoclark on 2016/3/17.
 */
public interface MessageDispatch {

    void setMessageHub(MessageHub hub);

    void dispatchMessage(String message, Bundle data);

    boolean doMessageCallback(Bundle data);
}
