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

    private Vector2 p1_pos_delta;
    private Vector2 p2_pos_delta;
    private Vector2 p3_pos_delta;
    private Vector2 p4_pos_delta;

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
        boolean retVal = false;
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
            retVal = pickupPositionAndDirection(centerPosSet, normalDirSet, objects);
            while (!retVal) {
                retVal = rotateObject(centerPosSet, normalDirSet, objects);
            }
        }
    }

    private boolean pickupPositionAndDirection(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {
        boolean retVal = false;
        int whichObject = pickWhichObjectToPlace(positionSet, directionSet);

        if (!isOverlap(positionSet.get(whichObject), directionSet.get(whichObject), objects)) {
            if (!isOutOfBoundary(positionSet.get(whichObject), directionSet.get(whichObject))) {
                centerPos = positionSet.get(whichObject);
                direction = directionSet.get(whichObject);
                return true;
            }
        }
        return retVal;
    }

    private int pickWhichObjectToPlace(Array<Vector2> positionSet, Array<Integer> directionSet) {
        return MathUtils.random(0, positionSet.size - 1);
    }

    private boolean rotateObject(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {
        DominoObject tailObject = objects.get(objects.size() - 1);
        return true;
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

    private void updateRectangleVertexPositionDelta(int direction) {
        int cuboidWidth = 44;
        int cuboidLength = 90;
        p1_pos_delta.set(cuboidLength * MathUtils.cosDeg(direction) + cuboidWidth * 0.5f * MathUtils.cosDeg(direction - 90),
                         cuboidLength * MathUtils.sinDeg(direction) + cuboidWidth * 0.5f * MathUtils.sinDeg(direction - 90));
        p2_pos_delta.set(cuboidWidth * 0.5f * MathUtils.cosDeg(direction - 90),
                         cuboidWidth * 0.5f * MathUtils.sinDeg(direction - 90));
        p3_pos_delta.set(cuboidWidth * 0.5f * MathUtils.cosDeg(direction + 90),
                         cuboidWidth * 0.5f * MathUtils.sinDeg(direction + 90));
        p4_pos_delta.set(cuboidLength * MathUtils.cosDeg(direction) + cuboidWidth * 0.5f * MathUtils.cosDeg(direction + 90),
                         cuboidLength * MathUtils.sinDeg(direction) + cuboidWidth * 0.5f * MathUtils.sinDeg(direction + 90));
    }

    private boolean isOverlap(Vector2 position, int direction, DominoContainer objects) {
        updateRectangleVertexPositionDelta(direction);
        Vector2 rectangle1_p1 = new Vector2(position).add(p1_pos_delta);
        Vector2 rectangle1_p2 = new Vector2(position).add(p2_pos_delta);
        Vector2 rectangle1_p3 = new Vector2(position).add(p3_pos_delta);
        Vector2 rectangle1_p4 = new Vector2(position).add(p4_pos_delta);
        for (int i = 0; i < (objects.size() - 1); i++) {
            DominoObject obj = objects.get(i);
            updateRectangleVertexPositionDelta(obj.getDirection());
            Vector2 rectangle2_p1 = new Vector2(obj.getCenterPos()).add(p1_pos_delta);
            Vector2 rectangle2_p2 = new Vector2(obj.getCenterPos()).add(p2_pos_delta);
            Vector2 rectangle2_p3 = new Vector2(obj.getCenterPos()).add(p3_pos_delta);
            Vector2 rectangle2_p4 = new Vector2(obj.getCenterPos()).add(p4_pos_delta);
            boolean retVal = isPointInRectangle(rectangle1_p1, rectangle1_p2, rectangle1_p3, rectangle1_p4, rectangle2_p1) &&
                             isPointInRectangle(rectangle1_p1, rectangle1_p2, rectangle1_p3, rectangle1_p4, rectangle2_p2) &&
                             isPointInRectangle(rectangle1_p1, rectangle1_p2, rectangle1_p3, rectangle1_p4, rectangle2_p3) &&
                             isPointInRectangle(rectangle1_p1, rectangle1_p2, rectangle1_p3, rectangle1_p4, rectangle2_p4);
            if (retVal) {
                return retVal;
            }
        }
        return false;
    }

    private boolean isPointInRectangle(Vector2 rectangle1_p1, Vector2 rectangle1_p2, Vector2 rectangle1_p3, Vector2 rectangle1_p4, Vector2 rectangle2_p1) {
        Vector2 p1_p4 = new Vector2(rectangle1_p1).sub(rectangle1_p4);
        Vector2 p3_p4 = new Vector2(rectangle1_p3).sub(rectangle1_p4);
        Vector2 two_p_c = rectangle2_p1.scl(2.0f).sub(rectangle1_p1).sub(rectangle1_p3);    // TWO_P_C=2P-C, C=Center of rectangle

        float part1 = new Vector2(p3_p4).dot(new Vector2(two_p_c).sub(p3_p4));
        float part2 = new Vector2(p3_p4).dot(new Vector2(two_p_c).add(p3_p4));
        float part3 = new Vector2(p1_p4).dot(new Vector2(two_p_c).sub(p1_p4));
        float part4 = new Vector2(p1_p4).dot(new Vector2(two_p_c).add(p1_p4));
        return part1 <= 0 && part2 >= 0 && part3 <= 0 && part4 >= 0;
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
