package com.supershooter.game.state.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supershooter.game.Player;

/**
 * Created by evenl on 4/2/2017.
 */
class AliveState extends PlayerState {

    public void update(Player player, float delta) {
        //check state
        if (player.isDestroyed()){
            current = PlayerStateCode.DESTROYED;
        }

        timeSinceShot += delta;
        updateProjectiles(player);

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
