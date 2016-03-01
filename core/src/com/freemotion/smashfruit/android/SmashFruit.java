package com.freemotion.smashfruit.android;

import com.badlogic.gdx.Gdx;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Resources.LogoTextureLoader;
import com.freemotion.smashfruit.android.Screens.LogoScreen;
import com.freemotion.smashfruit.android.Utils.GameBase;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

public class SmashFruit extends GameBase {

	public SmashFruit() {
	}

	@Override
	public void create () {
		super.create();

		JsonConfigFactory configFactory = JsonConfigFactory.getInstance();
		configFactory.createAnimationConfigs("config/AnimationConfig");
		AnimationConfig ac = configFactory.getAnimationConfig("logo2");
		if (ac != null) {
			Gdx.app.log("SmashFruit", "config : " + ac.key + " " + ac.atlas + " " + ac.region + " " + ac.duration + " " + ac.mode);
		}
		//configFactory.dumpAnimationJsonConfigs();

		LogoTextureLoader logoLoader = new LogoTextureLoader();
		ResourceManager.getInstance().addLoader(logoLoader);
		logoLoader.load();
		logoLoader.finishLoading();
		LogoScreen logoScreen = new LogoScreen(this);
		setScreen(logoScreen);
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
