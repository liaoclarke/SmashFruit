package com.freemotion.smashfruit.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Resources.LogoTextureLoader;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Stages.*;
import com.freemotion.smashfruit.android.Utils.*;

import java.util.ArrayList;

/**
 * Created by liaoclark on 1/22/16.
 */
public class GameScreen extends ScreenBase {

    private String LOG_TAG;
    private SpriteBatch batch;
    private GameStage gameStage;
    private MainStage mainStage;
    private Array<StageBase> stages;

    public GameScreen(GameBase gameInstance) {
        super(gameInstance);
        LOG_TAG = this.getClass().getSimpleName();
        batch = new SpriteBatch();

        stages = new Array<StageBase>();
        mainStage = new MainStage();
        stages.add(mainStage);
        //gameStage = new GameStage();
        //stages.add(gameStage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(LOG_TAG, "pause");
        for (StageBase st : stages) {
            st.pause();
        }
    }

    @Override
    public void render(float delta) {
        if (paused) {
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Stage s : stages) {
            s.draw();
            s.act(delta);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log(LOG_TAG, "resume: resourceManager: " + ResourceManager.getInstance());
        for (StageBase st : stages) {
            st.resume();
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
