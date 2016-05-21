package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

/**
 * Created by liaoclark on 4/24/2016.
 */
public class TimeRaceGameBackground extends BaseImage {

    private Action debugAction = new Action() {

        public boolean act(float delta) {
            selfAlpha = 1;
            isDrawing = true;
            Gdx.app.error(LOG_TAG, " set alpha = 0 then set visible enable");
            return true;
        }
    };

    private float selfAlpha;
    private boolean isDrawing;

    public TimeRaceGameBackground(StageConfig config) {
        super(config, (SceneTextureLoader) ResourceManager.getInstance().findLoader(SceneTextureLoader.class.getSimpleName()));
        LOG_TAG = this.getClass().getSimpleName();
        selfAlpha = 0f;
        isDrawing = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
            Color c = this.getColor();
            batch.setColor(c.r, c.g, c.b, c.a * selfAlpha);
            super.draw(batch, parentAlpha);
            batch.setColor(c.r, c.g, c.b, c.a);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("timerace_game_background");
        TransitionConfig to = JsonConfigFactory.getInstance().getTransitionConfig(sc, "fade_in");
        addAction(Actions.sequence(Actions.delay(0.0f), Actions.alpha(0f), debugAction, Actions.fadeIn(to.getDuration()), completeShowAction));
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
