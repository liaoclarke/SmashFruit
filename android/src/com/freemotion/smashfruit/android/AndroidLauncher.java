package com.freemotion.smashfruit.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.freemotion.smashfruit.android.Game.GameView;

public class AndroidLauncher extends AndroidApplication {

	private GameView gameView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        gameView = new SmashFruitGameView(this);
        gameView.init();
        setContentView(gameView.getLayout());

		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new SmashFruit(), config);
	}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
