package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/12.
 */
public class RateButton extends BaseButton {

    private TextureRegion pressedTexture;
    private Rectangle pressedTextureRectangle;

    public RateButton(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        addListener(listener);
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        pressedTexture = uiLoader.getTextureAtlas().findRegion("circle_press_effect");
        pressedTextureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                pressedTexture.getRegionWidth() * config.getScaleX(), pressedTexture.getRegionHeight() * config.getScaleY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (pressed) {
            batch.draw(pressedTexture, pressedTextureRectangle.x, pressedTextureRectangle.y, pressedTextureRectangle.width, pressedTextureRectangle.height);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
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
        }
    };

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("rate");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_up");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("rate");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_down");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
    }
}
