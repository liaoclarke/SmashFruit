package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Game.TouchEventListener;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.TextureConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Stages.GameStage;
import com.freemotion.smashfruit.android.Utils.GameActor;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/8.
 */
public class PassButton extends GameActor {

    private TextureRegion texture;
    private Rectangle textureRectangle;

    public PassButton() {
        super();
        LOG_TAG = PassButton.class.getSimpleName();
        TextureConfig config = JsonConfigFactory.getInstance().getTextureConfig("pass");
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        texture = sceneLoader.getTextureAtlas().findRegion(config.getRegion());
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                                         texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        setTouchable(Touchable.enabled);
        addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, "passButton touch down");
            ((GameStage) getStage()).handleKeyPressedEvent(TouchEventListener.INPUT_EVENT.Pass);
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, "passButton touch up");
            super.touchUp(event, x, y, pointer, button);
        }
    };
}
