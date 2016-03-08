package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Game.GameController;
import com.freemotion.smashfruit.android.Game.TouchEventListener;
import com.freemotion.smashfruit.android.Sprites.Cuboid;
import com.freemotion.smashfruit.android.Sprites.DominoActor;
import com.freemotion.smashfruit.android.Sprites.SceneBackground;
import com.freemotion.smashfruit.android.Utils.StageBase;

/**
 * Created by liaoclark on 2016/3/3.
 */
public class GameStage extends StageBase {

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
    };
    private LEVEL_STATE levelState;

    public GameStage() {
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
        Gdx.app.error(LOG_TAG, "stage touch up");
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.error(LOG_TAG, "stage touch down");
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void setupUI() {

    }

    private void setupScene() {
        addActor(new SceneBackground());
        cuboids = new Array<Cuboid>();
        game = new GameController();
        game.enterGame();
        createDominos();
    }

    private void createDominos() {
        Array<DominoObject> objects = game.getDominoObjects();
        DominoActor prevDomino = null;
        for (int i = objects.size - 1; i >= 0; i--) {
            DominoObject obj = objects.get(i);
            switch (obj.getDominoType()) {
                case Cuboid:
                    Cuboid c = new Cuboid(obj);
                    c.setNextDomino(prevDomino);
                    cuboids.add(c);
                    addActor(c);
                    prevDomino = c;
                    break;
            }
        }
    }

    @Override
    public boolean handleKeyPressedEvent(TouchEventListener.INPUT_EVENT event) {
        switch (event) {
            case Reset:
                for (Cuboid c : cuboids) {
                    c.remove();
                }
                cuboids.clear();
                game.resetGame();
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

    public void pushDomino() {
        levelState = LEVEL_STATE.PUSHED;
    }
}
