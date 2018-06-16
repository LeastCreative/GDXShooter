package com.supershooter.game.state.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.supershooter.game.AudioManager;
import com.supershooter.game.enemy.Enemy;
import com.supershooter.game.projectile.Projectile;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 1/31/2017.
 */
class RunningState extends GameState {

    private boolean pause;

    RunningState(GameScreen screen) {
        super(screen);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            pause = true;
            GameScreen.hud.setPaused(true);
            AudioManager.pause();
            Timer.instance().stop();
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //draw actors to stage
        screen.stage.act(deltaTime);
        screen.hud.act(deltaTime);
    }

    @Override
    public GameStateCode getNextState() {
        if (pause) {
            pause = false;
            return GameStateCode.PAUSED;
        }
        return GameStateCode.RUNNING;
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.stage.draw();
        screen.hud.draw();
    }
}



