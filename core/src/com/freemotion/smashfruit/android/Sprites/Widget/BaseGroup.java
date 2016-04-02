package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.freemotion.smashfruit.android.Misc.StageConfig;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Created by liaoclark on 3/27/2016.
 */
public class BaseGroup extends Group implements TransitionActor {

    protected String LOG_TAG;
    protected TransitionActor parent;
    protected String configFile, configName;
    private ArrayList<TransitionActor> childActors;

    public BaseGroup(StageConfig config) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        configFile = config.getConfigFile();
        configName = config.getConfigName();
        childActors = new ArrayList<TransitionActor>();
        setPosition(config.getPositionX(), config.getPositionY());
        //setOrigin(config.getOrigPositionX(), config.getOrigPositionY());
        setOrigin(config.getWidth() * 0.5f, config.getHeight() * 0.5f);
        setScale(config.getScaleX(), config.getScaleY());
        setGroupContent(config);
    }

    private void setGroupContent(StageConfig config) {
        ArrayList<StageConfig> group = config.getGroup();
        for (StageConfig c : group) {
            try {
                String pkg = "com.freemotion.smashfruit.android.Sprites.Widget.";
                Class cls = Class.forName(pkg + c.getDclass());
                Constructor ctor = cls.getConstructor(StageConfig.class);
                TransitionActor ac = (TransitionActor) ctor.newInstance(c);
                childActors.add(ac);
                addActor(ac.getActor());
            } catch (Exception e) {
                Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey());
                throw new RuntimeException("Can not parser config file: " + configFile);
            }
        }
    }

    public ArrayList<TransitionActor> getChildActors() {
        return childActors;
    }

    @Override
    public void setParent(TransitionActor actor) {
        parent = actor;
    }

    @Override
    public Actor getActor() {
        return this;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public float getDuration() {
        return 0;
    }
}
