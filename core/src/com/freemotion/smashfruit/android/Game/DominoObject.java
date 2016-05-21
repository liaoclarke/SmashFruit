package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    public DominoObject(int centerX, int centerY, int dir, DOMINO_TYPE type) {
        centerPos = new Vector2(centerX, centerY);
        dominoType = type;
        direction = dir;
    }

    public DominoObject(DominoObject firstObject, DOMINO_TYPE type) {
        dominoType = type;
        centerPos = new Vector2(0, 0);
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
        boolean isSection1 = isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

        boolean isSection2 = isOutOfTopBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfLeftBoundary(firstObject.centerPos.x, firstObject.centerPos.y)
                          && !isOutOfRightBoundary(firstObject.centerPos.x, firstObject.centerPos.y);

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

        int collisionRegionWidth = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_WIDTH").getValue());
        int collisionRegionNearestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_NEAREST").getValue());
        int collisionRegionFurthestDistance = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("COLLISION_FURTHEST").getValue());

        int tp = MathUtils.randomSign() * MathUtils.random(0, collisionRegionWidth);
        int np = MathUtils.random(collisionRegionNearestDistance, collisionRegionFurthestDistance);
        float rad = MathUtils.degreesToRadians * firstObject.getDirection();
        float parallel_delta_x = tp * MathUtils.sin(rad);
        float parallel_delta_y = tp * MathUtils.cos(rad);
        float normal_delta_x = np * MathUtils.cos(rad);
        float normal_delta_y = np * MathUtils.sin(rad);
        float pos_x = firstObject.getCenterPos().x;
        float pos_y = firstObject.getCenterPos().y;
        centerPos.x = (int) (pos_x + parallel_delta_x + normal_delta_x);
        centerPos.y = (int) (pos_y + parallel_delta_y + normal_delta_y);

        /*while (false &&
               isOutOfLeftBoundary(centerPos.x, centerPos.y) ||
               isOutOfTopBoundary(centerPos.x, centerPos.y) ||
               isOutOfRightBoundary(centerPos.x, centerPos.y) ||
               isOutOfBottomBoundary(centerPos.x, centerPos.y)) {
            centerPos.x = pos_x;
            centerPos.y = pos_y;
            tp = MathUtils.randomSign() * MathUtils.random(0, collisionRegionWidth);
            np = MathUtils.random(collisionRegionNearestDistance, collisionRegionFurthestDistance);
            rad = MathUtils.degreesToRadians * firstObject.getDirection();
            parallel_delta_x = tp * MathUtils.sin(rad);
            parallel_delta_y = tp * MathUtils.cos(rad);
            normal_delta_x = np * MathUtils.cos(rad);
            normal_delta_y = np * MathUtils.sin(rad);
            centerPos.x = (int) (centerPos.x + parallel_delta_x + normal_delta_x);
            centerPos.y = (int) (centerPos.y + parallel_delta_y + normal_delta_y);
            Gdx.app.error(LOG_TAG, "center position x: " + centerPos.x + " y: " + centerPos.y + " direction: " + direction +
                    " out left : " + isOutOfLeftBoundary(centerPos.x, centerPos.y) + " out right: " + isOutOfRightBoundary(centerPos.x, centerPos.y) + " out top: " + isOutOfTopBoundary(centerPos.x, centerPos.y) + " out bottom: " + isOutOfBottomBoundary(centerPos.x, centerPos.y));
        }*/
        //Gdx.app.error(LOG_TAG, "direction : " + direction);
        /*if (isOutOfLeftBoundary()) {
            Gdx.app.error(LOG_TAG, "is out of left boundary...");
            if (firstObject.getDirection() == 120) {
                direction = firstObject.getDirection() - 60;
            } else if (firstObject.getDirection() == 240) {
                direction = firstObject.getDirection() + 60;
            }
        } else if (isOutOfRightBoundary()) {
            Gdx.app.error(LOG_TAG, "is out of right boundary...");
            if (firstObject.getDirection() == 60) {
                direction = firstObject.getDirection() + 60;
            } else if (firstObject.getDirection() == 300) {
                direction = firstObject.getDirection() - 60;
            }
        }

        if (isOutOfTopBoundary()) {
            Gdx.app.error(LOG_TAG, "is out of top boundary...");
            if (firstObject.getDirection() == 120) {
                direction = firstObject.getDirection() + 60;
            } else if (firstObject.getDirection() == 60) {
                direction = firstObject.getDirection() - 60;
            }
        } else if (isOutOfBottomBoundary()) {
            Gdx.app.error(LOG_TAG, "is out of bottom boundary...");
            if (firstObject.getDirection() == 240) {
                direction = firstObject.getDirection() - 60;
            } else if (firstObject.getDirection() == 300) {
                direction = firstObject.getDirection() + 60;
            }
        }*/
       //Gdx.app.error(LOG_TAG, " centerPos: " + centerPos + " direction: " + direction);
    }

    public Vector2 getCenterPos() {
        return centerPos;
    }

    public int getDirection() {
        if (dominoType != DOMINO_TYPE.Tomato) {
            return direction;
        } else {
            return 0;
        }
    }

    public DOMINO_TYPE getDominoType() {
        return dominoType;
    }

    private boolean isOutOfTopBoundary(float x, float y) {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        int board_height = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_HEIGHT").getValue());
        if (y >= board_y + board_height) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfBottomBoundary(float x, float y) {
        int board_y = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_Y").getValue());
        if (y <= board_y) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfLeftBoundary(float x, float y) {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        if (x <= board_x) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOutOfRightBoundary(float x, float y) {
        int board_x = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_X").getValue());
        int board_width = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("SCENE_WIDTH").getValue());
        if (x >= board_x + board_width) {
            return true;
        } else {
            return false;
        }
    }
}
