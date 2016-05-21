package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class BaseActor extends Actor implements TransitionActor {

    protected TransitionActor parent;
    protected String configFile, configName;
    protected boolean isShowup, isHidden;

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
}
