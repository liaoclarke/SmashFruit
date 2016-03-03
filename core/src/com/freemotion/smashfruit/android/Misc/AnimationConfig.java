package com.freemotion.smashfruit.android.Misc;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by liaoclark on 2016/2/28.
 */
public class AnimationConfig extends JsonConfig {

    private String atlas;
    private String region;
    private float duration;
    private Animation.PlayMode mode;

    public AnimationConfig() {
        atlas = null;
        region = null;
        duration = 0f;
        mode = Animation.PlayMode.NORMAL;
    }

    public String getAtlas() {
        return atlas;
    }

    public String getRegion() {
        return region;
    }

    public float getDuration() {
        return duration;
    }

    public Animation.PlayMode getMode() {
        return mode;
    }
}
