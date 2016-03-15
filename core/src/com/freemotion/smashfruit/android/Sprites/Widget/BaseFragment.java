package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Stages.MenuStage;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class BaseFragment extends Actor implements JsonConfigFileParser, TransitionActor {

    private Array<TransitionActor> group;
    protected String LOG_TAG;
    private String configFile, configName;
    private TransitionActor parent;
    private MenuStage stage;

    public BaseFragment(StageConfig config) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        group = new Array<TransitionActor>();
        configFile = config.getConfigFile();
        configName = config.getConfigName();
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
        JsonConfigFactory.getInstance().inflateStage(configName);
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            TransitionActor ac = (TransitionActor) ctor.newInstance(config);
            ac.setParent(this);
            group.add(ac);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey());
            throw new RuntimeException("Can not parser config file: " + configFile);
        }
    }

    @Override
    protected void setStage(Stage stage) {
        if (getStage() != null) {
            remove();
        }
        this.stage = (MenuStage) stage;
        if (this.stage != null) {
            for (TransitionActor ac : group) {
                this.stage.addActor(ac.getActor());
            }
        }
        super.setStage(stage);
    }

    @Override
    public void setParent(TransitionActor actor) {
    }

    @Override
    public Actor getActor() {
        return this;
    }

    public void show() {
        for (TransitionActor ac : group) {
            ac.show();
        }
    }

    public void hide() {
        for (TransitionActor ac : group) {
            ac.hide();
        }
    }

    @Override
    public float getDuration() {
        return .2f;
    }
}
