package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class MainMenuBackground extends BaseImage {

    public MainMenuBackground(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("main_background");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "show_delay");
        TransitionConfig to = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(to.getPositionX(), to.getPositionY()), Actions.visible(true)));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("main_background");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "hide_delay");
        TransitionConfig to = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(to.getPositionX(), to.getPositionY()), Actions.visible(false)));
    }
}
