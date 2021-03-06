package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageHubObject;
import com.freemotion.smashfruit.android.Utils.StageBase;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class BaseFragment extends Actor implements JsonConfigFileParser, TransitionActor, MessageDispatch {

    protected String LOG_TAG;
    protected StageBase stage;
    protected String configFile, configName;
    protected Array<TransitionActor> group;
    protected MessageHubObject messageHub;

    public BaseFragment(StageConfig config) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        messageHub = new MessageHubObject();
        group = new Array<TransitionActor>();
        configFile = config.getConfigFile();
        configName = config.getConfigName();
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
        JsonConfigFactory.getInstance().inflateStage(configName, this);
        setName(configName);
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
        this.stage = (StageBase) stage;
        if (this.stage != null) {
            for (TransitionActor ac : group) {
                this.stage.addActor(ac.getActor());
            }
        }
        super.setStage(stage);
    }

    public Actor findChildByName(String configName) {
        for (TransitionActor ac : group) {
             if (ac instanceof BaseGroup ) {
                ArrayList<TransitionActor> childActors = ((BaseGroup) ac).getChildActors();
                for (TransitionActor cac : childActors) {
                    if (cac.getActor() != null && configName.equals(cac.getActor().getName())) {
                        return cac.getActor();
                    }
                }
            } else if (ac.getActor() != null && configName.equals(ac.getActor().getName()))  {
                return ac.getActor();
            }
        }
        return null;
    }


    @Override
    public void setParent(TransitionActor actor) {
    }

    @Override
    public Actor getActor() {
        return this;
    }

    @Override
    public MessageDispatch getMessageDispatch() {
        return this;
    }

    public void show() {
        JsonConfigFactory.getInstance().getStageConfig(configName).setActive(true);
    }

    public void hide() {
        JsonConfigFactory.getInstance().getStageConfig(configName).setActive(false);
    }

    @Override
    public boolean isShowCompleted() {
        return false;
    }

    @Override
    public boolean isHideCompleted() {
        return false;
    }

    public boolean isActive() {
        return JsonConfigFactory.getInstance().getStageConfig(configName).getActive();
    }

    public String getConfigName() {
        return configName;
    }

    @Override
    public float getDuration() {
        return 0f;
    }

    @Override
    public void setMessageHub(MessageHub hub) {
        messageHub.setHub(hub);
    }

    @Override
    public void dispatchMessage(String message, Bundle data) {

    }

    @Override
    public boolean doMessageCallback(Bundle data) {
        return false;
    }
}
