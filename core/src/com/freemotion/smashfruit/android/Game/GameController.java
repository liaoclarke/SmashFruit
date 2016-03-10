package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class GameController {

    private final static String LOG_TAG = GameController.class.getSimpleName();
    private int level, difficulty, dominoNum;
    private Array<DominoObject> dominoObjects;

    public GameController() {

    }

    public void enterGame() {
        level = 1;
        difficulty = 1;
        //dominoNum = MathUtils.random(6 + difficulty, 10 + difficulty);
        dominoNum = 12;
        dominoObjects = new Array<DominoObject>();
        resetGame();
    }

    public void resetGame() {
        dominoObjects.clear();
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        int board_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_WIDTH").getValue());
        int board_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_HEIGHT").getValue());
        int firstCuboid_x = MathUtils.random(board_x, board_x + board_width);
        int firstCuboid_y = MathUtils.random(board_y, board_y + board_height);
        DominoObject firstCuboid = new DominoObject(firstCuboid_x, firstCuboid_y, 60 * MathUtils.random(0, 5), DominoObject.DOMINO_TYPE.Cuboid);
        Gdx.app.error(LOG_TAG, " DominoObject: " + firstCuboid.getCenterPos());
        dominoObjects.add(firstCuboid);
        for (int i = 1; i < dominoNum; i++) {
            DominoObject currentCuboid = new DominoObject(firstCuboid, DominoObject.DOMINO_TYPE.Cuboid);
            dominoObjects.add(currentCuboid);
            firstCuboid = currentCuboid;
        }
    }

    public void nextLevel() {
        level += 1;
        difficulty += 1;
    }

    public void failLevel() {

    }

    public void passLevel() {
    }

    public void update() {

    }

    public void pushDomino() {

    }

    public Array<DominoObject> getDominoObjects() {
        return dominoObjects;
    }
}
