package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by liaoclark on 1/22/16.
 */
public class ResourceLoader {

    protected String loaderName;
    public ResourceManager manager;

    public void setManager(ResourceManager manager) {
        loaderName = this.getClass().getSimpleName();
        this.manager = manager;
    }

    public TextureAtlas getTextureAtlas() {
        return null;
    }

    public void load() {}

    public void dispose() {}

    public boolean update() { return false; }

    public void finishLoading() {}

    public String getLoaderName() {
        return loaderName;
    }
}
