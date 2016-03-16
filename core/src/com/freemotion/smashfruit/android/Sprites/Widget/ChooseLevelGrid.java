package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;

/**
 * Created by liaoclark on 2016/3/16.
 */
public class ChooseLevelGrid extends BaseScrollPane {

    public ChooseLevelGrid(StageConfig config) {
        super(config);
        LOG_TAG = this.getClass().getSimpleName();
        setFlingTime(0.1f);
        inflateContent();
    }

    private void inflateContent() {
        int level = 1;
        int page_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_PAGE_NUM").getValue());
        int row_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_LEVEL_GRID_ROW").getValue());
        int col_num = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FIND_BEST_LEVEL_GRID_COL").getValue());
        int page_padding = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_PAGE_PADDING").getValue());
        int cell_x_space = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_CELL_X_SPACE").getValue());
        int cell_y_space = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("LEVEL_GRID_CELL_Y_SPACE").getValue());
        for (int page = 0; page < page_num; page++) {
            Table levels = new Table();
            //levels.setDebug(true);
            levels.defaults().space(cell_y_space, cell_x_space, cell_y_space, cell_x_space);
            levels.padLeft(page_padding).padRight(page_padding);
            for (int row = 0; row < row_num; row++) {
                levels.row();
                for (int col = 0; col < col_num; col++) {
                    levels.add(getLevelButton(level++));
                }
            }
            addPage(levels);
        }
    }

    private LevelButton getLevelButton(int level) {
        StageConfig config = new StageConfig();
        config.setAtlas("graphic/ui.txt");
        config.setRegion("unlocked_level");
        config.setPositionX(0);
        config.setPositionY(0);
        config.setScaleX(1);
        config.setScaleY(1);
        return new LevelButton(config, level);
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
    }

    @Override
    public void hide() {
        Gdx.app.error(LOG_TAG, " hide");
    }
}
