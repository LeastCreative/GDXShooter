package com.supershooter.game.state.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supershooter.game.Player;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 4/2/2017.
 */
public class DestroyedState extends PlayerState {
    private final float DESTROY_DELAY = 3;
    private float currentDelay = DESTROY_DELAY;

    public DestroyedState(Player player) {
        this.player = player;
    }

    public void act(float delta) {
        //update state
        currentDelay -= delta;

        //check state change
        if (currentDelay <= 0) {
            if (GameScreen.hud.getLives() > 0) {
                player.respawn();
                GameScreen.stage.addActor(player);
                GameScreen.hud.addLives(-1);
                player.setInvincible(true);

                currentDelay = DESTROY_DELAY;
                player.setState(player.respawnState);
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {

    }

}
