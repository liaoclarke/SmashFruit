package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseImage;

/**
 * Created by liaoclark on 2016/3/17.
 */
public class Indicator extends BaseImage {

    private String configKey;

    public Indicator(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        configKey = config.getKey();
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
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig(configKey);
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()), completeShowAction));
        isShowup = false;
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig(configKey);
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        addAction(Actions.sequence(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()), completeHideAction));
        isHidden = false;
    }
}
