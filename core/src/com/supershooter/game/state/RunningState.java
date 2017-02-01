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
class RunningState extends GameState {


    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            current = StateCode.PAUSED;
            Game.hud.setPaused(true);
            Timer.instance().stop();
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        //draw actors to stage
        Game.stage.act(deltaTime);
        Game.hud.act(deltaTime);

        //schedule respawn 3 seconds from destroy
        if (Game.player.isDestroyed() && !Game.isRespawning) {
            Game.isRespawning = true;
            if (Game.hud.getLives() > 0)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Game.isRespawning = false;
                        Game.player.respawn();
                        Game.stage.addActor(Game.player);
                        Game.hud.addLives(-1);
                    }
                }, 3);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Game.stage.draw();
        Game.hud.draw();
    }

}



