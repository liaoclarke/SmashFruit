package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.freemotion.smashfruit.android.Game.MessageType;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Stages.MenuStage;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageListener;

/**
 * Created by liaoclark on 3/26/2016.
 */
public class DialogCloseButton extends BaseButton {

    private TransitionActor parentDialog;

    public DialogCloseButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        setName("DialogCloseButton");
        addListener(listener);
    }

    public void setParentDialog(TransitionActor parent) {
        parentDialog = parent;
    }

    protected InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, LOG_TAG + " button touch down");
            pressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, LOG_TAG + " button touch up");
            pressed = false;
            super.touchUp(event, x, y, pointer, button);
            Bundle data = new Bundle();
            data.putActor(parentDialog);
            data.putString(MessageType.Close_Settings_Dialog);
            data.putCallback(DialogCloseButton.this);
            dispatchMessage(MessageType.Close_Settings_Dialog, data);
        }
    };

    @Override
    public void setMessageHub(MessageHub hub) {
        super.setMessageHub(hub);
        hub.addHubObserver(this);
    }
}
