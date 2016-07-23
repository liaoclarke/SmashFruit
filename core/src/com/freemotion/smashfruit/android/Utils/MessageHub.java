package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.utils.Array;

/**
 * Created by liaoclark on 3/21/2016.
 */
public interface MessageHub {

    void registerMessageListener(String message, MessageListener listener);

    void forwardMessageToListener(String message, Bundle data);

    void addHubObserver(MessageDispatch observer);

    Array<MessageDispatch> getHubObservers();
}
