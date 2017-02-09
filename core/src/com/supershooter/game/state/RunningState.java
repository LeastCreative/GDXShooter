package com.supershooter.game.state;

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
class RunningState extends GameState {


    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            current = StateCode.PAUSED;
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
        GameScreen.stage.act(deltaTime);
        GameScreen.hud.act(deltaTime);

        //schedule respawn 3 seconds from destroy
        if (GameScreen.player.isDestroyed() && !GameScreen.isRespawning) {
            GameScreen.isRespawning = true;
            if (GameScreen.hud.getLives() > 0)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        GameScreen.isRespawning = false;
                        GameScreen.player.respawn();
                        GameScreen.stage.addActor(GameScreen.player);
                        GameScreen.hud.addLives(-1);
                    }
                }, 3);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GameScreen.stage.draw();
        GameScreen.hud.draw();
    }

}



