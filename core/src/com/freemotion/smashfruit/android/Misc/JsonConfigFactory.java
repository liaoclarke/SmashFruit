package com.freemotion.smashfruit.android.Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * Created by liaoclark on 2016/2/28.
 */
public class JsonConfigFactory {

    private String LOG_TAG = "JsonConfigFactory";
    private static JsonConfigFactory instance;
    private Array<JsonConfigArray> jsonConfigMap;

    public JsonConfigFactory() {
        jsonConfigMap = new Array<JsonConfigArray>();
    }

    public static JsonConfigFactory getInstance() {
        if (instance == null) {
            instance = new JsonConfigFactory();
        }
        return instance;
    }

    public void createAnimationConfigs(String filePath) {
        createJsonConfigs(filePath, AnimationConfig.class);
    }

    public void createKeyConfigs(String filePath) {
        createJsonConfigs(filePath, KeyConfig.class);
    }

    public void createSceneConfigs(String filePath) {
        createJsonConfigs(filePath, SceneConfig.class);
    }

    public void createJsonConfigs(String configFilePath, Class confiClass) {
        FileHandle file = Gdx.files.internal(configFilePath);
        String text = file.readString();
		Json json = new Json();
        json.setElementType(JsonConfigArray.class, "data", confiClass);
		JsonConfigArray array = json.fromJson(JsonConfigArray.class, text);
        jsonConfigMap.add(array);
    }

    public AnimationConfig getAnimationConfig(String key) {
        return (AnimationConfig) getJsonConfig(AnimationConfig.class.getSimpleName(), key);
    }

    public KeyConfig getKeyConfig(String key) {
        return (KeyConfig) getJsonConfig(KeyConfig.class.getSimpleName(), key);
    }

    public SceneConfig getSceneConfig(String key) {
        return (SceneConfig) getJsonConfig(SceneConfig.class.getSimpleName(), key);
    }

    public JsonConfig getJsonConfig(String type, String key) {
         for (JsonConfigArray array : jsonConfigMap) {
            if (type.equals(array.type)) {
                for (int i = 0; i < array.data.size(); i++) {
                    if (key.equals(((JsonConfig)array.data.get(i)).key)) {
                        return (JsonConfig) array.data.get(i);
                    }
                }
            }
        }
        return null;
    }

    public void dumpKeyJsonConfigs() {
        dumpJsonConfigs(KeyConfig.class.getSimpleName());
    }

    public void dumpAnimationJsonConfigs() {
        dumpJsonConfigs(AnimationConfig.class.getSimpleName());
    }

    public void dumpSceneJsonConfigs() {
        dumpJsonConfigs(SceneConfig.class.getSimpleName());
    }

    public void dumpJsonConfigs(String jsonConfigClass) {
        for (JsonConfigArray array : jsonConfigMap) {
            for (int i = 0; i < array.data.size(); i++) {
                try {
                    if (KeyConfig.class.getSimpleName().equals(jsonConfigClass)) {
                        KeyConfig kc = (KeyConfig) array.data.get(i);
                        Gdx.app.log(LOG_TAG, "config : " + kc.key + " " + kc.getValue());
                    } else if (AnimationConfig.class.getSimpleName().equals(jsonConfigClass)) {
                        AnimationConfig ac = (AnimationConfig) array.data.get(i);
                        Gdx.app.log(LOG_TAG, "config : " + ac.getKey() + " " + ac.getAtlas()+ " " + ac.getRegion() + " " + ac.getDuration() + " " + ac.getMode());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Can not create JsonConfig for" + array.type);
                }
            }
        }
    }

}
