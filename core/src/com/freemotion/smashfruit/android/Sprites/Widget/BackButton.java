package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Json;
import com.freemotion.smashfruit.android.Misc.JsonConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Stages.MenuStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageListener;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class BackButton extends BaseButton implements MessageDispatch, MessageListener {

    private MessageListener levelPages;
    private int current_page;

    public BackButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        addListener(listener);
        setName(config.getConfigName());
        levelPages = null;
        current_page = 1;
    }

    @Override
    public void setMessageListener(MessageListener listener) {
        levelPages = listener;
    }

    @Override
    public void dispatchMessage(MessageListener listener, Bundle data) {
        if (listener != null) {
            listener.handleMessage(data);
        }
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
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch down");
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch up");
            super.touchUp(event, x, y, pointer, button);
            if (current_page == 1) {
                Bundle data = new Bundle();
                data.putInteger(MenuStage.HIDE_FINDBEST_SHOW_MAINMENU);
                dispatchMessage((MenuStage) getStage(), data);
            } else {
                Bundle data = new Bundle();
                data.putString("press_back_button");
                dispatchMessage(levelPages, data);
                current_page -= 1;
            }
        }
    };

    @Override
    public void handleMessage(Bundle data) {
        String str = data.getString();
        int max_page_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_PAGE_NUM").getValue());
        if ("press_next_button".equals(str) && current_page < max_page_num) {
            current_page += 1;
        }
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_back_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.delay(delay.getDuration(), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_back_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
    }
}
