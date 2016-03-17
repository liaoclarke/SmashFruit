package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TextureConfig;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/13.
 */
public class BaseImage extends BaseActor {

    protected String LOG_TAG;
    private TextureRegion texture;
    private TextureRegion selected_texture;
    private Rectangle textureRectangle;
    private boolean isSelected;

    public BaseImage(String keyName) {
        super();
        TextureConfig config = JsonConfigFactory.getInstance().getTextureConfig(keyName);
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        texture = uiLoader.getTextureAtlas().findRegion(config.getRegion());
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
    }

    public BaseImage(StageConfig config) {
        super();
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        texture = uiLoader.getTextureAtlas().findRegion(config.getRegion());
        if (config.getSelected() != null) {
            selected_texture = uiLoader.getTextureAtlas().findRegion(config.getSelected());
        }
        isSelected = false;
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
    }

    public void select(boolean selected) {
        isSelected = selected;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isSelected) {
            batch.draw(selected_texture, getX(), getY(), textureRectangle.width, textureRectangle.height);
        } else {
            batch.draw(texture, getX(), getY(), textureRectangle.width, textureRectangle.height);
        }
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
