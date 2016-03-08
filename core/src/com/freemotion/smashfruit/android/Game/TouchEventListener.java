package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.freemotion.smashfruit.android.Utils.StageBase;

/**
 * Created by liaoclark on 2016/3/6.
 */
public class TouchEventListener implements InputProcessor {

    public enum INPUT_EVENT {
        Reset,
        Pass,
        Error,
    };

    private StageBase stageObject;

    public TouchEventListener(StageBase stage) {
        stageObject = stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        return stageObject.handleKeyPressedEvent(parseInputEvent(keycode));
    }

    @Override
    public boolean keyUp(int keycode) {
        return stageObject.handleKeyReleasedEvent(parseInputEvent(keycode));
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return stageObject.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return stageObject.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return stageObject.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private INPUT_EVENT parseInputEvent(int keyCode) {
        switch (keyCode) {
            case Input.Keys.R:
                return INPUT_EVENT.Reset;

            case Input.Keys.P:
                return INPUT_EVENT.Pass;

            default:
                return INPUT_EVENT.Error;
        }
    }
}
