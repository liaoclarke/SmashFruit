package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class BaseScrollPane extends ScrollPane implements TransitionActor {

    protected String LOG_TAG;
    protected TransitionActor parent;
    private Table content;
    private boolean wasPanDragFling = false;

    public BaseScrollPane (StageConfig config) {
        super(null);
        setBounds(config.getPositionX(), config.getPositionY(), config.getWidth(), config.getHeight());
        content = new Table();
        //content.setDebug(true);
        super.setWidget(content);
        setFadeScrollBars(false);
    }

    public void addPage (Actor page) {
        content.add(page).expandY().fillY();
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

    private void scrollToPage () {
        final float width = getWidth();
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) return;

        Array<Actor> pages = content.getChildren();
        float pageX = 0;
        float pageWidth = 0;
        if (pages.size > 0) {
            for (Actor a : pages) {
                pageX = a.getX();
                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
        }
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
}
