package com.freemotion.smashfruit.android.Utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

/**
 * Created by liaoclark on 1/23/16.
 */
public class TransitionScreen implements Screen {

    private GameBase game;
    private Screen prev;
    private Screen next;
    private int currentTransitionEffect;
    private Array<TransitionEffect> transitionEffects;

    public TransitionScreen(GameBase game, ScreenBase prev, ScreenBase next, Array<TransitionEffect> transitionEffects) {
        this.game = game;
        this.prev = prev;
        this.next = next;
        this.transitionEffects = transitionEffects;
        this.currentTransitionEffect = 0;
    }

    @Override
    public void render(float delta) {
        if (currentTransitionEffect >= transitionEffects.size) {
            game.setScreen(next);
            return;
        }
        boolean finished = transitionEffects.get(currentTransitionEffect).update(delta);
        transitionEffects.get(currentTransitionEffect).render(prev, next);
        if (finished) {
            currentTransitionEffect += 1;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }
}
