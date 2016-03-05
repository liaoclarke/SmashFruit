package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class GameController {

    private int level, difficulty;
    private Array<DominoObject> dominoObjects;

    public GameController() {

    }

    public void enterGame() {
        level = 1;
        difficulty = 1;
        dominoObjects = new Array<DominoObject>();
        /*DominoObject object0 = new DominoObject(300, 600, 0, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object0);
        DominoObject object60 = new DominoObject(300, 600, 60, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object60);
        DominoObject object120 = new DominoObject(300, 600, 120, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object120);
        DominoObject object180 = new DominoObject(300, 600, 180, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object180);
        DominoObject object240 = new DominoObject(300, 600, 240, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object240);
        DominoObject object300 = new DominoObject(300, 600, 300, DominoObject.DOMINO_TYPE.Cuboid);
        dominoObjects.add(object300);*/
    }

    public void nextLevel() {
        level += 1;
        difficulty += 1;
    }

    public void failLevel() {

    }

    public void passLevel() {
        /* TODO */
        nextLevel();
    }

    public void update() {

    }

    public void pushDomino() {

    }

    public Array<DominoObject> getDominoObjects() {
        return dominoObjects;
    }
}
