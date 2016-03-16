package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class FindBestMenu extends BaseFragment {

    public FindBestMenu(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
    }

    @Override
    public void show() {
        for (TransitionActor ac : group) {
            ac.show();
        }
    }

    @Override
    public void hide() {
        for (TransitionActor ac : group) {
            ac.hide();
        }
    }

    @Override
    public float getDuration() {
        return 0.2f;
    }
}
