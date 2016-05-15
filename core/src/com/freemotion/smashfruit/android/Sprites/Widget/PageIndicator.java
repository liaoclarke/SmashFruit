package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageListener;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 2016/3/17.
 */
public class PageIndicator extends BaseActor implements JsonConfigFileParser, MessageListener {

    private String LOG_TAG;
    private Array<Indicator> indicators;

    public PageIndicator(StageConfig config) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        indicators = new Array<Indicator>();
        configFile = config.getConfigFile();
        configName = config.getConfigName();
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
        JsonConfigFactory.getInstance().inflateStage(configName, this);
        setName(configName);
        indicators.get(0).select(true);
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            Indicator ac = (Indicator) ctor.newInstance(config);
            ac.setParent(this);
            indicators.add(ac);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey() + " error: " + e.toString());
            throw new RuntimeException("Can not parser config file: " + configFile);
        }
    }

    @Override
    protected void setStage(Stage stage) {
        if (getStage() != null) {
            remove();
        }
        if (stage != null) {
            for (TransitionActor ac : indicators) {
                stage.addActor(ac.getActor());
            }
        }
        super.setStage(stage);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_page_indicator");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
        for (Indicator ac : indicators) {
            ac.show();
        }
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_page_indicator");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
        for (Indicator ac : indicators) {
            ac.hide();
        }
    }
    @Override
    public void handleMessage(Bundle data) {
        int app_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("APP_WIDTH").getValue());
        int page = data.getInteger() / app_width;
        page += (data.getInteger() % app_width) > (app_width * 0.5f) ? 1 : 0;
        for (int i = 0; i < indicators.size; i++) {
            indicators.get(i).select(false);
        }
        indicators.get(page).select(true);
        Gdx.app.error(LOG_TAG, " page : " + page + " page x : " + data.getInteger());
    }
}
