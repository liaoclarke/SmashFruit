package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TextureConfig;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/12.
 */
public class BaseButton extends BaseActor {

    protected String LOG_TAG;
    private TextureRegion texture;
    private Rectangle textureRectangle;
    protected boolean pressed;

    public BaseButton(String keyName) {
        super();
        TextureConfig config = JsonConfigFactory.getInstance().getTextureConfig(keyName);
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        texture = uiLoader.getTextureAtlas().findRegion(config.getRegion());
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        setTouchable(Touchable.enabled);
        pressed = false;
    }

    public BaseButton(StageConfig config) {
        super();
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        texture = uiLoader.getTextureAtlas().findRegion(config.getRegion());
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), textureRectangle.width, textureRectangle.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
