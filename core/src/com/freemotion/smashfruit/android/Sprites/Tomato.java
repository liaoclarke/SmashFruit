package com.freemotion.smashfruit.android.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Stages.GameStage;

/**
 * Created by liaoclark on 5/18/2016.
 */
public class Tomato extends DominoActor {

    public Tomato(DominoObject object) {
        super(object);
        LOG_TAG = this.getClass().getSimpleName();
        setTouchable(Touchable.enabled);
        //addListener(listener);
        Gdx.app.error(LOG_TAG, " textureRectangle: " + textureRectangle);
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
            if (!(((GameStage) getStage()).isDominoPushed())) {
                ((GameStage) getStage()).pushDomino();
                playAnimation();
                return super.touchDown(event, x, y, pointer, button);
            } else {
                return false;
            }
            //return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, "touch up");
            if (!(((GameStage) getStage()).isDominoPushed())) {
                ((GameStage) getStage()).pushDomino();
                playAnimation();
                super.touchUp(event, x, y, pointer, button);
            }
        }
    };
}
