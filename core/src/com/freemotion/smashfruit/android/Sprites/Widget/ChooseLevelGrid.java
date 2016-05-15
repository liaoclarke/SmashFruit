package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.LevelConfig;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Misc.TransitionConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageDispatch;
import com.freemotion.smashfruit.android.Utils.MessageHub;
import com.freemotion.smashfruit.android.Utils.MessageListener;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class ChooseLevelGrid extends BaseScrollPane implements MessageDispatch, MessageListener {

    private MessageHub messageHub;

    public ChooseLevelGrid(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        inflateContent();
        setName(configName);
    }

    private void inflateContent() {
        int page_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_PAGE_NUM").getValue());
        int row_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_LEVEL_GRID_ROW").getValue());
        int col_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_LEVEL_GRID_COL").getValue());
        int page_padding = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_PAGE_PADDING").getValue());
        int cell_x_space = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_CELL_X_SPACE").getValue());
        int cell_y_space = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_CELL_Y_SPACE").getValue());
        StageConfig viewConfig = new StageConfig();
        viewConfig.setAtlas("graphic/ui.txt").setRegion("unlocked_level").setPositionX(0).setPositionY(0).setScaleX(1).setScaleY(1);
        for (int page = 0; page < page_num; page++) {
            String levelConfigName = JsonConfigFactory.getInstance().createLevelConfigs(configFile + page);
            Table levels = new Table();
            //levels.setDebug(true);
            levels.defaults().space(cell_y_space, cell_x_space, cell_y_space, cell_x_space);
            levels.padLeft(page_padding).padRight(page_padding);
            for (int row = 0; row < row_num; row++) {
                levels.row();
                for (int col = 0; col < col_num; col++) {
                    LevelConfig dataConfig = JsonConfigFactory.getInstance().getLevelConfig(levelConfigName, page + "-" + row + "-" + col);
                    levels.add(new LevelButton(viewConfig, dataConfig));
                }
            }
            content.add(levels).expandY().fillY();
        }
    }

    @Override
    protected void scrollToPage() {
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
            //Gdx.app.error(LOG_TAG, " scrollX: " + scrollX + " pageX: " + pageX + " width: " + width + " maxX" + maxX);
            Bundle data = new Bundle();
            data.putInteger((int) scrollX);
            data.putString("scroll_level_page");
            dispatchMessage("scroll_level_page", data);
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
        }
    }

    @Override
    public void setMessageHub(MessageHub hub) {
        messageHub = hub;
    }

    @Override
    public void dispatchMessage(String message, Bundle data) {
        messageHub.forwardMessageToListener(message, data);
    }

    @Override
    public void handleMessage(Bundle data) {
        String message = data.getString();
        float scrollX = getScrollX();
        if ("press_next_button".equals(message)) {
            scrollX += 400;
        } else if ("press_back_button".equals(message)) {
            if (scrollX > 0) {
                scrollX -= 400;
            }
        }
        setScrollX(scrollX);
        scrollToPage();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void show() {
        Gdx.app.error(LOG_TAG, " show");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_level_grid");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_left");
        TransitionConfig delay = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_delay");
        addAction(Actions.sequence(Actions.delay(delay.getDuration()), Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration())));
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
        StageConfig sc = JsonConfigFactory.getInstance().getStageConfig("findbest_level_grid");
        TransitionConfig tc = JsonConfigFactory.getInstance().getTransitionConfig(sc, "move_right");
        addAction(Actions.moveTo(tc.getPositionX(), tc.getPositionY(), tc.getDuration()));
    }
}
