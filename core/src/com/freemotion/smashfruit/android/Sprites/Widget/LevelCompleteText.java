package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseImage;

/**
 * Created by liaoclark on 4/10/2016.
 */
public class LevelCompleteText extends BaseImage {

    public LevelCompleteText(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
        remove();
    }
}
