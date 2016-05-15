package com.freemotion.smashfruit.android.Sprites.Widget;

import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Stages.MenuStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageHub;

/**
 * Created by liaoclark on 2016/3/15.
 */
public class MainMenu extends BaseFragment implements MessageHub {

    public MainMenu(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();

        FindBestButton findBestButton = (FindBestButton) findChildByName("FindBestButton");
        findBestButton.setMessageHub(this);

        ShapeItButton shapeItButton = (ShapeItButton) findChildByName("ShapeItButton");
        shapeItButton.setMessageHub(this);

        TimeRaceButton timeRaceButton = (TimeRaceButton) findChildByName("TimeRaceButton");
        timeRaceButton.setMessageHub(this);
    }

    @Override
    public void forwardMessageToListener(String message, Bundle data) {
        if ("show_findbest_menu".equals(message)) {
            ((MenuStage) getStage()).handleMessage(data);
        } else if ("show_shapeit_menu".equals(message)) {
            ((MenuStage) getStage()).handleMessage(data);
        } else if ("play_timerace_game".equals(message)) {
            ((MenuStage) getStage()).handleMessage(data);
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
