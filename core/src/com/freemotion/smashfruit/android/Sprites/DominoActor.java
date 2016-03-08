package com.freemotion.smashfruit.android.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.GameActor;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/8.
 */
public class DominoActor extends GameActor {

    private enum DOMINO_STATE {
        Stand,
        Lean,
    };

    protected DOMINO_STATE status;
    protected Animation leanAnimation;
    protected TextureRegion textureRegion;
    protected Rectangle textureRectangle;
    protected DominoActor nextDomino;
    protected String dominoType;
    protected Pixmap touchableMask;
    protected float leanSpeed;
    protected float stateTime;

    public DominoActor(DominoObject object) {
        super();
        switch (object.getDominoType()) {
            case Cuboid:
                dominoType = "cuboid";
                break;

            case Cylinder:
                dominoType = "cylinder";
                break;
        }
        AnimationConfig config = JsonConfigFactory.getInstance().getAnimationConfig(dominoType + object.getDirection());
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        leanAnimation = new Animation(config.getDuration(), sceneLoader.getTextureAtlas().findRegions(config.getRegion()));
        leanAnimation.setPlayMode(config.getMode());
        leanSpeed = config.getDuration();
        textureRegion = leanAnimation.getKeyFrame(0);
        textureRectangle = new Rectangle(object.getCenterPos().x - textureRegion.getRegionWidth() * 0.5f,
                                         object.getCenterPos().y - textureRegion.getRegionHeight() * 0.5f,
                                         textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        touchableMask = generateTouchableMask(textureRegion);
        stateTime = 0;
        status = DOMINO_STATE.Stand;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        switch (status) {
            case Stand:
                break;

            case Lean:
                stateTime += deltaTime;
                if (leanAnimation.isAnimationFinished(stateTime) && nextDomino != null && nextDomino.getStatus() == DOMINO_STATE.Stand) {
                    nextDomino.playAnimation(caculateLeanSpeed());
                }
                break;
        }
        textureRegion = leanAnimation.getKeyFrame(stateTime);
    }

    public void playAnimation() {
        status = DOMINO_STATE.Lean;
    }

    public void playAnimation(float leanSpeed) {
        Gdx.app.error(LOG_TAG, "playAnimation: " + leanSpeed);
        leanAnimation.setFrameDuration(leanSpeed);
        this.leanSpeed = leanSpeed;
        status = DOMINO_STATE.Lean;
    }

    public void setNextDomino(DominoActor nextDomino) {
        this.nextDomino = nextDomino;
    }

    public String getDominoType() {
        return dominoType;
    }

    public DOMINO_STATE getStatus() {
        return status;
    }

    private float caculateLeanSpeed() {
        return leanSpeed * Float.parseFloat(JsonConfigFactory.getInstance().getKeyConfig("LEAN_SPEED_SCALE").getValue());
    }

    private Pixmap generateTouchableMask(TextureRegion fromTexture) {
        TextureData texData = fromTexture.getTexture().getTextureData();
        if(!texData.isPrepared()) texData.prepare();

        return texData.consumePixmap();
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if((touchable && getTouchable() != Touchable.enabled)) {
            return null;
        } else {
            //Gdx.app.error(LOG_TAG, "domino hit x: " + x + " y: " + y);
            int pix = (touchableMask != null) ? touchableMask.getPixel(textureRegion.getRegionX() + (int) x, textureRegion.getRegionY() + (int) (getHeight() - y)) : 1;
            return ((pix & 0x000000ff) != 0) ? this : null;
        }
    }
}