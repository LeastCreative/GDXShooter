package com.supershooter.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Bullet class to shoot with
 * <p>
 * Created by Dan on 1/28/2017.
 */
public class Bullet extends Projectile {
    private static Texture texture;
    private static Rectangle t = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private static final int speed = 500;

    public Bullet(Vector2 pos, float targetx, float targety) {
        if (texture == null) {
            Pixmap pm = new Pixmap(10, 10, Pixmap.Format.LuminanceAlpha);
            pm.setColor(1, 1, 1, 1);
            pm.fillCircle(5, 5, 4);
            texture = new Texture(pm);
            pm.dispose();
        }

        velocity.set(targetx - pos.x, targety - pos.y).nor();
        velocity.x *= speed;
        velocity.y *= speed;

        setX(pos.x - texture.getWidth() / 2);
        setY(pos.y - texture.getHeight() / 2);
    }
    @Override
    public void destroy() {
        isDestroyed = true;
        this.remove();
    }

    /**
     * Updates the state of this actor over time
     *
     * @param delta time passed since last update in seconds
     */
    @Override
    public void act(float delta) {
        moveBy(velocity.x * delta, velocity.y * delta);
        if (!t.contains(getX(), getY())) {
            destroy();
        }
    }

    /**
     * Draws this bullet to the rendering batch
     *
     * @param batch       the batch to render to
     * @param parentAlpha the alpha value of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}