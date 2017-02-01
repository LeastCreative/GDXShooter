package com.supershooter.game.enemy;

import com.badlogic.gdx.utils.Array;
import com.supershooter.game.*;
import com.supershooter.game.projectile.*;


/**
 * Abstract enemy class, share a target player instance
 * <p>
 * Created by Dan on 1/27/2017.
 */
public abstract class Enemy extends GameActor {
    public static Player player;
    Array<Projectile> projectiles = new Array<Projectile>();

    public abstract void attackPlayer();

    public abstract int getScoreValue();

    /**
     * Updates the state of all projectiles
     *
     * @param delta time passed since last update in seconds
     */
    void updateProjectiles(float delta) {
        for (Projectile p : projectiles) {
            if (p.isDestroyed()) {
                projectiles.removeValue(p, false);
            } else {
                //simple collision - probably not efficient
                double dist = Math.sqrt(Math.pow(player.getX() + 5 - p.getX(), 2) + Math.pow(player.getY() + 5 - p.getY(), 2));
                if (dist < 13 && !player.isDestroyed())
                    player.hitBy(p);
            }
        }
    }

    @Override
    public void hitBy(Projectile p) {
        destroy();
    }
}
