package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseGroup;

/**
 * Created by liaoclark on 3/27/2016.
 */
public class UnlockDialogBox extends BaseGroup {

    public UnlockDialogBox(StageConfig config) {
        super(config);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("unlock_dialog");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "scale_up");
        addAction(Actions.scaleTo(tc.getScaleX(), tc.getScaleY(), tc.getDuration()));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        remove();
    }
}
