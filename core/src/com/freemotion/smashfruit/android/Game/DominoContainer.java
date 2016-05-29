package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by liaoclark on 5/22/2016.
 */
public class DominoContainer {

    private Array<DominoObject> dominoObjects;

    public DominoContainer() {
        dominoObjects = new Array<DominoObject>();
    }

    public DominoObject get(int index) {
        return dominoObjects.get(index);
    }

    public Array<DominoObject> getAll() {
        return dominoObjects;
    }

    public void add(DominoObject obj) {
        dominoObjects.add(obj);
    }

    public void clear() {
        dominoObjects.clear();
    }

    public int size() { return dominoObjects.size; }

    public void reverse() {
        dominoObjects.reverse();
    }
}
