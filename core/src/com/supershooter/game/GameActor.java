package com.supershooter.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.supershooter.game.projectile.Projectile;

/**
 * An object that takes actions over time.
 * <p>
 * Created by Dan on 1/27/2017.
 */
public abstract class GameActor extends Actor {
    protected Vector2 velocity = new Vector2();
    protected Array<Projectile> projectiles = new Array<Projectile>();
    protected boolean isDestroyed;

    /**
     * @return true if this actor is destroyed
     */
    public final boolean isDestroyed() {
        return isDestroyed;
    }

    public void hitBy(Projectile projectile) {
        /**
         * not implemented - do nothing
         */
    }

    /**
     * Destroys this actor
     */
    public abstract void destroy();

    /**
     * @return the projectiles that belong to this actor
     */
    public Array<Projectile> getProjectiles(){
        return projectiles;
    }

}
