package com.freemotion.smashfruit.android.Misc;

/**
 * Created by clarkliao on 2016/7/25.
 */
public class TileConfig extends JsonConfig {

    private int touch_centerX;
    private int touch_centerY;
    private int touch_width;
    private int touch_height;
    private int tile_positionX;
    private int tile_positionY;
    private int tile_width;
    private int tile_height;

    public TileConfig() {
        super();
    }

    public TileConfig(TileConfig config) {
        touch_centerX = config.getTouchableRegionCenterX();
        touch_centerY = config.getTouchabelRegionCenterY();
        touch_width = config.getTouchableRegionWidth();
        touch_height = config.getTouchableRegionHeight();
        tile_positionX = config.getTilePositionX();
        tile_positionY = config.getTilePositionY();
        tile_width = config.getTileWidth();
        tile_height = config.getTiledHeigh();
    }

    public int getTouchableRegionCenterX() {
        return touch_centerX;
    }

    public int getTouchabelRegionCenterY() {
        return touch_centerY;
    }

    public int getTouchableRegionWidth() {
        return touch_width;
    }

    public int getTouchableRegionHeight() {
        return touch_height;
    }

    public int getTilePositionX() {
        return tile_positionX;
    }

    public int getTilePositionY() {
        return tile_positionY;
    }

    public int getTileWidth() {
        return tile_width;
    }

    public int getTiledHeigh() {
        return tile_height;
    }

    public void setTouchableRegionCenterX(int x) {
        touch_centerX = x;
    }

    public void setTouchableRegionCenterY(int y) {
        touch_centerY = y;
    }

    public void setTouchableRegionWidth(int width) {
        touch_width = width;
    }

    public void setTouchableRegionHeight(int height) {
        touch_height = height;
    }

    public void setTilePositionX(int x) {
        tile_positionX = x;
    }

    public void setTilePositionY(int y) {
        tile_positionY = y;
    }

    public void setTileWidth(int width) {
        tile_width = width;
    }

    public void setTileHeight(int height) {
        tile_height = height;
    }
}
