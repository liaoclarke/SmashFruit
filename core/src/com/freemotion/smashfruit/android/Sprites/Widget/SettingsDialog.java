package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseFragment;
import com.freemotion.smashfruit.android.Sprites.Widget.DialogCloseButton;
import com.freemotion.smashfruit.android.Sprites.Widget.TransitionActor;

/**
 * Created by liaoclark on 4/2/2016.
 */
public class SettingsDialog extends BaseFragment {

    private String LOG_TAG;

    public SettingsDialog(StageConfig config) {
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
