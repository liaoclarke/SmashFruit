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

    public static int generateDirection(Vector2 cuboidPos, int nextDirection, DominoObject nextObject, DominoContainer objects) {
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
        boolean isSection1 = isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && isOutOfTopBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection2 = isOutOfTopBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfRightBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection3 = isOutOfTopBoundary(cuboidPos.x, cuboidPos.y)
                          && isOutOfRightBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection4 = isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfTopBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection5 = !isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfTopBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfRightBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection6 = isOutOfRightBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfTopBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection7 = isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection8 = isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfLeftBoundary(cuboidPos.x, cuboidPos.y)
                          && !isOutOfRightBoundary(cuboidPos.x, cuboidPos.y);

        boolean isSection9 = isOutOfBottomBoundary(cuboidPos.x, cuboidPos.y)
                          && isOutOfRightBoundary(cuboidPos.x, cuboidPos.y);

        int direction = 0;
        Gdx.app.error(LOG_TAG, " " + isSection1 + " | " + isSection2 + " | " + isSection3);
        Gdx.app.error(LOG_TAG, " " + isSection4 + " | " + isSection5 + " | " + isSection6);
        Gdx.app.error(LOG_TAG, " " + isSection7 + " | " + isSection8 + " | " + isSection9);
        if (isSection1) {
            if (nextDirection == 0) {
                direction = 300;
            } else if (direction == 60) {
                direction = 0;
            } else if (direction == 120) {
                direction = MathUtils.randomBoolean() ? 180 : 60;
            } else if (direction == 180) {
                direction = 240;
            } else if (direction == 240) {
                direction = 300;
            } else if (direction == 300) {
                direction = 0;
            }
        }
        else if (isSection2) {
            if (direction == 0) {
                direction = 300;
            } else if (direction == 60) {
                direction = 0;
            } else if (direction == 120){
                direction = 180;
            } else if (direction == 180) {
                direction = 240;
            } else if (direction == 240) {
                direction = MathUtils.randomBoolean() ? 240 : 300;
            } else if (direction == 300) {
                direction = MathUtils.randomBoolean() ? 240 : 300;
            }
        }
        else if (isSection3) {
            if (direction == 0) {
                direction = 300;
            } else if (direction == 60) {
                direction = MathUtils.randomBoolean() ? 0 : 120;
            } else if (direction == 120){
                direction = 180;
            } else if (direction == 180) {
                direction = 240;
            } else if (direction == 240) {
                direction = 240;
            } else if (direction == 300) {
                direction = 240;
            }
        }
        else if (isSection4) {
            if (direction == 0) {
                direction = 0;
            } else if (direction == 60) {
                direction = 0;
            } else if (direction == 120) {
                direction = 60;
            } else if (direction == 180) {
                direction = MathUtils.randomBoolean() ? 120 : 240;
            } else if (direction == 240) {
                direction = 300;
            } else if (direction == 300) {
                direction = 0;
            }
        }
        else if (isSection5) {
            if (nextObject.getDominoType() == DOMINO_TYPE.Tomato) {
                direction = nextDirection;
            } else {
                Gdx.app.error(LOG_TAG, "direction(0) : " + direction);
                if (nextDirection == 0 || nextDirection == 180) {
                    direction = nextDirection + 60 * MathUtils.random(-1, 1);
                } else if (nextDirection == 120 || nextDirection == 300) {
                    direction = nextDirection + 60 * (MathUtils.randomBoolean() ? 0 : 1);
                } else if (nextDirection == 60 || nextDirection == 240) {
                    direction = nextDirection + 60 * (MathUtils.randomBoolean() ? -1 : 0);
                }
            }
            /*if (hasClosure(cuboidPos, direction, nextObject, objects)) {
                direction = nextDirection - 60;
                Gdx.app.error(LOG_TAG, "direction(-60) : " + direction);
                if (hasClosure(cuboidPos, direction, nextObject, objects)) {
                    direction = nextDirection + 60;
                    Gdx.app.error(LOG_TAG, "direction(+60) : " + direction);
                }
            }*/
            if (direction == -60) {
                direction = 300;
            } else if (direction == 360) {
                direction = 0;
            }
        }
        else if (isSection6) {
            if (direction == 0) {
                direction = MathUtils.randomBoolean() ? 60 : 300;
            } else if (direction == 60) {
                direction = 120;
            } else if (direction == 120){
                direction = 180;
            } else if (direction == 180) {
                direction = 180;
            } else if (direction == 240) {
                direction = 180;
            } else if (direction == 300) {
                direction = 240;
            }
        }
        else if (isSection7) {
            if (direction == 0) {
                direction = 60;
            } else if (direction == 60) {
                direction = 60;
            } else if (direction == 120) {
                direction = 60;
            } else if (direction == 180) {
                direction = 120;
            } else if (direction == 240) {
                direction = MathUtils.randomBoolean() ? 180 : 300;
            } else if (direction == 300) {
                direction = 0;
            }
        }
        else if (isSection8) {
            if (direction == 0) {
                direction = 60;
            } else if (direction == 60) {
                direction = MathUtils.randomBoolean() ? 60 : 120;
            } else if (direction == 120) {
                direction = MathUtils.randomBoolean() ? 60 : 120;
            } else if (direction == 180) {
                direction = 120;
            } else if (direction == 240) {
                direction = 180;
            } else if (direction == 300) {
                direction = 0;
            }
        }
        else if (isSection9) {
            if (direction == 0) {
                direction = 60;
            } else if (direction == 60) {
                direction = 120;
            } else if (direction == 120) {
                direction = 120;
            } else if (direction == 180) {
                direction = 120;
            } else if (direction == 240) {
                direction = 180;
            } else if (direction == 300) {
                direction = MathUtils.randomBoolean() ? 240 : 0;
            }
        }

        /*if (direction < 180) {
            direction = 180 - direction;
        } else {
            direction = direction - 180;
        }*/
        return direction;
    }

    public static int generateDirection(DominoObject firstObject, DominoContainer objects) {
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
        boolean isSection1 = isOutOfLeftBoundary(firstObject.getCenterPos().x, firstObject.getCenterPos().y)
                          && isOutOfTopBoundary(firstObject.getCenterPos().x, firstObject.getCenterPos().y);

        boolean isSection2 = isOutOfTopBoundary(firstObject.getCenterPos().x, firstObject.getCenterPos().y)
                          && !isOutOfLeftBoundary(firstObject.getCenterPos().x, firstObject.getCenterPos().y)
                          && !isOutOfRightBoundary(firstObject.getCenterPos().x, firstObject.getCenterPos().y);

        boolean isSection3 = isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection4 = isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection5 = !isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection6 = isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection7 = isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection8 = isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection9 = isOutOfBottomBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        int direction = 0;
        Gdx.app.error(LOG_TAG, " " + isSection1 + " | " + isSection2 + " | " + isSection3);
        Gdx.app.error(LOG_TAG, " " + isSection4 + " | " + isSection5 + " | " + isSection6);
        Gdx.app.error(LOG_TAG, " " + isSection7 + " | " + isSection8 + " | " + isSection9);
        if (isSection1) {
            if (firstObject.direction == 0) {
                direction = 300;
            } else if (firstObject.direction == 60) {
                direction = 0;
            } else if (firstObject.direction == 120) {
                direction = MathUtils.randomBoolean() ? 180 : 60;
            } else if (firstObject.direction == 180) {
                direction = 240;
            } else if (firstObject.direction == 240) {
                direction = 300;
            } else if (firstObject.direction == 300) {
                direction = 0;
            }
        }
        else if (isSection2) {
            if (firstObject.direction == 0) {
                direction = 300;
            } else if (firstObject.direction == 60) {
                direction = 0;
            } else if (firstObject.direction == 120){
                direction = 180;
            } else if (firstObject.direction == 180) {
                direction = 240;
            } else if (firstObject.direction == 240) {
                direction = MathUtils.randomBoolean() ? 240 : 300;
            } else if (firstObject.direction == 300) {
                direction = MathUtils.randomBoolean() ? 240 : 300;
            }
        }
        else if (isSection3) {
            if (firstObject.direction == 0) {
                direction = 300;
            } else if (firstObject.direction == 60) {
                direction = MathUtils.randomBoolean() ? 0 : 120;
            } else if (firstObject.direction == 120){
                direction = 180;
            } else if (firstObject.direction == 180) {
                direction = 240;
            } else if (firstObject.direction == 240) {
                direction = 240;
            } else if (firstObject.direction == 300) {
                direction = 240;
            }
        }
        else if (isSection4) {
            if (firstObject.direction == 0) {
                direction = 0;
            } else if (firstObject.direction == 60) {
                direction = 0;
            } else if (firstObject.direction == 120) {
                direction = 60;
            } else if (firstObject.direction == 180) {
                direction = MathUtils.randomBoolean() ? 120 : 240;
            } else if (firstObject.direction == 240) {
                direction = 300;
            } else if (firstObject.direction == 300) {
                direction = 0;
            }
        }
        else if (isSection5) {
            direction = firstObject.direction + 60 * MathUtils.random(-1, 1);
            if (direction == -60) {
                direction = 300;
            } else if (direction == 360) {
                direction = 0;
            }
        }
        else if (isSection6) {
            if (firstObject.direction == 0) {
                direction = MathUtils.randomBoolean() ? 60 : 300;
            } else if (firstObject.direction == 60) {
                direction = 120;
            } else if (firstObject.direction == 120){
                direction = 180;
            } else if (firstObject.direction == 180) {
                direction = 180;
            } else if (firstObject.direction == 240) {
                direction = 180;
            } else if (firstObject.direction == 300) {
                direction = 240;
            }
        }
        else if (isSection7) {
            if (firstObject.direction == 0) {
                direction = 60;
            } else if (firstObject.direction == 60) {
                direction = 60;
            } else if (firstObject.direction == 120) {
                direction = 60;
            } else if (firstObject.direction == 180) {
                direction = 120;
            } else if (firstObject.direction == 240) {
                direction = MathUtils.randomBoolean() ? 180 : 300;
            } else if (firstObject.direction == 300) {
                direction = 0;
            }
        }
        else if (isSection8) {
            if (firstObject.direction == 0) {
                direction = 60;
            } else if (firstObject.direction == 60) {
                direction = MathUtils.randomBoolean() ? 60 : 120;
            } else if (firstObject.direction == 120) {
                direction = MathUtils.randomBoolean() ? 60 : 120;
            } else if (firstObject.direction == 180) {
                direction = 120;
            } else if (firstObject.direction == 240) {
                direction = 180;
            } else if (firstObject.direction == 300) {
                direction = 0;
            }
        }
        else if (isSection9) {
            if (firstObject.direction == 0) {
                direction = 60;
            } else if (firstObject.direction == 60) {
                direction = 120;
            } else if (firstObject.direction == 120) {
                direction = 120;
            } else if (firstObject.direction == 180) {
                direction = 120;
            } else if (firstObject.direction == 240) {
                direction = 180;
            } else if (firstObject.direction == 300) {
                direction = MathUtils.randomBoolean() ? 240 : 0;
            }
        }

        if (direction < 180) {
            direction = 180 - direction;
        } else {
            direction = direction - 180;
        }
        return direction;
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
        /*centerPos = generatePosition((int)object.getCenterPos().x,
                                     (int)object.getCenterPos().y,
                                     object.getDirection() < 180 ? (object.getDirection() + 180) : (object.getDirection() - 180));
        direction = generateDirection(centerPos,  object.getDirection(), object, objects);
        */
            Array<Vector2> centerPosSet = new Array<Vector2>();
            Array<Integer> directionSet = new Array<Integer>();
            int normalDir = object.getDirection() < 180 ? (object.getDirection() + 180) : (object.getDirection() - 180);
            for (int deltaDir = -60; deltaDir < 120; deltaDir += 60) {
                centerPosSet.add(generatePosition((int) object.getCenterPos().x, (int) object.getCenterPos().y, normalDir));
                if ((object.getDirection() - deltaDir) == -60) {
                    directionSet.add(300);
                } else if ((object.getDirection() - deltaDir) == 360) {
                    directionSet.add(0);
                } else {
                    directionSet.add(object.getDirection() - deltaDir);
                }
            }
            centerPosSet.add(generatePosition((int) object.getCenterPos().x, (int) object.getCenterPos().y, normalDir - 60));
            if ((object.getDirection() + 60) == 360) {
                directionSet.add(0);
            } else {
                directionSet.add(object.getDirection() + 60);
            }
            centerPosSet.add(generatePosition((int) object.getCenterPos().x, (int) object.getCenterPos().y, normalDir + 60));
            if ((object.getDirection() - 60) == -60) {
                directionSet.add(300);
            } else {
                directionSet.add(object.getDirection() - 60);
            }
            pickupPositionAndDirection(centerPosSet, directionSet, objects);
        }
        dominoType = type;
    }

    private void pickupPositionAndDirection(Array<Vector2> positionSet, Array<Integer> directionSet, DominoContainer objects) {
        Array<Vector2> goodPositionSet = new Array<Vector2>();
        Array<Integer> goodDirectionSet = new Array<Integer>();
        Array<Vector2> betterPositionSet = new Array<Vector2>();
        Array<Integer> betterDirectionSet = new Array<Integer>();

        /* Filter out the position and direction that is out of scene boundary*/
        for (int i = 0; i < positionSet.size; i++) {
            if (!isOutOfBoundary(positionSet.get(i), directionSet.get(i))) {
                goodPositionSet.add(positionSet.get(i));
                goodDirectionSet.add(directionSet.get(i));
            }
        }
        /* Filter out the position and direction that might overlap with exists cuboid */
        for (int i = 0; i < goodPositionSet.size; i++) {
            if (!isOverlap(positionSet.get(i), directionSet.get(i), objects)) {
                betterPositionSet.add(positionSet.get(i));
                betterDirectionSet.add(directionSet.get(i));
            }
        }

        if (betterPositionSet.size > 0) {
            int index = MathUtils.random(0, betterPositionSet.size - 1);
            centerPos = betterPositionSet.get(index);
            direction = betterDirectionSet.get(index);
        } else {
            int index = MathUtils.random(0, goodPositionSet.size - 1);
            centerPos = goodPositionSet.get(index);
            direction = goodDirectionSet.get(index);
        }
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
        for (int i = 1; i < (objects.size() - 1); i++) {
            DominoObject obj = objects.get(i);
            if (position.dst(obj.getCenterPos()) < 5) {
                return true;
            } else {
                float degree = MathUtils.atan2(obj.getCenterPos().y - position.y, obj.getCenterPos().x - position.x) * MathUtils.radiansToDegrees;
                if ((position.dst(obj.getCenterPos()) < 75)
                     && (Math.abs(degree - direction) <= 5)) {
                    Gdx.app.error(LOG_TAG, "centerPos: " + position + " direction: " + direction + " colliside pos: " + obj.getCenterPos() + " direction: " + obj.getDirection());
                    return true;
                }
            }
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

    private static boolean hasClosure(Vector2 centerPos, int direction, DominoObject nextObject, DominoContainer objects) {
        for (DominoObject obj : objects.getAll()) {
            float degree = MathUtils.atan2(obj.getCenterPos().y - centerPos.y, obj.getCenterPos().x - centerPos.x) * MathUtils.radiansToDegrees;
            if ((centerPos.dst(obj.getCenterPos()) < 75)
                 && (Math.abs(degree - direction) <= 5)
                 && (obj != nextObject)) {
                Gdx.app.error(LOG_TAG, "centerPos: " + centerPos + " direction: " + direction + " colliside pos: " + obj.getCenterPos() + " direction: " + obj.getDirection());
                break;
            }
        }
        return false;
    }

}
