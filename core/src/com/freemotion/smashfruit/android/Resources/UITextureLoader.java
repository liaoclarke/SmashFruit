package com.freemotion.smashfruit.android.Resources;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.freemotion.smashfruit.android.Utils.ResourceLoader;

/**
 * Created by liaoclark on 2016/3/12.
 */
public class UITextureLoader extends ResourceLoader {

    private final String dataFile = "graphic/ui.txt";
    private final String textureFile = "graphic/ui.png";
    private TextureAtlas textureAtlas;

    public UITextureLoader() {
        super();
        loaderName = this.getClass().getSimpleName();
    }

    @Override
    public void dispose() {
        textureAtlas = null;
        manager.getAssetManager().unload(dataFile);
    }

    @Override
    public void load() {
        textureAtlas = null;
        manager.getAssetManager().load(dataFile, TextureAtlas.class);
    }

    @Override
    public void finishLoading() {
        manager.getAssetManager().finishLoading();
        if (textureAtlas == null && manager.getAssetManager().isLoaded(dataFile)) {
            textureAtlas = manager.getAssetManager().get(dataFile, TextureAtlas.class);
        }
    }

    @Override
    public boolean update() {
        if (manager != null && manager.getAssetManager() != null) {
            boolean updated = manager.getAssetManager().update();
            if (updated) {
                textureAtlas = manager.getAssetManager().get(dataFile, TextureAtlas.class);
            }
            return updated;
        } else {
            return false;
        }
    }

    @Override
    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }
}
