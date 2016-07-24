package com.freemotion.smashfruit.android.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.StageConfig;

import java.util.HashMap;

/**
 * Created by liaoclark on 2016/3/5.
 */
public class GameController {

    private String LOG_TAG;
    private TiledMap mapLoader;
    private static GameController instance;
    private int currentCourse, firstCourse, bestCourse;
    private String courseRootDirectory;
    private String courseDirectoryPrefix;
    private String courseFilePrefix;
    private String courseFilePathSeperator;
    private String courseFileNameSeperator;
    private String dominoLayerName;
    private String dominoTileSetName;
    private Array<StageConfig> dominoInstances;

    private GameController() {
        LOG_TAG = GameController.class.getSimpleName();
        JsonConfigFactory.getInstance().createCourseConfigs("config/GameCourseConfig");
        bestCourse = Integer.parseInt(JsonConfigFactory.getInstance().getCourseConfig("BestCourse").getValue());
        firstCourse = Integer.parseInt(JsonConfigFactory.getInstance().getCourseConfig("FirstCourse").getValue());
        courseRootDirectory = JsonConfigFactory.getInstance().getCourseConfig("CourseRootDirectory").getValue();
        courseDirectoryPrefix = JsonConfigFactory.getInstance().getCourseConfig("CourseDirectoryPrefix").getValue();
        courseFilePrefix = JsonConfigFactory.getInstance().getCourseConfig("CourseFilePrefix").getValue();
        courseFilePathSeperator = JsonConfigFactory.getInstance().getCourseConfig("CourseFilePathSeperator").getValue();
        courseFileNameSeperator = JsonConfigFactory.getInstance().getCourseConfig("CourseFileNameSeperator").getValue();
        dominoLayerName = JsonConfigFactory.getInstance().getCourseConfig("DominoLayerName").getValue();
        dominoTileSetName = JsonConfigFactory.getInstance().getCourseConfig("DominoTileSetName").getValue();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void enterGame() {
        currentCourse = firstCourse;
        String tmxPath = buildTMXPath(currentCourse);
        loadCourseTiledMap(tmxPath);
    }

    public String buildTMXPath(int whichCourse) {
        StringBuilder builder = new StringBuilder();
        int whichFile = 1;
        builder.append(courseRootDirectory).append(courseFilePathSeperator).
                append(courseDirectoryPrefix).append(courseFileNameSeperator).append(whichCourse).append(courseFilePathSeperator).
                append(courseFilePrefix).append(courseFileNameSeperator).append(whichFile).append(".tmx");
        return builder.toString();
    }

    public Array<StageConfig> getDominoObjects(){
        return dominoInstances;
    }

    private void loadCourseTiledMap(String tmxPath) {
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.textureMagFilter = Texture.TextureFilter.Nearest;
        parameters.textureMinFilter = Texture.TextureFilter.Nearest;
        mapLoader = new TmxMapLoader().load(tmxPath, parameters);

        MapLayers layers = mapLoader.getLayers();

        try {
            MapObjects tiledObjects = layers.get(dominoLayerName).getObjects();
            TiledMapTileSet tileset = mapLoader.getTileSets().getTileSet(dominoTileSetName);
            //int firstGid = (Integer)tileset.getProperties().get("firstgid");
            for (MapObject tiledObj : tiledObjects) {
                //DominoInstance dominoObject = new DominoInstance();
                MapProperties prop  = tiledObj.getProperties();
                int gid = (Integer)prop.get("gid");
                String direction = (String)tileset.getTile(gid).getProperties().get("Direction");
                String shape = (String)tileset.getTile(gid).getProperties().get("Shape");
                Rectangle rect = new Rectangle((Float)prop.get("x"), (Float)prop.get("y"), (Float)prop.get("width"), (Float)prop.get("height"));
                StageConfig config = new StageConfig();
                config.setPositionX((int)rect.getX());
                config.setPositionY((int)rect.getY());
                config.setDclass(shape);
                config.setConfigName(direction);
                config.setRotation(Integer.parseInt(JsonConfigFactory.getInstance().getCourseConfig(direction).getValue()));
                // set touchable region in texture region
                config.setOrigPositionX();
                config.setOrigPositionX();
                config.setWidth();
                config.setHeight();
                dominoInstances.add(config);
                //dominoObject.setPosition(rect.getX(), rect.getY());
                //dominoObject.setDirection(direction);
                //dominoObject.setShape(shape);
                //dominoInstances.add(dominoObject);
            }
        } catch (Error e) {
            Gdx.app.error(LOG_TAG, "Error happend when load course tiled map file");
        }
    }

    public void nextLevel() {
    }

    public void failLevel() {
    }

    public void passLevel() {
    }
}
