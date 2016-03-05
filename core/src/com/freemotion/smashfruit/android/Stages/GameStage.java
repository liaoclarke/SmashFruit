package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Game.GameController;
import com.freemotion.smashfruit.android.Sprites.Cuboid;
import com.freemotion.smashfruit.android.Sprites.SceneBackground;
import com.freemotion.smashfruit.android.Utils.StageBase;

/**
 * Created by liaoclark on 2016/3/3.
 */
public class GameStage extends StageBase {

    private String LOG_TAG;
    private GameController game;

    private enum LEVEL_STATE {
        RUNNING,
        PAUSED,
        FAILED,
        PASSED,
        SETUP,
    };
    private LEVEL_STATE levelState;

    public GameStage() {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        setupViewPort();
        setupInput();
        setupUI();
        setupScene();
        levelState = LEVEL_STATE.SETUP;
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

    private void setupUI() {

    }

    private void setupScene() {
        addActor(new SceneBackground());
        levelState = LEVEL_STATE.RUNNING;
        game = new GameController();
        game.enterGame();
        Array<DominoObject> objects = game.getDominoObjects();
        for (DominoObject obj : objects) {
            switch (obj.getDominoType()) {
                case Cuboid:
                    addActor(new Cuboid(obj));
                    break;
            }
        }
    }
}
