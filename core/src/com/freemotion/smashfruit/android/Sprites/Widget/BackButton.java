package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseButton;
import com.freemotion.smashfruit.android.Stages.MenuStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageListener;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class BackButton extends BaseButton implements MessageDispatch, MessageListener {

    private MessageHub messageHub;
    private int current_page;

    public BackButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        addListener(listener);
        setName(config.getConfigName());
        current_page = 0;
    }

    @Override
    public void setMessageHub(MessageHub hub) {
        messageHub = hub;
    }

    @Override
    public void dispatchMessage(String message, Bundle data) {
        messageHub.forwardMessageToListener(message, data);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    protected InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_TAG, this.getClass().getSimpleName() + " button touch down");
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_TAG, this.getClass().getSimpleName() + " button touch up current_page: " + current_page);
            super.touchUp(event, x, y, pointer, button);
            if (current_page <= 0) {
                Bundle data = new Bundle();
                data.putInteger(MenuStage.HIDE_FINDBEST_SHOW_MAINMENU);
                dispatchMessage("back_to_main_menu", data);
            } else {
                Bundle data = new Bundle();
                data.putString("press_back_button");
                dispatchMessage("press_back_button", data);
                current_page -= 1;
            }
        }
    };

    @Override
    public void handleMessage(Bundle data) {
        String strData = data.getString();
        int max_page_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_PAGE_NUM").getValue());
        if ("press_next_button".equals(strData) && current_page < max_page_num) {
            current_page += 1;
            Gdx.app.log(LOG_TAG, "press next button : " + current_page);
        } else if ("scroll_level_page".equals(strData) && current_page < max_page_num) {
            int app_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("APP_WIDTH").getValue());
            int page = data.getInteger() / app_width;
            page += (data.getInteger() % app_width) > (app_width * 0.5f) ? 1 : 0;
            current_page = page;
            Gdx.app.log(LOG_TAG, "scroll level page : " + current_page);
        }
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_back_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.sequence(Actions.delay(delay.getDuration(), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())), completeShowAction));
        isShowup = false;
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_back_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.sequence(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()), completeHideAction));
        isHidden = false;
    }
}
