package com.freemotion.smashfruit.android;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.freemotion.smashfruit.android.Game.GameView;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;

/**
 * Created by liaoclark on 1/22/16.
 */
public class SmashFruitGameView extends GameView {

    private AndroidApplication application;
    private RelativeLayout layout;

    public SmashFruitGameView(AndroidApplication application) {
        super(application);
        this.application = application;
    }

    @Override
    public void init() {
        // Do the stuff that initialize() would do for you
        application.requestWindowFeature(Window.FEATURE_NO_TITLE);
        application.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        application.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
        //config.hideStatusBar = true;
        config.useImmersiveMode = true;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            application.getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_VISIBLE);
            application.getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }

        //add adview to
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        View gameView = application.initializeForView(new SmashFruit(), config);
        layout = new RelativeLayout(application);
        layout.addView(gameView);
    }

    @Override
    public View getLayout() {
        return layout;
    }
}
