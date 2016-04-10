package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 4/10/2016.
 */
public class TotalCuboidWidget extends BaseGroup {

    public TotalCuboidWidget(StageConfig config) {
        super(config);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        remove();
    }
}
