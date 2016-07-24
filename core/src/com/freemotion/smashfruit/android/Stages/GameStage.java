package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Game.DominoInstance;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Game.GameController;
import com.freemotion.smashfruit.android.Utils.MessageHubImpl;
import com.freemotion.smashfruit.android.Game.MessageType;
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

    private enum GameState {
        TAP_TO_START,
        WAIT_FOR_TOUCH,
        PASS,
        AUTO_PASS,
        PERFECT_PASS,
        TIMEOUT,
        PUSHED,
        TOUCHED,
        END
    };

    private GameScreen gameScreen;
    private Array<TransitionActor> widgets;
    private GameState gameState;
    private GameController game;
    private Array<DominoActor> cuboids;
    private MessageHubImpl messageHub;

    private Action completeAction = new Action() {

        public boolean act(float delta) {
            return true;
        }
    };

    public GameStage() {
        super();
        configFile = "config/GameStageConfig";
        configName = "GameStage";
        LOG_TAG = this.getClass().getSimpleName();

        messageHub = new MessageHubImpl();
        messageHub.registerMessageListener(MessageType.Game_Start, this);
        messageHub.registerMessageListener(MessageType.Game_Reset, this);
        messageHub.registerMessageListener(MessageType.Game_Over, this);
        messageHub.registerMessageListener(MessageType.Open_Settings_Dialog, this);
        messageHub.registerMessageListener(MessageType.Close_Settings_Dialog, this);

        setupViewPort();
        setupInput();
        readStageConfig();
        setStageContent();

        game = GameController.getInstance();
        gameState = GameState.TAP_TO_START;
    }

    public GameStage(GameScreen screen) {
        this();
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
            case TAP_TO_START:
                break;

            case WAIT_FOR_TOUCH:
                break;

            case PERFECT_PASS:
                showPerfectPassDialog();
                break;

            case AUTO_PASS:
                break;

            case PASS:
                showPassDialog();
                break;

            case TIMEOUT:
                showTimeoutDialog();
                break;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.log(LOG_TAG, "stage touch up screenX: " + screenX + " screenY: " + screenY);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.log(LOG_TAG, "stage touch down screenX: " + screenX + " screenY: " + screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void handleMessage(Bundle data) {
        Gdx.app.error(LOG_TAG, data.getString());
        if (MessageType.Game_Start.equals(data.getString())) {
            setupGameScene();
        } else if (MessageType.Open_Settings_Dialog.equals(data.getString())) {
            TransitionActor dialog = data.getActor();
            dialog.getMessageDispatch().setMessageHub(messageHub);
            widgets.add(dialog);
            addActor(dialog.getActor());
            dialog.show();
        } else if (MessageType.Close_Settings_Dialog.equals(data.getString())) {
            TransitionActor dialog = data.getActor();
            widgets.removeValue(dialog, true);
            dialog.hide();
        }
        data.getCallback().doMessageCallback(data);
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
            widget.getMessageDispatch().setMessageHub(messageHub);
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
        game = GameController.getInstance();
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show game stage...");
        for (TransitionActor widget : widgets) {
            widget.show();
        }
        gameState = GameState.TAP_TO_START;
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

    private void setupGameScene() {
        clear();
        widgets.clear();
        game.enterGame();
        for (DominoInstance instance : game.getDominoObjects()) {

        }
    }

    public boolean isDominoPushed() {
        return true;
    }

    public void pushDomino() {

    }
}
