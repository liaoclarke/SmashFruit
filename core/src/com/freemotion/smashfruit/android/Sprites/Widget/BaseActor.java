package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class BaseActor extends Actor implements TransitionActor {

    protected TransitionActor parent;
    protected String configFile, configName;

    public BaseActor() {
        super();
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
    public float getDuration() {
        return 0;
    }
}
