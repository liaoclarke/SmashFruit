package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Stages.MenuStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageHub;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class FindBestMenu extends BaseFragment implements MessageHub {

    public FindBestMenu(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();

        ChooseLevelGrid levelGrid = (ChooseLevelGrid) findChildByName("ChooseLevelGrid");
        PageIndicator indicator = (PageIndicator) findChildByName("PageIndicator");
        //levelGrid.setMessageListener(indicator);
        levelGrid.setMessageHub(this);

        BackButton backButton = (BackButton) findChildByName("BackButton");
        NextButton nextButton = (NextButton) findChildByName("NextButton");
        //backButton.setMessageHub(levelGrid);
        //nextButton.setMessageHub(levelGrid);
        //nextButton.setMessageHub(backButton);
        backButton.setMessageHub(this);
        nextButton.setMessageHub(this);
    }

    @Override
    public void forwardMessageToListener(String message, Bundle data) {
        if ("press_back_button".equals(message)) {
            ChooseLevelGrid levelGrid = (ChooseLevelGrid) findChildByName("ChooseLevelGrid");
            PageIndicator indicator = (PageIndicator) findChildByName("PageIndicator");
            levelGrid.handleMessage(data);
            //indicator.handleMessage(data);
        } else if ("press_next_button".equals(message)) {
            ChooseLevelGrid levelGrid = (ChooseLevelGrid) findChildByName("ChooseLevelGrid");
            PageIndicator indicator = (PageIndicator) findChildByName("PageIndicator");
            BackButton backButton = (BackButton) findChildByName("BackButton");
            backButton.handleMessage(data);
            levelGrid.handleMessage(data);
            //indicator.handleMessage(data);
        } else if ("back_to_main_menu".equals(message)) {
            ((MenuStage) getStage()).handleMessage(data);
        } else if ("scroll_level_page".equals(message)) {
            PageIndicator indicator = (PageIndicator) findChildByName("PageIndicator");
            BackButton backButton = (BackButton) findChildByName("BackButton");
            indicator.handleMessage(data);
            backButton.handleMessage(data);
        }
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
