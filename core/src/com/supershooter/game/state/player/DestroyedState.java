package com.supershooter.game.state.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supershooter.game.Player;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 4/2/2017.
 */
class DestroyedState extends PlayerState {
    private final float DESTROY_DELAY = 3;

    private float currentDelay = DESTROY_DELAY;

    public void update(Player player, float deltaTime) {
        updateProjectiles(player);

        currentDelay -= deltaTime;
        if (currentDelay <= 0) {
            if (GameScreen.hud.getLives() > 0) {
                player.respawn();
                GameScreen.stage.addActor(player);
                GameScreen.hud.addLives(-1);
                player.setInvincible(true);

                currentDelay = DESTROY_DELAY;
                current = PlayerStateCode.RESPAWNING;
            }
        }
    }

    public void draw(SpriteBatch batch) {

    }

}
