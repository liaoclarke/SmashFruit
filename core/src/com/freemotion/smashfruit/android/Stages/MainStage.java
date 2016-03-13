package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Utils.GameActor;
import com.freemotion.smashfruit.android.Utils.StageBase;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 2016/3/10.
 */
public class MainStage extends StageBase implements JsonConfigFileParser {

    private Array<GameActor> actors;
    private final static String configFile = "config/MainStageConfig";
    private final static String configName = "MainStage";

    public MainStage() {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        setupViewPort();
        setupInput();
        resume();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void readStageConfig() {
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
    }

    @Override
    public void setStageContent() {
        actors = new Array<GameActor>();
        JsonConfigFactory.getInstance().inflateStaget(configName);
        for (GameActor ac : actors) {
            addActor(ac);
        }
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            actors.add((GameActor) ctor.newInstance(config));
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
        readStageConfig();
        setStageContent();
    }
}
