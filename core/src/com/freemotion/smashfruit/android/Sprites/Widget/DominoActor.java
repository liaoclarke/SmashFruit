package com.freemotion.smashfruit.android.Sprites.Widget;

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
import com.freemotion.smashfruit.android.Misc.DominoConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 7/25/16.
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
    protected Rectangle touchableRectangle;
    protected float leanSpeed;
    protected float stateTime;

    public DominoActor(DominoConfig dc) {
        super();
        String animationName ;
        if ("Cuboid".equals(dc.getShape())) {
            dominoType = "cuboid";
            animationName = dominoType + dc.getDegree();
        } else if ("Cylinder".equals(dc.getShape())) {
            dominoType = "cylinder";
            animationName = dominoType + dc.getDegree();
        } else if ("Tomato".equals(dc.getShape())) {
            dominoType = "tomato";
            animationName = "tomato0";
        } else {
            animationName = null;
        }

        AnimationConfig ac = JsonConfigFactory.getInstance().getAnimationConfig(animationName);
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        leanAnimation = new Animation(ac.getDuration(), sceneLoader.getTextureAtlas().findRegions(ac.getRegion()));
        leanAnimation.setPlayMode(ac.getMode());
        leanSpeed = ac.getDuration();
        textureRegion = leanAnimation.getKeyFrame(0);
        textureRectangle = new Rectangle(dc.getTile().getTilePositionX(), dc.getTile().getTilePositionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        touchableRectangle = new Rectangle(dc.getTile().getTouchableRegionCenterX() - dc.getTile().getTouchableRegionWidth() * 0.5f,
                                           dc.getTile().getTouchabelRegionCenterY() - dc.getTile().getTouchableRegionHeight() * 0.5f,
                                           dc.getTile().getTouchableRegionWidth(), dc.getTile().getTouchableRegionHeight());
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

    /*private Pixmap generateTouchableMask(TextureRegion fromTexture) {
        TextureData texData = fromTexture.getTexture().getTextureData();
        if(!texData.isPrepared()) texData.prepare();

        return texData.consumePixmap();
    }*/

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        //return super.hit(x, y, touchable);
        if((touchable && getTouchable() != Touchable.enabled)) {
            return null;
        } else {
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
