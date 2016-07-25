package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Game.MessageType;
import com.freemotion.smashfruit.android.Misc.DominoConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;

/**
 * Created by liaoclark on 7/25/16.
 */
public class Cuboid extends DominoActor {

    public Cuboid(DominoConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        setTouchable(Touchable.enabled);
        addListener(listener);
        //Gdx.app.error(LOG_TAG, " textureRectangle: " + textureRectangle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }

    private InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, "touch down");
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, "touch up");
            Bundle data = new Bundle();
            data.putString(MessageType.Push_Cuboid);
            data.putCallback(Cuboid.this);
            dispatchMessage(MessageType.Push_Cuboid, data);
            super.touchUp(event, x, y, pointer, button);
        }
    };

    @Override
    public boolean doMessageCallback(Bundle data) {
        playAnimation();
        return true;
    }
}
