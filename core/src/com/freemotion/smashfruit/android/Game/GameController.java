package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class GameController {

    private final static String LOG_TAG = GameController.class.getSimpleName();
    private int level, difficulty, dominoNum;
    private DominoContainer dominoObjects;

    public GameController() {

    }

    public void enterGame() {
        level = 1;
        difficulty = 1;
        //dominoNum = MathUtils.random(6 + difficulty, 10 + difficulty);
        dominoNum = 3;
        dominoObjects = new DominoContainer();
        resetGame();
    }

    public void resetGame() {
        dominoObjects.clear();
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        int board_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_WIDTH").getValue());
        int board_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_HEIGHT").getValue());
        int firstCuboid_x = MathUtils.random((int)(board_x + board_width * 0.2f), (int)(board_x + board_width * 0.8f));
        int firstCuboid_y = MathUtils.random((int)(board_y + board_width * 0.2f), (int)(board_y + board_height * 0.8f));

        Vector2 position = new Vector2(firstCuboid_x, firstCuboid_y);
        int direction = 60 * MathUtils.random(0, 5);
        DominoObject tomato = DominoObject.createObject((int)position.x, (int)position.y, direction, DominoObject.DOMINO_TYPE.Tomato);
        DominoObject nextCuboid = tomato;
        dominoObjects.add(tomato);
        Gdx.app.error(LOG_TAG, "DominoObject: " + tomato.getCenterPos()
                               + " direction: " + tomato.getDirection()
                               + " type: " + tomato.getDominoType());
        nextCuboid = DominoObject.createObject(nextCuboid, dominoObjects, DominoObject.DOMINO_TYPE.Cuboid, true);
        dominoObjects.add(nextCuboid);
        Gdx.app.error(LOG_TAG, "DominoObject: " + nextCuboid.getCenterPos()
                               + " direction: " + nextCuboid.getDirection()
                               + " type: " + nextCuboid.getDominoType());
        for (int i = 2; i < dominoNum; i++) {
            //direction = DominoObject.generateDirection(nextCuboid, dominoObjects);
            //position = DominoObject.generatePosition((int)nextCuboid.getCenterPos().x, (int) nextCuboid.getCenterPos().y, direction);
            nextCuboid = DominoObject.createObject(nextCuboid, dominoObjects, DominoObject.DOMINO_TYPE.Cuboid, false);
            dominoObjects.add(nextCuboid);
            Gdx.app.error(LOG_TAG, "DominoObject: " + nextCuboid.getCenterPos()
                               + " direction: " + nextCuboid.getDirection()
                               + " type: " + nextCuboid.getDominoType());
        }
        dominoObjects.reverse();
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
        return dominoObjects.getAll();
    }
}
