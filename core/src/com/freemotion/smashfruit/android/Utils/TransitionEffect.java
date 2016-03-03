package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by liaoclark on 1/23/16.
 */
public abstract class TransitionEffect {

    public float transitionDuration;
    public float transitionTime;

    public TransitionEffect(float duration) {
        transitionDuration = duration;
        transitionTime = 0f;
    }

    public abstract boolean update(float delta);

    public abstract void render(Screen prev, Screen next);
}
