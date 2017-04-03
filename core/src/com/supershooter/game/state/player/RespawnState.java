package com.supershooter.game.state.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supershooter.game.Player;

/**
 * Created by evenl on 4/2/2017.
 */
class RespawnState extends PlayerState {
    private final float RESPAWN_DELAY = 5;

    private float currentDelay = RESPAWN_DELAY;

    public void update(Player player, float delta) {
        updateProjectiles(player);

        currentDelay -= delta;
        if (currentDelay <= 0) {
            player.setInvincible(false);
            currentDelay = RESPAWN_DELAY;
            current = PlayerStateCode.ALIVE;
        }

        timeSinceShot += delta;

        //process moving
        if ((movingUp ^ movingDown && movingLeft ^ movingRight)) {
            //must be a diagonal motion
            player.moveBy((movingLeft ? -1 : 1) * speed * delta / 1.41f, (movingUp ? -1 : 1) * speed * delta / 1.14f);

        } else {
            //otherwise assume axis direction; assumes up and left
            if (movingUp | movingDown)
                player.moveBy(0, (movingUp ? -1 : 1) * speed * delta);

            if (movingLeft | movingRight)
                player.moveBy((movingLeft ? -1 : 1) * speed * delta, 0);

        }

        //player perimeter ([0,0] top left to (screenWidth() - playerwidth), screenHeight() - playerheight)
        if (player.getX() > Gdx.graphics.getWidth() - 30)
            player.setX(Gdx.graphics.getWidth() - 30);
        if (player.getX() < 0)
            player.setX(0);
        if (player.getY() > Gdx.graphics.getHeight() - 30)
            player.setY(Gdx.graphics.getHeight() - 30);
        if (player.getY() < 0)
            player.setY(0);

        //process shooting
        if (isShooting() && timeSinceShot > reloadTime) {
            shoot(player);
        }

    }

    public void draw(SpriteBatch batch) {

    }
}

