package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TextureConfig;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Sprites.Widget.BaseButton;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 4/7/2016.
 */
public class SettingsDialogButton extends BaseButton {

    private static final String SettingsCheckedButtonMarginTexture = "settings_checked_button_margin";
    private static final String SettingsButtonMarginTexture = "settings_button_margin";
    protected TextureRegion margin, checked_margin, checked_texture;
    protected Rectangle marginRectangle;
    protected boolean checked;

    public SettingsDialogButton(StageConfig config) {
        super(config);
        TextureConfig marginConfig = JsonConfigFactory.getInstance().getTextureConfig(SettingsButtonMarginTexture);
        TextureConfig checkedMarginConfig = JsonConfigFactory.getInstance().getTextureConfig(SettingsCheckedButtonMarginTexture);
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        margin = uiLoader.getTextureAtlas().findRegion(marginConfig.getRegion());
        checked_margin = uiLoader.getTextureAtlas().findRegion(checkedMarginConfig.getRegion());
        texture = uiLoader.getTextureAtlas().findRegion(config.getRegion());
        checked_texture = uiLoader.getTextureAtlas().findRegion(config.getSelected());
        marginRectangle = new Rectangle(getX(), getY(), margin.getRegionWidth() * marginConfig.getScaleX(), margin.getRegionHeight() * marginConfig.getScaleY());
        checked = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (checked) {
            batch.draw(checked_margin, getX(), getY(), marginRectangle.width, marginRectangle.height);
            batch.draw(checked_texture, getX(), getY(), textureRectangle.width, textureRectangle.height);
        } else {
            batch.draw(margin, getX(), getY(), marginRectangle.width, marginRectangle.height);
            batch.draw(texture, getX(), getY(), textureRectangle.width, textureRectangle.height);
        }
        //super.draw(batch, parentAlpha);
    }
}
