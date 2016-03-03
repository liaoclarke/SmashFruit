package com.freemotion.smashfruit.android.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.freemotion.smashfruit.android.Misc.Constants;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.SmashFruit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Constants.APP_WIDTH;
		config.height = (int) Constants.APP_HEIGHT;
		new LwjglApplication(new SmashFruit(), config);
	}
}
