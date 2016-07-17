package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

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

    public static Vector2 generatePosition(int centerX, int centerY, int direction) {
        int collisionRegionWidth = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_WIDTH").getValue());
        int collisionRegionNearestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_NEAREST").getValue());
        int collisionRegionFurthestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_FURTHEST").getValue());

        int tp = MathUtils.randomSign() * MathUtils.random(0, collisionRegionWidth);
        int np = MathUtils.random(collisionRegionNearestDistance, collisionRegionFurthestDistance);
        float rad = MathUtils.degreesToRadians * direction;
        float parallel_delta_x = tp * MathUtils.sin(rad);
        float parallel_delta_y = tp * MathUtils.cos(rad);
        float normal_delta_x = np * MathUtils.cos(rad);
        float normal_delta_y = np * MathUtils.sin(rad);
        return new Vector2((int)(centerX + parallel_delta_x + normal_delta_x), (int)(centerY + parallel_delta_y + normal_delta_y));
    }

    public static DominoObject createObject(int centerX, int centerY, int dir, DOMINO_TYPE type) {
        return new DominoObject(centerX, centerY, dir, type);
    }

    public static DominoObject createObject(DominoObject object, DominoContainer objects, DOMINO_TYPE type, boolean sameDirection) {
        return new DominoObject(object, objects, type, sameDirection);
    }

    public DominoObject(int centerX, int centerY, int dir, DOMINO_TYPE type) {
        centerPos = new Vector2(centerX, centerY);
        dominoType = type;
        direction = dir;
    }

    public DominoObject(DominoObject object, DominoContainer objects, DOMINO_TYPE type, boolean sameDirection) {
        dominoType = type;
        if (sameDirection) {
            int normalDir = object.getDirection() < 180 ? (object.getDirection() + 180) : (object.getDirection() - 180);
            centerPos = generatePosition((int)object.getCenterPos().x, (int)object.getCenterPos().y, normalDir);
            direction = object.getDirection();
        } else {
            Array<Integer> centerDirSet = new Array<Integer>();
            Array<Integer> normalDirSet = new Array<Integer>();
            Array<Vector2> centerPosSet = new Array<Vector2>();

            int centerDir = object.getDirection() < 180 ? (object.getDirection() + 180) : (object.getDirection() - 180);
            //centerDirSet.add(centerDir);
            centerDirSet.add((centerDir - 60) < 0 ? 300 : (centerDir - 60));
            centerDirSet.add((centerDir + 60) >= 360 ? (centerDir - 300) : (centerDir + 60));
            for (int i = 0; i < centerDirSet.size; i++) {
                normalDirSet.add(centerDirSet.get(i) < 180 ? centerDirSet.get(i) + 180 : centerDirSet.get(i) - 180);
            }
            for (int i = 0; i < centerDirSet.size; i++) {
                centerPosSet.add(generatePosition((int) object.getCenterPos().x, (int) object.getCenterPos().y, centerDirSet.get(i)));
            }
            pickupPositionAndDirection(centerPosSet, normalDirSet, objects);
        }
    }

    private void pickupPositionAndDirection(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {
        int whichObject = pickWhichObjectToPlace(positionSet, directionSet);

        if (!isOverlap(positionSet.get(whichObject), directionSet.get(whichObject), objects)) {
            if (!isOutOfBoundary(positionSet.get(whichObject), directionSet.get(whichObject))) {
                centerPos = positionSet.get(whichObject);
                direction = directionSet.get(whichObject);
            } else {
                rotateObject(positionSet, directionSet, objects);
            }
        } else {
            rotateObject(positionSet, directionSet, objects);
        }
    }

    private int pickWhichObjectToPlace(Array<Vector2> positionSet, Array<Integer> directionSet) {
        return MathUtils.random(0, positionSet.size - 1);
    }

    private void rotateObject(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {

    }

    private boolean isOutOfBoundary(Vector2 position, int direction) {
             /*
        *   1 |       2       | 3
        *  -----------------------
        *     |               |
        *     |               |
        *   4 |       5       | 6
        *     |               |
        *     |               |
        *  -----------------------
        *   7 |       8       | 9
        * */
        boolean inSection5 = !isOutOfLeftBoundary(position.x, position.y)
                          && !isOutOfTopBoundary(position.x, position.y)
                          && !isOutOfRightBoundary(position.x, position.y)
                          && !isOutOfBottomBoundary(position.x, position.y);

        return !inSection5;
    }

    private boolean isOverlap(Vector2 position, int direction, DominoContainer objects) {
        const int cuboidWidth = ;
        const int cuboidLength = ;
        for (int i = 0; i < (objects.size() - 1); i++) {
            DominoObject obj = objects.get(i);
            float p1_x = position.x + cuboidLength * MathUtils.cosDeg(direction) + cuboidWidth * 0.5f * MathUtils.sinDeg(direction);
            float p1_y = position.y + cuboidWidth * 0.5f * MathUtils.cosDeg(direction) + cuboidLength * MathUtils.sinDeg(direction);
            float p2_x = position.x + cuboidWidth * 0.5f * MathUtils.sinDeg(direction);
            float p2_y = position.y + cuboidWidth * 0.5f * MathUtils.cosDeg(direction);
            /*float degree = MathUtils.atan2(obj.getCenterPos().y - position.y, obj.getCenterPos().x - position.x) * MathUtils.radiansToDegrees;
            float distance = position.dst(obj.getCenterPos());
            if ((position.dst(obj.getCenterPos()) < 75)
                 && (Math.abs(degree - direction) <= 5)) {
                Gdx.app.error(LOG_TAG, "centerPos: " + position + " direction: " + direction + " colliside pos: " + obj.getCenterPos() + " direction: " + obj.getDirection());
                return true;
            }*/
        }
        return false;
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

    private static boolean isOutOfTopBoundary(float x, float y) {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        int board_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_HEIGHT").getValue());
        if (y >= board_y + board_height) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOutOfBottomBoundary(float x, float y) {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        if (y <= board_y) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOutOfLeftBoundary(float x, float y) {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        if (x <= board_x) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOutOfRightBoundary(float x, float y) {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        int board_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_WIDTH").getValue());
        if (x >= board_x + board_width) {
            return true;
        } else {
            return false;
        }
    }

    private static int findBestPosition(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {
        int retval = 0;

        for (int i = 0; i < positionSet.size; i++) {
            boolean isSection1 = isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection2 = isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection3 = isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection4 = isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection5 = !isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection6 = isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfTopBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection7 = isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection8 = isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfLeftBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && !isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y);

            boolean isSection9 = isOutOfBottomBoundary(positionSet.get(i).x, positionSet.get(i).y)
                    && isOutOfRightBoundary(positionSet.get(i).x, positionSet.get(i).y);

            int direction = 0;
            Gdx.app.error(LOG_TAG, " " + isSection1 + " | " + isSection2 + " | " + isSection3);
            Gdx.app.error(LOG_TAG, " " + isSection4 + " | " + isSection5 + " | " + isSection6);
            Gdx.app.error(LOG_TAG, " " + isSection7 + " | " + isSection8 + " | " + isSection9);

            if (isSection1) {

            }
        }

        return retval;
    }
}
