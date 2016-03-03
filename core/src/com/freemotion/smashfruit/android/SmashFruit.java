package com.freemotion.smashfruit.android;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.freemotion.smashfruit.android.Misc.AnimationConfig;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.KeyConfig;
import com.freemotion.smashfruit.android.Resources.LogoTextureLoader;
import com.freemotion.smashfruit.android.Screens.LogoScreen;
import com.freemotion.smashfruit.android.Utils.GameBase;
import com.freemotion.smashfruit.android.Utils.ResourceManager;

public class SmashFruit extends GameBase {

	private int logLevel = Application.LOG_NONE;

	public SmashFruit() {
	}

	@Override
	public void create () {
		super.create();

		Gdx.app.setLogLevel(logLevel);
		JsonConfigFactory.getInstance().createKeyConfigs("config/KeyConfig");
		JsonConfigFactory.getInstance().createAnimationConfigs("config/AnimationConfig");

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
