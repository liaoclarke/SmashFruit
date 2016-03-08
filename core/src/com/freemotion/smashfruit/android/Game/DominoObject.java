package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.freemotion.smashfruit.android.Misc.JsonConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

import java.util.Vector;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class DominoObject {

    private final static String LOG_TAG = DominoObject.class.getSimpleName();

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
        dominoType = type;
        direction = dir;
    }

    public DominoObject(DominoObject firstObject, DOMINO_TYPE type) {
        dominoType = type;
        centerPos = new Vector2(0, 0);
        direction = firstObject.getDirection() + 60 * MathUtils.random(-1, 1);
        if (direction < 0) {
            direction += 360;
        } else if (direction == 360) {
            direction -= 360;
        }

        int collisionRegionWidth = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_WIDTH").getValue());
        int collisionRegionNearestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_NEAREST").getValue());
        int collisionRegionFurthestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_FURTHEST").getValue());
        int tp = MathUtils.randomSign() * MathUtils.random(0, collisionRegionWidth);
        float rad = MathUtils.degreesToRadians * (180 - firstObject.getDirection());
        centerPos.x = (int) (firstObject.getCenterPos().x + tp * MathUtils.cos(rad));
        centerPos.y = (int) (firstObject.getCenterPos().y + tp * MathUtils.sin(rad));
        int np = MathUtils.random(collisionRegionNearestDistance, collisionRegionFurthestDistance);
        rad = MathUtils.degreesToRadians * firstObject.getDirection();
        centerPos.x = (int) (centerPos.x + np * MathUtils.cos(rad));
        centerPos.y = (int) (centerPos.y + np * MathUtils.sin(rad));

        if (isOutOfLeftBoundary()) {
            if (firstObject.getDirection() == 120) {
                direction = firstObject.getDirection() - 60;
            } else if (firstObject.getDirection() == 240) {
                direction = firstObject.getDirection() + 60;
            }
        } else if (isOutOfRightBoundary()) {
            if (firstObject.getDirection() == 60) {
                direction = firstObject.getDirection() + 60;
            } else if (firstObject.getDirection() == 300) {
                direction = firstObject.getDirection() - 60;
            }
        }

        if (isOutOfTopBoundary()) {
            if (firstObject.getDirection() == 120) {
                direction = firstObject.getDirection() + 60;
            } else if (firstObject.getDirection() == 60) {
                direction = firstObject.getDirection() - 60;
            }
        } else if (isOutOfBottomBoundary()) {
            if (firstObject.getDirection() == 240) {
                direction = firstObject.getDirection() - 60;
            } else if (firstObject.getDirection() == 300) {
                direction = firstObject.getDirection() + 60;
            }
        }

        if (direction < 0) {
            direction += 360;
        } else if (direction == 360) {
            direction -= 360;
        }
        Gdx.app.error(LOG_TAG, " centerPos: " + centerPos + " direction: " + direction);
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

    private boolean isOutOfTopBoundary() {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        int board_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_HEIGHT").getValue());
        if (centerPos.y >= board_y + board_height) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfBottomBoundary() {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        if (centerPos.y <= board_y) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfLeftBoundary() {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        if (centerPos.x <= board_x) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfRightBoundary() {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        int board_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_WIDTH").getValue());
        if (centerPos.x >= board_x + board_width) {
            return true;
        } else {
            return false;
        }
    }
}
