package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Game.TouchEventListener;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TextureConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Stages.GameStage;
import com.freemotion.smashfruit.android.Stages.TimeRaceStage;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/8.
 */
public class PassButton extends BaseButton {

    protected String LOG_TAG;
    //private TextureRegion texture;
    //private Rectangle textureRectangle;
    private TextureRegion pressedTexture;
    private Rectangle pressedTextureRectangle;
    private float selfAlpha;

    private Action setOpaque = new Action() {
        public boolean act(float delta) {
            selfAlpha = 1;
            Gdx.app.error(LOG_TAG, " set alpha = 0 then set visible enable");
            return true;
        }
    };

    public PassButton(StageConfig config) {
        super(config, ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName()));
        LOG_TAG = PassButton.class.getSimpleName();
        SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        texture = sceneLoader.getTextureAtlas().findRegion(config.getRegion());
        pressedTexture = sceneLoader.getTextureAtlas().findRegion(config.getRegion());
        pressedTextureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
                pressedTexture.getRegionWidth() * config.getScaleX(), pressedTexture.getRegionHeight() * config.getScaleY());
        setName("ResetButton");
        addListener(listener);
        selfAlpha = 0;
        //TextureConfig config = JsonConfigFactory.getInstance().getTextureConfig("pass");
        //SceneTextureLoader sceneLoader = (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName());
        //texture = sceneLoader.getTextureAtlas().findRegion(config.getRegion());
        //textureRectangle = new Rectangle(config.getPositionX(), config.getPositionY(),
        //                                 texture.getRegionWidth() * config.getScaleX(), texture.getRegionHeight() * config.getScaleY());
        //setBounds(textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
        //setTouchable(Touchable.enabled);
        //addListener(listener);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = this.getColor();
        batch.setColor(c.r, c.g, c.b, c.a * selfAlpha);
        super.draw(batch, parentAlpha);
        if (pressed) {
            batch.draw(pressedTexture, pressedTextureRectangle.x, pressedTextureRectangle.y, pressedTextureRectangle.width, pressedTextureRectangle.height);
        }
        batch.setColor(c.r, c.g, c.b, c.a);
        //batch.draw(texture, textureRectangle.x, textureRectangle.y, textureRectangle.width, textureRectangle.height);
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

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("timerace_game_pass");
        TransitionConfig to = JsonConfigFactory.getInstance().getTransitionConfig(sc, "fade_in");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "show_delay");
        addAction(Actions.sequence(Actions.delay(0.0f), Actions.alpha(0), setOpaque, Actions.delay(delay.getDuration()), Actions.fadeIn(to.getDuration()), completeShowAction));
        isShowup = false;
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        /*StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("timerace_game_background");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "hide_delay");
        TransitionConfig to = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(to.getPositionX(), to.getPositionY()), Actions.visible(false)));
        */
    }
}
