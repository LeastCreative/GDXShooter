package com.supershooter.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * An object that takes actions over time.
 * <p>
 * Created by Dan on 1/27/2017.
 */
public abstract class GameActor extends Actor {
    protected Vector2 velocity = new Vector2();
    private boolean isDestroyed;

    /**
     * @return true if this actor is destroyed
     */
    public final boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Destroys this actor
     */
    protected void destroy() {
        isDestroyed = true;
    }
}
