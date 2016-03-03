package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by liaoclark on 1/23/16.
 */
public class GameBase extends Game {

    private String LOG_TAG = "GameBase";

    public GameBase() {}

    public void installScreen() {

    }

    @Override
    public void create() {
        ResourceManager.getInstance().init();
    }

    @Override
    public void dispose() {
        ResourceManager.getInstance().dispose();
        super.dispose();
        Gdx.app.log(LOG_TAG, "dispose");
    }

    @Override
    public void pause() {
        ResourceManager.getInstance().pause();
        super.pause();
        Gdx.app.log(LOG_TAG, "pause");
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(LOG_TAG, "resize");
    }

    @Override
    public void resume() {
        ResourceManager.getInstance().resume();
        super.resume();
        Gdx.app.log(LOG_TAG, "resume");
    }
}
