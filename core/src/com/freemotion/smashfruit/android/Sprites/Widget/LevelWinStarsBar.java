package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseGroup;

/**
 * Created by liaoclark on 4/10/2016.
 */
public class LevelWinStarsBar extends BaseGroup {

    public LevelWinStarsBar(StageConfig config) {
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
