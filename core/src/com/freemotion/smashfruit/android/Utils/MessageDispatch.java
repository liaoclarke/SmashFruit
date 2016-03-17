package com.freemotion.smashfruit.android.Utils;

/**
 * Created by liaoclark on 2016/3/17.
 */
public interface MessageDispatch {

    void setMessageListener(MessageListener listener);

    void dispatchMessage(MessageListener listener, Bundle data);
}
