package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by liaoclark on 7/24/16.
 */
public class DominoInstance {

    private Vector2 mPosition;
    private String mDirection;
    private String mShape;

    public DominoInstance() {
        mPosition = new Vector2(0, 0);
    }

    public void setPosition(float x, float y) {
        mPosition.set(x, y);
    }

    public void setDirection(String direction) {
        mDirection = direction;
    }

    public void setShape(String shape) {
        mShape = shape;
    }
}
