package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Game.GameController;
import com.freemotion.smashfruit.android.Game.TouchEventListener;
import com.freemotion.smashfruit.android.Sprites.Cuboid;
import com.freemotion.smashfruit.android.Sprites.DominoActor;
import com.freemotion.smashfruit.android.Sprites.SceneBackground;
import com.freemotion.smashfruit.android.Sprites.Widget.PassButton;
import com.freemotion.smashfruit.android.Sprites.Widget.ResetButton;
import com.freemotion.smashfruit.android.Utils.StageBase;

/**
 * Created by liaoclark on 2016/3/3.
 */
public class TimeRaceStage extends StageBase {

    private String LOG_TAG;
    private GameController game;
    private Array<Cuboid> cuboids;

    private enum LEVEL_STATE {
        RUNNING,
        PAUSED,
        FAILED,
        PASSED,
        PUSHED,
        SETUP,
        TOUCHED,
    };
    private LEVEL_STATE levelState;
    private LEVEL_STATE savedState;

    public TimeRaceStage() {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        setupViewPort();
        setupInput();
        setupUI();
        setupScene();
        levelState = LEVEL_STATE.RUNNING;
    }

    @Override
    public void draw() {
        super.draw();

        switch (levelState) {
             case RUNNING:
                break;

            case PASSED:
                break;

            case PAUSED:
                break;

            case FAILED:
                break;

            case SETUP:
                break;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Gdx.app.log(LOG_TAG, " act");
        switch (levelState) {
            case RUNNING:
                break;

            case PASSED:
                break;

            case PAUSED:
                break;

            case FAILED:
                break;

            case SETUP:
                break;
        }
    }

    protected void setupInput() {
        Gdx.input.setInputProcessor(new TouchEventListener(this));
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.error(LOG_TAG, "stage touch up screenX: " + screenX + " screenY: " + screenY);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.error(LOG_TAG, "stage touch down screenX: " + screenX + " screenY: " + screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void setupUI() {

    }

    private void setupScene() {
        addActor(new SceneBackground());
        cuboids = new Array<Cuboid>();
        game = GameController.getInstance();
        game.enterGame();
        createDominos();
        //addActor(new ResetButton());
        //addActor(new PassButton());
    }

    private void createDominos() {
    }

    @Override
    public boolean handleKeyPressedEvent(TouchEventListener.INPUT_EVENT event) {
        switch (event) {
            case Reset:
                for (Cuboid c : cuboids) {
                    c.remove();
                }
                cuboids.clear();
                createDominos();
                levelState = LEVEL_STATE.RUNNING;
                break;

            case Pass:
                levelState = LEVEL_STATE.PASSED;
                cuboids.get(cuboids.size - 1).playAnimation();
                game.passLevel();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean handleKeyReleasedEvent(TouchEventListener.INPUT_EVENT event) {
        return true;
    }

    public boolean isDominoPushed() {
        return levelState == LEVEL_STATE.PUSHED;
    }

    public boolean isGameTouched() {
        return levelState == LEVEL_STATE.TOUCHED;
    }

    public void pushDomino() {
        levelState = LEVEL_STATE.PUSHED;
    }
}
