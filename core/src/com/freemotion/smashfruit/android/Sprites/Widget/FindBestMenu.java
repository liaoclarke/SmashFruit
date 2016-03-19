package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class FindBestMenu extends BaseFragment {

    public FindBestMenu(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        ChooseLevelGrid levelGrid = (ChooseLevelGrid) findChildByName("ChooseLevelGrid");
        PageIndicator indicator = (PageIndicator) findChildByName("PageIndicator");
        levelGrid.setMessageListener(indicator);

        BackButton backButton = (BackButton) findChildByName("BackButton");
        NextButton nextButton = (NextButton) findChildByName("NextButton");
        backButton.setMessageListener(levelGrid);
        nextButton.setMessageListener(levelGrid);
        nextButton.setMessageListener(backButton);
    }

    @Override
    public void show() {
        super.show();
        for (TransitionActor ac : group) {
            ac.show();
        }
    }

    @Override
    public void hide() {
        super.hide();
        for (TransitionActor ac : group) {
            ac.hide();
        }
    }

    @Override
    public float getDuration() {
        return 0.2f;
    }
}
