package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.utils.Array;

/**
 * Created by liaoclark on 7/23/16.
 */
public class MessageHubObject implements MessageHub {

    private MessageHub hubObject;

    private Array<MessageDispatch> HubObservers;

    public MessageHubObject() {
        HubObservers = new Array<MessageDispatch>();
    }

    @Override
    public void registerMessageListener(String message, MessageListener listener) {
        hubObject.registerMessageListener(message, listener);
    }

    @Override
    public void forwardMessageToListener(String message, Bundle data) {
        hubObject.forwardMessageToListener(message, data);
    }

    @Override
    public void addHubObserver(MessageDispatch observer) {
        if ((HubObservers.indexOf(observer, true) == -1) ||
            (HubObservers.indexOf(observer, false) == -1)) {
            HubObservers.add(observer);
        }
    }

    @Override
    public Array<MessageDispatch> getHubObservers() {
        return HubObservers;
    }

    public void setHub(MessageHub hub) {
        hubObject = hub;
    }
}
