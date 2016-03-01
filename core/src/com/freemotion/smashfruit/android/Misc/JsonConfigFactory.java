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
        FileHandle file = Gdx.files.internal(filePath);
        String text = file.readString();
		Json json = new Json();
        json.setElementType(JsonConfigArray.class, "data", AnimationConfig.class);
		JsonConfigArray array = json.fromJson(JsonConfigArray.class, text);
        jsonConfigMap.add(array);
    }

    public AnimationConfig getAnimationConfig(String key) {
        for (JsonConfigArray array : jsonConfigMap) {
            System.out.println("class type: " + AnimationConfig.class.getSimpleName());
            if (AnimationConfig.class.getSimpleName().equals(array.type)) {
                for (int i = 0; i < array.data.size(); i++) {
                    if (key.equals(((AnimationConfig)array.data.get(i)).key)) {
                        return (AnimationConfig) array.data.get(i);
                    }
                }
            }
        }
        return null;
    }

    public void dumpAnimationJsonConfigs() {
        for (JsonConfigArray array : jsonConfigMap) {
            Gdx.app.log(LOG_TAG, "type: " + array.type);
            for (int i = 0; i < array.data.size(); i++) {
                try {
                    AnimationConfig ac = (AnimationConfig) array.data.get(i);
                    Gdx.app.log(LOG_TAG, "config : " + ac.key + " " + ac.atlas + " " + ac.region + " " + ac.duration + " " + ac.mode);
                } catch (Exception e) {
                    throw new RuntimeException("Can not create JsonConfig for" + array.type);
                }
            }
        }
    }

    public void createKeyConfigs(String filePath) {
        FileHandle file = Gdx.files.internal(filePath);
        String text = file.readString();
		Json json = new Json();
        json.setElementType(JsonConfigArray.class, "data", KeyConfig.class);
		JsonConfigArray array = json.fromJson(JsonConfigArray.class, text);
        jsonConfigMap.add(array);
    }

    public KeyConfig getKeyConfig(String key) {
        for (JsonConfigArray array : jsonConfigMap) {
            if (KeyConfig.class.getName().equals(array.type)) {
                for (int i = 0; i < array.data.size(); i++) {
                    if (key.equals(((AnimationConfig)array.data.get(i)).key)) {
                        return (KeyConfig) array.data.get(i);
                    }
                }
            }
        }
        return null;
    }

    public void dumpKeyJsonConfigs() {
        for (JsonConfigArray array : jsonConfigMap) {
            Gdx.app.log(LOG_TAG, "type: " + array.type);
            for (int i = 0; i < array.data.size(); i++) {
                try {
                    KeyConfig kc = (KeyConfig) array.data.get(i);
                    Gdx.app.log(LOG_TAG, "config : " + kc.key + " " + kc.value);
                } catch (Exception e) {
                    throw new RuntimeException("Can not create JsonConfig for" + array.type);
                }
            }
        }
    }

}
