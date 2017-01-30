package com.supershooter.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.supershooter.game.projectile.Bullet;

/**
 * This enemy bounces back and forth vertically and horizontally.
 * Attacks by shooting a bullet directly at the player.
 * <p>
 * Created by Dan on 1/27/2017.
 */
public class PingPong extends Enemy {
    private static final RandomXS128 rand = new RandomXS128();
    private static Texture texture;
    private static final int speed = 300;
    private float timeSinceLastAttack;
    private float firingSpeed = rand.nextFloat() * 10;

    public PingPong() {
        if (texture == null) {
            Pixmap pm = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
            pm.setColor(1, 0, 0, 1);
            pm.fill();
            texture = new Texture(pm);
            pm.dispose();
        }

        setX(rand.nextFloat() * Gdx.graphics.getWidth() - 30);
        setY(rand.nextFloat() * Gdx.graphics.getHeight() - 30);

        velocity.add((rand.nextFloat() - .5f) * speed, (rand.nextFloat() - .5f) * speed);
    }

    /**
     * Draws this xxxxxxx to the rendering batch
     *
     * @param batch       the batch to render to
     * @param parentAlpha the alpha value of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    /**
     * Updates the state of this actor over time
     *
     * @param delta time passed since last update in seconds
     */
    @Override
    public void act(float delta) {
        //update this enemies projectiles
        updateProjectiles(delta);
        setOrigin(getWidth() / 2, getHeight() / 2);

        //reverse direction to "bounce" off walls
        if (getX() < 0 && velocity.x < 0 || getX() > Gdx.graphics.getWidth() - 30 && velocity.x > 0)
            velocity.x = -velocity.x;
        if (getY() < 0 && velocity.y < 0 || getY() > Gdx.graphics.getHeight() - 30 && velocity.y > 0)
            velocity.y = -velocity.y;

        //occasionally will attack
        if (!player.isDestroyed())
            timeSinceLastAttack += delta;
        if (timeSinceLastAttack > firingSpeed) {
            if (!player.isDestroyed())
                attackPlayer();
            firingSpeed = rand.nextFloat() * 7;
        }

        moveBy(velocity.x * delta, velocity.y * delta);
    }

    /**
     * This enemy attacks the player
     */
    @Override
    public void attackPlayer() {
        Bullet newBullet = new Bullet(new Vector2(getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2), player.getX() + 10, player.getY() + 10);
        projectiles.add(newBullet);
        this.getStage().addActor(newBullet);
        timeSinceLastAttack = 0;
    }

    @Override
    public int getScoreValue() {
        return 100;
    }

}