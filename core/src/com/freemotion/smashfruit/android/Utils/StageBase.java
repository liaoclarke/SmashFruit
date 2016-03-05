package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

/**
 * Created by liaoclark on 2016/3/4.
 */
public class StageBase extends Stage {

    private OrthographicCamera stageCamera;

    protected void setupViewPort() {
        int viewport_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("APP_WIDTH").getValue());
        int viewport_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("APP_HEIGHT").getValue());
        stageCamera = new OrthographicCamera(viewport_width, viewport_height);
        stageCamera.position.set(stageCamera.viewportWidth / 2f, stageCamera.viewportHeight / 2f, 0f);
        stageCamera.update();
        this.setViewport(new ScalingViewport(Scaling.stretch, viewport_width, viewport_height, stageCamera));
        getViewport().setScreenX(0);
        getViewport().setScreenY(0);
        getViewport().setScreenWidth(Gdx.graphics.getWidth());
        getViewport().setScreenHeight(Gdx.graphics.getHeight());
    }

    protected void setupInput() {
        Gdx.input.setInputProcessor(this);
    }
}