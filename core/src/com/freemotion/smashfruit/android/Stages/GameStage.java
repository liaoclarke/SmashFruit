package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Game.GameController;
import com.freemotion.smashfruit.android.Game.TouchEventListener;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Screens.GameScreen;
import com.freemotion.smashfruit.android.Sprites.Cuboid;
import com.freemotion.smashfruit.android.Sprites.DominoActor;
import com.freemotion.smashfruit.android.Sprites.Tomato;
import com.freemotion.smashfruit.android.Sprites.Widget.TransitionActor;
import com.freemotion.smashfruit.android.Sprites.Widget.TransitionStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageListener;
import com.freemotion.smashfruit.android.Utils.StageBase;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 4/17/2016.
 */
public class GameStage extends StageBase implements JsonConfigFileParser, MessageListener, TransitionStage {

    enum GameState {
        START,
        RUNNING,
        PASSED,
        AUTO_PASSED,
        PERFECT_PASSED,
        TIMEOUT,
        PUSHED,
        TOUCHED,
        END
    };

    private String configFile;
    private String configName;
    private GameScreen gameScreen;
    private Array<TransitionActor> widgets;
    private GameState gameState;
    private GameController game;
    private Array<DominoActor> cuboids;

    private Action completeAction = new Action() {

        public boolean act(float delta) {
            gameScreen.stopGameStage();
            gameScreen.setMenuStage();
            gameScreen.getMenuStage().show();
            return true;
        }
    };

    public GameStage(String configFile, String configName) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        this.configFile = configFile;
        this.configName = configName;
        this.gameState = GameState.END;
        setupViewPort();
        readStageConfig();
        setStageContent();
    }

    public GameStage(GameScreen screen, String configFile, String configName) {
        this(configFile, configName);
        gameScreen = screen;
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        switch (gameState) {
            case START:
                boolean startCompleted = true;
                for (TransitionActor widget : widgets) {
                    if (!widget.isShowCompleted()) {
                        startCompleted = false;
                        break;
                    }
                }
                if (startCompleted) {
                    setupInput();
                    setupScene();
                    gameState = GameState.RUNNING;
                }
                break;

            case RUNNING:
                break;

            case PERFECT_PASSED:
                showPerfectPassDialog();
                break;

            case AUTO_PASSED:
                break;

            case PASSED:
                showPassDialog();
                break;

            case TIMEOUT:
                showTimeoutDialog();
                break;
        }
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

    public void setStageConfig(String configFile, String configName) {
        this.configFile = configFile;
        this.configName = configName;
        clear();
        readStageConfig();
        setStageContent();
    }

    @Override
    public void handleMessage(Bundle data) {
    }

    @Override
    public void readStageConfig() {
        if (configFile == null || configName == null) {
            return;
        }
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
    }

    @Override
    public void setStageContent() {
        if (configFile == null || configName == null) {
            return;
        }
        widgets = new Array<TransitionActor>();
        JsonConfigFactory.getInstance().inflateStage(configName);
        for (TransitionActor widget : widgets) {
            addActor(widget.getActor());
        }
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            TransitionActor actor = (TransitionActor) ctor.newInstance(config);
            widgets.add(actor);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey());
            throw new RuntimeException("Can not parser config file: " + configFile);
        }
    }

    @Override
    public void pause() {
        clear();
    }

    @Override
    public void resume() {
        setStageContent();
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show game stage...");
        for (TransitionActor widget : widgets) {
            widget.show();
        }
        gameState = GameState.START;
    }

    @Override
    public void hide() {

    }

    @Override
    public Stage getStage() {
        return this;
    }

    void showPerfectPassDialog() {

    }

    void showPassDialog() {

    }

    void showTimeoutDialog() {

    }

    private void setupScene() {
        cuboids = new Array<DominoActor>();
        game = new GameController();
        game.enterGame();
        createDominos();
        //addActor(new ResetButton());
        //addActor(new PassButton());
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

                case Tomato:
                    Tomato t = new Tomato(obj);
                    t.setNextDomino(prevDomino);
                    cuboids.add(t);
                    addActor(t);
                    prevDomino = t;
                    break;
            }
        }
    }


    @Override
    public boolean handleKeyPressedEvent(TouchEventListener.INPUT_EVENT event) {
        switch (event) {
            case Reset:
                for (DominoActor c : cuboids) {
                    c.remove();
                }
                cuboids.clear();
                game.resetGame();
                createDominos();
                gameState = GameState.RUNNING;
                break;

            case Pass:
                gameState = GameState.PASSED;
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
        return gameState == GameState.PUSHED;
    }

    public boolean isGameTouched() {
        return gameState == GameState.TOUCHED;
    }

    public void pushDomino() {
        gameState = GameState.PUSHED;
    }
}
