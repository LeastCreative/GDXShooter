package com.supershooter.game.state.player;

/**
 * Created by evenl on 4/2/2017.
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.supershooter.game.Player;

/**
 * Created by evenl on 1/31/2017.
 */
public abstract class PlayerState extends InputListener {
    protected Player player;

    public abstract void act(float delta);

    public abstract void draw(Batch batch, float parentAlpha);

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                player.movingUp = true;
                break;
            case Input.Keys.RIGHT:
                player.movingRight = true;
                break;
            case Input.Keys.DOWN:
                player.movingDown = true;
                break;
            case Input.Keys.LEFT:
                player.movingLeft = true;
                break;
            case Input.Keys.W:
                player.shootingUp = true;
                break;
            case Input.Keys.D:
                player.shootingRight = true;
                break;
            case Input.Keys.S:
                player.shootingDown = true;
                break;
            case Input.Keys.A:
                player.shootingLeft = true;
                break;
        }
        return false;
    }

    /**
     * Called by stage when any key is released
     *
     * @param event   the key event
     * @param keycode the key code
     * @return true if the event has been handled
     */
    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                player.movingUp = false;
                break;
            case Input.Keys.RIGHT:
                player.movingRight = false;
                break;
            case Input.Keys.DOWN:
                player.movingDown = false;
                break;
            case Input.Keys.LEFT:
                player.movingLeft = false;
                break;
            case Input.Keys.W:
                player.shootingUp = false;
                break;
            case Input.Keys.D:
                player.shootingRight = false;
                break;
            case Input.Keys.S:
                player.shootingDown = false;
                break;
            case Input.Keys.A:
                player.shootingLeft = false;
                break;
        }
        return false;
    }

}