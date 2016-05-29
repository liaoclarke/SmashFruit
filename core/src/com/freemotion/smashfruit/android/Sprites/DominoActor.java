package com.freemotion.smashfruit.android.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.freemotion.smashfruit.android.Game.DominoObject;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/8.
 */
public class DominoActor extends Actor {

    protected String LOG_TAG = "DominoActor";
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
    protected Vector2 touchPosition;
    protected Rectangle touchableRectangle;
    protected Pixmap touchableMask;
    protected float leanSpeed;
    protected float stateTime;

    public DominoActor(DominoObject object) {
        super();
        String animationName ;
        switch (object.getDominoType()) {
            case Cuboid:
                dominoType = "cuboid";
                animationName = dominoType + object.getDirection();
                break;

            case Cylinder:
                dominoType = "cylinder";
                animationName = dominoType + object.getDirection();
                break;

            case Tomato:
                dominoType = "tomato";
                animationName = "tomato0";
                break;

            default:
                animationName = null;
                break;
        }
        AnimationConfig config = JsonConfigFactory.getInstance().getAnimationConfig(animationName);
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        leanAnimation = new Animation(config.getDuration(), sceneLoader.getTextureAtlas().findRegions(config.getRegion()));
        leanAnimation.setPlayMode(config.getMode());
        leanSpeed = config.getDuration();
        textureRegion = leanAnimation.getKeyFrame(0);
        textureRectangle = new Rectangle(object.getCenterPos().x - textureRegion.getRegionWidth() * 0.5f,
                                         object.getCenterPos().y - textureRegion.getRegionHeight() * 0.5f,
                                         textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        touchPosition = new Vector2(textureRegion.getRegionX(), textureRegion.getRegionY());
        /*touchableRectangle = new Rectangle(object.getCenterPos().x - (Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_X").getValue())) * 0.5f,
                                           object.getCenterPos().y - (Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_Y").getValue())) * 0.5f,
                                           Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_X").getValue()),
                                           Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_Y").getValue()));
                                           */
        touchableRectangle = new Rectangle((textureRegion.getRegionWidth() - Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_X").getValue())) * 0.5f,
                                           (textureRegion.getRegionHeight() - Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_Y").getValue())) * 0.5f,
                                           Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_X").getValue()),
                                           Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("TOUCHABLE_OFFSET_Y").getValue()));
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
        //return super.hit(x, y, touchable);
        if((touchable && getTouchable() != Touchable.enabled)) {
            return null;
        } else {
            int pix = (touchableMask != null) ? touchableMask.getPixel((int) touchPosition.x + (int) x, (int) touchPosition.y + (int) (getHeight() - y)) : 1;
            //return (super.hit(x, y, touchable) != null) && ((pix & 0x000000ff) != 0) ? this : null;
            boolean t =  (super.hit(x, y, touchable) != null) &&
                   (x > touchableRectangle.x &&
                    x < touchableRectangle.x + touchableRectangle.width &&
                    y > touchableRectangle.y &&
                    y < touchableRectangle.y + touchableRectangle.height);
            if (t) {
                Gdx.app.error(LOG_TAG, "domino hit x: " + x + " y: " + y + " actorX: " + getX() + " actorY: " + getY() + " touchRegionX: " + touchableRectangle.x + " touchRegionY: " + touchableRectangle.y);
            }
            return t ? this : null;
        }
}
}
