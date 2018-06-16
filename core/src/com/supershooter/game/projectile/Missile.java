package com.supershooter.game.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.supershooter.game.GameActor;


/**
 * Missile that homes in on the player.
 * TODO: implement missle
 * <p>
 * Created by Dan on 1/28/2017.
 */
public class Missile extends Projectile {
    private static TextureRegion texture;
    private static Rectangle t = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private static final int speed = 800;

    private float rotation = 0f;

    public Missile(GameActor owner,Vector2 pos, float targetx, float targety) {
        super(owner);
        if (texture == null) {
            Pixmap pm = new Pixmap(10, 10, Pixmap.Format.LuminanceAlpha);
            pm.setColor(.7f, .7f, 1, 1);
            pm.fillRectangle(4, 0, 6, 10);
            Texture t = new Texture(pm);
            texture = new TextureRegion(t);
            pm.dispose();
        }

        //set rotation
        //TODO: could be simplified probably
        if (targetx != pos.x) {
            rotation = 90;
            if (targetx > pos.x && targety < pos.y || targetx < pos.x && targety > pos.y)
                rotation = 45;
            else if (targetx > pos.x && targety > pos.y || targetx < pos.x && targety < pos.y)
                rotation = 135;
        }


        velocity.set(targetx - pos.x, targety - pos.y).nor();
        velocity.x *= speed;
        velocity.y *= speed;

        setX(pos.x - texture.getTexture().getWidth() / 2);
        setY(pos.y - texture.getTexture().getHeight() / 2);
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
        batch.draw(texture, getX(), getY(), 5, 5, 10, 10, 1, 1, rotation);
    }
}

