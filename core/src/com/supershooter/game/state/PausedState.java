package com.supershooter.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Timer;
import com.supershooter.game.Game;

/**
 * Created by evenl on 1/31/2017.
 */
class PausedState extends GameState {

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Game.hud.setPaused(false);
            Timer.instance().start();
            current = StateCode.RUNNING;
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //do nothing... it is paused
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(.4f, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Game.stage.draw();
        Game.hud.draw();
    }

}
