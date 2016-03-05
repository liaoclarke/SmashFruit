package com.freemotion.smashfruit.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Resources.LogoTextureLoader;
import com.freemotion.smashfruit.android.Resources.SceneTextureLoader;
import com.freemotion.smashfruit.android.Utils.GameBase;
import com.freemotion.smashfruit.android.Utils.ResourceManager;
import com.freemotion.smashfruit.android.Utils.ScreenBase;

/**
 * Created by liaoclark on 1/22/16.
 */
public class LogoScreen extends ScreenBase {

    private String LOG_TAG;
    private SceneTextureLoader sceneTextureLoader;
    private LogoTextureLoader logoTextureLoader;
    private GameScreen gameScreen;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Animation logo;
    private Rectangle logoRectangle;
    private float stateTime;
    private float delayTime;
    private static final float MAX_DELAY_TIME = 1f;
    private float app_width;
    private float app_height;

    public LogoScreen(GameBase gameInstance) {
        super(gameInstance);
        LOG_TAG = this.getClass().getSimpleName();
        app_width = Float.parseFloat(JsonConfigFactory.getInstance().getKeyConfig("APP_WIDTH").getValue());
        app_height = Float.parseFloat(JsonConfigFactory.getInstance().getKeyConfig("APP_HEIGHT").getValue());
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, app_width, app_height);
        camera.update();
        logoTextureLoader = (LogoTextureLoader) ResourceManager.getInstance().findLoader(LogoTextureLoader.class.getSimpleName());
        AnimationConfig ac = JsonConfigFactory.getInstance().getAnimationConfig("logo");
        logo = new Animation(ac.getDuration(), logoTextureLoader.getTextureAtlas().findRegions(ac.getRegion()));
        logo.setPlayMode(ac.getMode());
        logoRectangle = new Rectangle((app_width - logo.getKeyFrame(0).getRegionWidth() * 1.5f) * 0.5f,
                                      (app_height- logo.getKeyFrame(0).getRegionHeight() * 1.5f) * 0.5f,
                                      logo.getKeyFrame(0).getRegionWidth() * 1.5f,
                                      logo.getKeyFrame(0).getRegionHeight() * 1.5f);
        stateTime = 0f;
        delayTime = 0f;

        sceneTextureLoader = new SceneTextureLoader();
        ResourceManager.getInstance().addLoader(sceneTextureLoader);
        sceneTextureLoader.load();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        if (paused) {
            return;
        }

        stateTime += delta;
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(logo.getKeyFrame(stateTime), logoRectangle.x, logoRectangle.y, logoRectangle.width, logoRectangle.height);
        batch.end();

        if (logo.isAnimationFinished(stateTime)) {
            delayTime += delta;
        }

        if (delayTime > MAX_DELAY_TIME && sceneTextureLoader.update() && gameInstance.getScreen() != gameScreen) {
            Gdx.app.log(LOG_TAG, " set gamescreen ");
            gameScreen = new GameScreen(gameInstance);
            gameInstance.setScreen(gameScreen);
            //FadeOutTransition fadeOut = new FadeOutTransition(1f);
            //FadeInTransition fadeIn = new FadeInTransition(1f);
            //Array<TransitionEffect> transitionEffects = new Array<TransitionEffect>();
            //transitionEffects.add(fadeOut);
            //transitionEffects.add(fadeIn);
            //TransitionScreen transitionScreen = new TransitionScreen(gameInstance, this, gameScreen, transitionEffects);
            //gameInstance.setScreen(transitionScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        super.pause();
        logo = null;
        Gdx.app.log(LOG_TAG, "pause");
    }

    @Override
    public void resume() {
        super.resume();
        AnimationConfig ac = JsonConfigFactory.getInstance().getAnimationConfig("logo2");
        logoTextureLoader = (LogoTextureLoader) ResourceManager.getInstance().findLoader(LogoTextureLoader.class.getSimpleName());
        logo = new Animation(ac.getDuration(), logoTextureLoader.getTextureAtlas().findRegions(ac.getRegion()));
        logo.setPlayMode(ac.getMode());
        stateTime = 0f;
        Gdx.app.log(LOG_TAG, "resume: resourceManager: " + ResourceManager.getInstance());
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
