package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.utils.Array;


/**
 * Created by liaoclark on 1/22/16.
 */
public class ResourceManager {

    private String LOG_TAG = "ResourceManager";
    private static ResourceManager instance;
    private AssetManager manager;
    private Array<ResourceLoader> loaders;

    public ResourceManager() {
        loaders = new Array<ResourceLoader>();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public ResourceManager init() {
        resume();
        assert instance == null;
        return instance;
    }

    public void resume() {
        if (manager == null) {
            manager = new AssetManager();
            for (ResourceLoader loader : loaders) {
                Gdx.app.log(LOG_TAG, "resume: " + loader.getLoaderName());
                loader.load();
                loader.finishLoading();
            }
        }
    }

    public void pause() {
        dispose();
    }

    public void dispose() {
        if (manager != null) {
            for (ResourceLoader loader : loaders) {
                Gdx.app.log(LOG_TAG, "dispose: " + loader.getLoaderName());
                loader.dispose();
            }
            manager.dispose();
            manager = null;
        }
    }

    public void addLoader(ResourceLoader loader) {
        loaders.add(loader);
        loader.setManager(this);
    }

    public ResourceLoader findLoader(String textureName) {
        for (ResourceLoader loader : loaders) {
            if (loader.getTextureAtlas() != null && loader.getLoaderName().equals(textureName)) {
                return loader;
            }
        }
        return null;
    }

    public AssetManager getAssetManager() {
        return manager;
    }
}
