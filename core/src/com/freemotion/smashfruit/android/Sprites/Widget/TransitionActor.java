package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by liaoclark on 2016/3/15.
 */
public interface TransitionActor {

    void show();

    void hide();

    boolean isShowCompleted();

    boolean isHideCompleted();

    Actor getActor();

    float getDuration();

    void setParent(TransitionActor parent);
}
