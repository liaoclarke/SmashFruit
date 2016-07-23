package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by liaoclark on 7/22/16.
 */
public class MessageHubImpl implements MessageHub {

    private HashMap<String, Array<MessageListener>> MessageMap;

    private Array<MessageDispatch> HubObservers;

    public MessageHubImpl() {
        MessageMap = new HashMap<String, Array<MessageListener>>();
        HubObservers = new Array<MessageDispatch>();
    }

    @Override
    public void registerMessageListener(String message, MessageListener listener) {
        if (MessageMap.get(message) == null) {
            MessageMap.put(message, new Array<MessageListener>());
        }
        MessageMap.get(message).add(listener);
    }

    @Override
    public void forwardMessageToListener(String message, Bundle data) {
        Array<MessageListener> listenerArray = MessageMap.get(message);
        if (listenerArray != null) {
            for (MessageListener listener : listenerArray) {
                listener.handleMessage(data);
            }
        }
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
}
