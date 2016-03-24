package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.LevelConfig;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Resources.UITextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class LevelButton extends BaseButton {

    private String LOG_TAG;
    private int level;
    private boolean isPan;
    private LevelConfig levelData;
    private static TextureRegion unlockedTexture, lockedTexture;

    public LevelButton(StageConfig viewConfig, LevelConfig dataConfig) {
        super(viewConfig);
        this.level = dataConfig.getLevel();
        levelData = dataConfig;
        UITextureLoader uiLoader = (UITextureLoader) ResourceManager.getInstance().findLoader(UITextureLoader.class.getSimpleName());
        if (lockedTexture == null) {
            lockedTexture = uiLoader.getTextureAtlas().findRegion("locked_level");
        }
        if (unlockedTexture == null) {
            unlockedTexture = uiLoader.getTextureAtlas().findRegion("unlocked_level");
        }
        LOG_TAG = this.getClass().getSimpleName();
        addListener(listener);
    }

    protected InputListener listener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch down x: " + x + " y: " + y);
            isPan = false;
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            isPan = true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch up x: " + x + " y: " + y);
            if (!isPan && !levelData.getPass()) {
                showUnlockDialog();
            }
            super.touchUp(event, x, y, pointer, button);
        }
    };

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (levelData.getPass()) {
            texture = unlockedTexture;
        } else {
            texture = lockedTexture;
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
    }

    private void showUnlockDialog() {

    }
}
