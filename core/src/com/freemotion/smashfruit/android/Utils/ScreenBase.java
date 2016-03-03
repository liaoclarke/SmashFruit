package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by liaoclark on 1/23/16.
 */
public class ScreenBase implements Screen {

    protected GameBase gameInstance;
    protected Stage stage;
    protected boolean paused;

    public ScreenBase(GameBase instance) {
        gameInstance = instance;
    }

    @Override
    public void dispose() {
        paused = true;
    }

    @Override
    public void hide() {
        paused = true;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        paused = false;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void show() {
        paused = false;
    }
}
