package com.freemotion.smashfruit.android.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseFragment;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageListener;
import com.freemotion.smashfruit.android.Utils.StageBase;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 2016/3/10.
 */
public class MenuStage extends StageBase implements JsonConfigFileParser, MessageListener {

    public static final int TO_MAINMENU = 0;
    public static final int TO_FINDBEST = 1;
    public static final int TO_SHAPEIT = 2;

    private Array<BaseFragment> fragments;
    private String configFile = "config/MenuStageConfig";
    private String configName = "MenuStage";
    private BaseFragment curr_frag, next_frag;

    public MenuStage() {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        setupViewPort();
        setupInput();
        readStageConfig();
        setStageContent();
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
        fragments = new Array<BaseFragment>();
        JsonConfigFactory.getInstance().inflateStage(configName);
        if (curr_frag == null) {
            curr_frag = findActiveFragment();
        }
        addActor(curr_frag);
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            BaseFragment frag = (BaseFragment) ctor.newInstance(config);
            fragments.add(frag);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey());
            throw new RuntimeException("Can not parser config file: " + configFile);
        }
    }

    public void handleMessage(Bundle data) {
        int message = data.getInteger();
        switch (message) {
            case TO_FINDBEST:
                curr_frag.hide();
                next_frag = findFragment("FindBestMenu");
                next_frag.show();
                curr_frag = next_frag;
                break;

            case TO_MAINMENU:
                curr_frag.hide();
                next_frag = findFragment("MainMenu");
                next_frag.show();
                curr_frag = next_frag;
                break;

            case TO_SHAPEIT:
                curr_frag.hide();
                next_frag = findFragment("ShapeItMenu");
                next_frag.show();
                curr_frag = next_frag;
                break;
        }
    }

    private BaseFragment findFragment(String fragName) {
        for (BaseFragment frag : fragments) {
            if (fragName.equals(frag.getName())) {
                return frag;
            }
        }
        return null;
    }

    private BaseFragment findActiveFragment() {
        Array<BaseFragment> active_frags = new Array<BaseFragment>();
        for (BaseFragment frag : fragments) {
            if (frag.isActive()) {
                active_frags.add(frag);
            }
        }
        if (active_frags.size > 1) {
            throw new RuntimeException("There are " + active_frags.size + " active fragments");
        }
        return active_frags.get(0);
    }

    @Override
    public void pause() {
        clear();
    }

    @Override
    public void resume() {
        setStageContent();
    }
}
