package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageListener;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class NextButton extends BaseButton implements MessageDispatch {

    private MessageHub messageHub;

    public NextButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        addListener(listener);
        setName(config.getConfigName());
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
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch down");
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch up");
            Bundle data = new Bundle();
            data.putString("press_next_button");
            dispatchMessage("press_next_button", data);
            super.touchUp(event, x, y, pointer, button);
        }
    };

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_next_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_next_button");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
    }
}
