package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;

/**
 * Created by liaoclark on 3/26/2016.
 */
public class DialogCloseButton extends BaseButton implements MessageDispatch {

    private MessageHub messageHub;

    public DialogCloseButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        setName("UnlockDialogCloseButton");
        addListener(listener);
    }


    protected InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch down");
            pressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch up");
            pressed = false;
            super.touchUp(event, x, y, pointer, button);
            Bundle data = new Bundle();
            dispatchMessage("close_unlock_dialog", data);
        }
    };

    @Override
    public void setMessageHub(MessageHub hub) {
        messageHub = hub;
    }

    @Override
    public void dispatchMessage(String message, Bundle data) {
        messageHub.forwardMessageToListener(message, data);
    }
}
