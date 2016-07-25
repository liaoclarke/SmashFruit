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
import com.freemotion.smashfruit.android.Misc.DominoConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

import java.util.Comparator;

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
    private Array<DominoConfig> dominoInstances;

    private GameController() {
        LOG_TAG = GameController.class.getSimpleName();
        JsonConfigFactory.getInstance().createDominoConfigs("config/DominoConfig");
        bestCourse = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("BestCourse").getValue());
        firstCourse = Integer.parseInt(JsonConfigFactory.getInstance().getKeyConfig("FirstCourse").getValue());
        courseRootDirectory = JsonConfigFactory.getInstance().getKeyConfig("CourseRootDirectory").getValue();
        courseDirectoryPrefix = JsonConfigFactory.getInstance().getKeyConfig("CourseDirectoryPrefix").getValue();
        courseFilePrefix = JsonConfigFactory.getInstance().getKeyConfig("CourseFilePrefix").getValue();
        courseFilePathSeperator = JsonConfigFactory.getInstance().getKeyConfig("CourseFilePathSeperator").getValue();
        courseFileNameSeperator = JsonConfigFactory.getInstance().getKeyConfig("CourseFileNameSeperator").getValue();
        dominoLayerName = JsonConfigFactory.getInstance().getKeyConfig("DominoLayerName").getValue();
        dominoTileSetName = JsonConfigFactory.getInstance().getKeyConfig("DominoTileSetName").getValue();
        dominoInstances = new Array<DominoConfig>();
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

    public Array<DominoConfig> getDominoObjects(){
        return dominoInstances;
    }

    public class DominoComparator implements Comparator<DominoConfig> {
        @Override
        public int compare (DominoConfig sprite1, DominoConfig sprite2) {
            return (sprite2.getIndex() - sprite1.getIndex()) > 0 ? 1 : -1;
        }
    }
    private DominoComparator comparator = new DominoComparator();

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
                int index = Integer.parseInt(tiledObj.getName());
                String direction = (String)tileset.getTile(gid).getProperties().get("Direction");
                Rectangle rect = new Rectangle((Float)prop.get("x"), (Float)prop.get("y"), (Float)prop.get("width"), (Float)prop.get("height"));
                DominoConfig dc = new DominoConfig(JsonConfigFactory.getInstance().getDominoConfig(direction));
                dc.setIndex(index);
                dc.getTile().setTilePositionX((int)rect.x);
                dc.getTile().setTilePositionY((int)rect.y);
                dc.getTile().setTileWidth((int)rect.width);
                dc.getTile().setTileHeight((int)rect.height);
                dominoInstances.add(dc);
            }
            dominoInstances.sort(comparator);
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
