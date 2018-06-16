package com.supershooter.game.projectile;

import com.supershooter.game.GameActor;

/**
 * Projectile class
 * <p>
 * Created by Dan on 1/28/2017.
 */
public abstract class Projectile extends GameActor {
    private final GameActor owner;
    public Projectile(GameActor owner)
    {
        this.owner = owner;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        this.remove();
        owner.getProjectiles().removeValue(this, false);
    }
}
