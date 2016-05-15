package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Screens.GameScreen;
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

    private String configFile;
    private String configName;
    private GameScreen gameScreen;
    private Array<TransitionActor> widgets;

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
        setupViewPort();
        setupInput();
        readStageConfig();
        setStageContent();
    }

    public GameStage(GameScreen screen, String configFile, String configName) {
        this(configFile, configName);
        gameScreen = screen;
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
    }

    @Override
    public void hide() {

    }

    @Override
    public Stage getStage() {
        return this;
    }
}
