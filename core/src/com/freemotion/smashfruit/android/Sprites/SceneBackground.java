package com.freemotion.smashfruit.android.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.SceneConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.GameActor;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/4.
 */
public class SceneBackground extends GameActor {

    private TextureAtlas.AtlasRegion texture;
    private Rectangle textureRectangle;

    public SceneBackground() {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        SceneConfig config = JsonConfigFactory.getInstance().getSceneConfig("background");
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        texture = sceneLoader.getTextureAtlas().findRegion(config.getRegion());
        textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                                         texture.getRegionWidth(), texture.getRegionHeight());
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
}
