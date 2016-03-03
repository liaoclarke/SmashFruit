package com.freemotion.smashfruit.android.Game;

import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * Created by liaoclark on 1/22/16.
 */
public abstract class GameView {

    protected GameView(AndroidApplication application) {}

    public abstract void init();

    public abstract View getLayout();
}
