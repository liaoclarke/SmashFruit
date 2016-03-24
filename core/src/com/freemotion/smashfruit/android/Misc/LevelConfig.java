package com.freemotion.smashfruit.android.Misc;

/**
 * Created by liaoclark on 3/22/2016.
 */
public class LevelConfig extends JsonConfig {

    private int star;
    private boolean pass;
    private int level;

    public int getStar() {
        return star;
    }

    public LevelConfig setStar(int value) {
        star = value;
        return this;
    }

    public boolean getPass() {
        return pass;
    }

    public LevelConfig setPass(boolean value) {
        pass = value;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public LevelConfig setLevel(int value) {
        level = value;
        return this;
    }
}
