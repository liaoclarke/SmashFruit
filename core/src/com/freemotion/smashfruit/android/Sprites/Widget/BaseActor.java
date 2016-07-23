package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageHubObject;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class BaseActor extends Actor implements TransitionActor, MessageDispatch {

    protected TransitionActor parent;
    protected String configFile, configName;
    protected boolean isShowup, isHidden;
    protected MessageHubObject messageHub;

    protected Action completeShowAction = new Action() {
        public boolean act(float delta) {
            isShowup = true;
            return true;
        }
    };

    protected Action completeHideAction = new Action() {
        public boolean act(float delta) {
            isHidden = true;
            return true;
        }
    };

    public BaseActor() {
        super();
        messageHub = new MessageHubObject();
        isShowup = false;
        isHidden = false;
    }

    @Override
    public void setParent(TransitionActor actor) {
        parent = actor;
    }

    @Override
    public Actor getActor() {
        return this;
    }

    @Override
    public MessageDispatch getMessageDispatch() {
        return this;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean isShowCompleted() {
        return isShowup;
    }

    @Override
    public boolean isHideCompleted() {
        return isHidden;
    }

    @Override
    public float getDuration() {
        return 0;
    }

    @Override
    public void setMessageHub(MessageHub hub) {
        messageHub.setHub(hub);
    }

    @Override
    public void dispatchMessage(String message, Bundle data) {
        messageHub.forwardMessageToListener(message, data);
    }

    @Override
    public boolean doMessageCallback(Bundle data) {
        return false;
    }
}
