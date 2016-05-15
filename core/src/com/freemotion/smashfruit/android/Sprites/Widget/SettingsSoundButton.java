package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 4/2/2016.
 */
public class SettingsSoundButton extends SettingsDialogButton {

    public SettingsSoundButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        setName("SettingsSoundButton");
        addListener(listener);
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
            checked = checked ? false : true;
            if (!checked) {

            }
            super.touchUp(event, x, y, pointer, button);
        }
    };
}
