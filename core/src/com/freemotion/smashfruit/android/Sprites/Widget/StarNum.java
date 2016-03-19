package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class StarNum extends BaseImage {

    public StarNum(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
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
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_star_num");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_star_num");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
    }
}
