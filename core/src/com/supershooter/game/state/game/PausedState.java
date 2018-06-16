package com.supershooter.game.state.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Timer;
import com.supershooter.game.AudioManager;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 1/31/2017.
 */
class PausedState extends GameState {

    private boolean unpause;

    PausedState(GameScreen screen){
        super(screen);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            screen.hud.setPaused(false);
            AudioManager.resume();
            Timer.instance().start();
            unpause = true;
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //close pause menu if button was pressed
    }

    @Override
    public GameStateCode getNextState() {
        if(unpause) {
            unpause = false;
            return GameStateCode.RUNNING;
        }
        return GameStateCode.PAUSED;
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(.4f, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.stage.draw();
        screen.hud.draw();
    }

}
