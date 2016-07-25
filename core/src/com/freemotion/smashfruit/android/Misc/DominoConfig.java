package com.freemotion.smashfruit.android.Misc;

/**
 * Created by liaoclark on 7/24/16.
 */
public class DominoConfig extends JsonConfig {

    private String shape;
    private int degree;
    private int index;
    private TileConfig tile;

    public DominoConfig() {
        super();
    }

    public DominoConfig(DominoConfig config) {
        shape = config.getShape();
        degree = config.getDegree();
        tile = new TileConfig(config.getTile());
    }

    public TileConfig getTile() {
        return tile;
    }

    public String getShape() {
        return shape;
    }

    public int getDegree() {
        return degree;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
