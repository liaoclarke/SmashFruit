package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class BaseScrollPane extends ScrollPane implements TransitionActor {

    protected String LOG_TAG;
    protected TransitionActor parent;
    protected Table content;
    protected String configFile, configName;
    private boolean wasPanDragFling = false;


    public BaseScrollPane (StageConfig config) {
        super(null);
        setBounds(config.getPositionX(), config.getPositionY(), config.getWidth(), config.getHeight());
        configFile = config.getConfigFile();
        configName = config.getConfigName();
        content = new Table();
        //content.setDebug(true);
        super.setWidget(content);
        setFadeScrollBars(false);
        setFlingTime(0.1f);
        addListener(inputListener);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }

    @Override
    public void setWidth (float width) {
        super.setWidth(width);
        if (content != null) {
            for (Cell cell : content.getCells()) {
                cell.width(width);
            }
            content.invalidate();
        }
    }

    public void setPageSpacing (float pageSpacing) {
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }

    protected void scrollToPage () {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public Actor getActor() {
        return this;
    }

    @Override
    public float getDuration() {
        return 0;
    }

    @Override
    public void setParent(TransitionActor parent) {

    }

    protected InputListener inputListener = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch down" + " scrollX: " + getScrollX());
            scrollToPage();
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.error(LOG_TAG, this.getClass().getSimpleName() + " button touch up" + " scrollX: " + getScrollX());
            super.touchUp(event, x, y, pointer, button);
        }
    };
}
