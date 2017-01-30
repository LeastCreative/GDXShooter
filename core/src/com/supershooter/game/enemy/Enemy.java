package com.supershooter.game.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.supershooter.game.GameActor;
import com.supershooter.game.Player;
import com.supershooter.game.projectile.Projectile;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Abstract enemy class, share a target player instance
 * <p>
 * Created by Dan on 1/27/2017.
 */
public abstract class Enemy extends GameActor {
    public static Player player;
    protected ConcurrentLinkedQueue<Projectile> projectiles = new ConcurrentLinkedQueue<Projectile>();

    public abstract void attackPlayer();

    /**
     * Updates the state of all projectiles
     *
     * @param delta time passed since last update in seconds
     */
    void updateProjectiles(float delta) {
        for (Projectile p : projectiles) {
            p.act(delta);

            //simple collision - probably not efficient
            double dist = Math.sqrt(Math.pow(player.getX() + 5 - p.getX(), 2) + Math.pow(player.getY() + 5 - p.getY(), 2));
            if (dist < 13 && !player.isDestroyed())
                player.hitBy(p);
        }
    }

    /**
     * Renders all projectiles to the rendering batch
     *
     * @param batch the render batch
     */
    void drawProjectiles(Batch batch) {
        for (Projectile p : projectiles) {
            if (p.isDestroyed())
                projectiles.remove(p);
            else
                p.draw(batch, 1);
        }
    }
}
