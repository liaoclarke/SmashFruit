package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 4/2/2016.
 */
public class DialogBackground extends BaseImage {

    public DialogBackground(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        remove();
    }
}
