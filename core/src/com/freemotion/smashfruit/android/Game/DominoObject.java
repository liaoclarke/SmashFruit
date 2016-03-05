package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class DominoObject {

    public enum DOMINO_TYPE {
        Cuboid,
        Cylinder,
        Tomato
    };

    private int direction;
    private Vector2 centerPos;
    private DOMINO_TYPE dominoType;

    public DominoObject(int centerX, int centerY, int dir, DOMINO_TYPE type) {
        centerPos = new Vector2(centerX, centerY);
        direction = dir;
        dominoType = type;
    }

    public Vector2 getCenterPos() {
        return centerPos;
    }

    public int getDirection() {
        return direction;
    }

    public DOMINO_TYPE getDominoType() {
        return dominoType;
    }
}
