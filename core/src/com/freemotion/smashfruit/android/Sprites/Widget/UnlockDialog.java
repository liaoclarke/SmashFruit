package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 3/26/2016.
 */
public class UnlockDialog extends BaseFragment {

    private String LOG_TAG;

    public UnlockDialog(StageConfig config, LevelButton button) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();

        DialogCloseButton closeButton = (DialogCloseButton) findChildByName("DialogCloseButton");
        closeButton.setParentDialog(this);
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
        getActor().remove();
    }
}
