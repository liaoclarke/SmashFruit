package com.freemotion.smashfruit.android.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.SceneConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.GameActor;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 1/22/16.
 */
public class Cuboid extends GameActor {

    private Animation leanAnimation;
    private TextureRegion textureRegion;
    private Rectangle textureRectangle;
    private float stateTime;

    public Cuboid(DominoObject object) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        AnimationConfig config = JsonConfigFactory.getInstance().getAnimationConfig("cuboid" + object.getDirection());
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        leanAnimation = new Animation(config.getDuration(), sceneLoader.getTextureAtlas().findRegions(config.getRegion()));
        leanAnimation.setPlayMode(config.getMode());
        textureRegion = leanAnimation.getKeyFrame(0);
        textureRectangle = new Rectangle(object.getCenterPos().x, object.getCenterPos().y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        Gdx.app.log(LOG_TAG, " textureRectangle: " + textureRectangle);
        stateTime = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        textureRegion = leanAnimation.getKeyFrame(stateTime);
        stateTime += deltaTime;
    }
}
