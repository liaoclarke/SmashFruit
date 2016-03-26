package com.freemotion.smashfruit.android.Sprites.Widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.freemotion.smashfruit.android.Misc.JsonConfigFactory;
import com.freemotion.smashfruit.android.Misc.JsonConfigFileParser;
import com.freemotion.smashfruit.android.Misc.StageConfig;
import com.freemotion.smashfruit.android.Utils.Bundle;
import com.freemotion.smashfruit.android.Utils.MessageHub;

import java.lang.reflect.Constructor;

/**
 * Created by liaoclark on 3/26/2016.
 */
public class UnlockDialog extends BaseActor implements TransitionActor, JsonConfigFileParser, MessageHub {

    private String configFile, configName;
    private Array<BaseActor> group;
    private String LOG_TAG;
    private Stage parentStage;

    public UnlockDialog(LevelButton button) {
        super();
        LOG_TAG = this.getClass().getSimpleName();
        parentStage = button.getStage();
        configFile = "config/UnlockDialogConfig";
        configName = "UnlockDialog";
        group = new Array<BaseActor>();
        JsonConfigFactory.getInstance().createStageConfigs(configFile, this);
        JsonConfigFactory.getInstance().inflateStage(configName, this);

        DialogCloseButton closeButton = (DialogCloseButton) findChildByName("UnlockDialogCloseButton");
        closeButton.setMessageHub(this);
    }

    @Override
    public void getGameActorObject(StageConfig config) {
        try {
            String pkg = "com.freemotion.smashfruit.android.Sprites.Widget";
            Class cls = Class.forName(pkg + "." + config.getDclass());
            Constructor ctor = cls.getConstructor(StageConfig.class);
            BaseActor ac = (BaseActor) ctor.newInstance(config);
            ac.setParent(this);
            group.add(ac);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Can not create object from config : " + config.getKey());
            throw new RuntimeException("Can not parser config file: " + configFile);
        }
    }

    @Override
    public void show() {
        for (BaseActor ac : group) {
            parentStage.addActor(ac);
            ac.show();
        }
    }

    @Override
    public void hide() {
        for (BaseActor ac : group) {
            ac.remove();
        }
    }

    @Override
    public void forwardMessageToListener(String message, Bundle data) {
        if ("close_unlock_dialog".equals(message)) {
            hide();
        }
    }

    public Actor findChildByName(String configName) {
        for (TransitionActor ac : group) {
            if (ac.getActor() != null && configName.equals(ac.getActor().getName()))  {
                return ac.getActor();
            }
        }
        return null;
    }
}
