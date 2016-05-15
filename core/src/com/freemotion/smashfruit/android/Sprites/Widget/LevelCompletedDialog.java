package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 4/10/2016.
 */
public class LevelCompletedDialog extends BaseFragment {

    private String LOG_TAG;

    public LevelCompletedDialog(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();

        ContinueButton closeButton = (ContinueButton) findChildByName("ContinueButton");
        closeButton.setParentDialog(this);

        BackToMenuButton backButton = (BackToMenuButton) findChildByName("BackToButton");
        backButton.setParentDialog(this);
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
