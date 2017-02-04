package com.supershooter.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.supershooter.game.projectile.Bullet;
import com.supershooter.game.projectile.Projectile;


/**
 * Created by evenl on 2/1/2017.
 */
public class Weirdo extends Enemy {
    private static final int width = 50;
    private static final int height = 50;
    private static final RandomXS128 rand = new RandomXS128();
    private TextureRegion texture;
    private Texture textures[];
    private Rectangle rectangle = new Rectangle();
    boolean clockwise;


    private static final int speed = 300;
    private float timeSinceLastAttack;
    private float firingSpeed = rand.nextFloat() * 10;
    private float rotation = 0;
    private Vector2 direction;
    private Vector2 targetDirection;

    public Weirdo() {
        texture = new TextureRegion();
        if (textures == null) {
            textures = new Texture[5];
            Pixmap pm = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            for (int i = 0; i < 5; i++) {
                pm.setColor(.4f - .1f * i, .6f - i * .1f, 1, 1);
                pm.fill();
                textures[i] = new Texture(pm);
            }
            texture.setTexture(textures[4]);
            pm.dispose();
        }

        direction = new Vector2();
        targetDirection = new Vector2();
        setX(rand.nextBoolean() ? Gdx.graphics.getWidth() : 0);
        setY(rand.nextBoolean() ? Gdx.graphics.getHeight() : 0);

        velocity.add(getX() == 0 ? 1 : -1, getY() == 0 ? 1 : -1);
        velocity.nor();
    }

    /**
     * Draws this bullet to the rendering batch
     *
     * @param batch       the batch to render to
     * @param parentAlpha the alpha value of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), width / 2, height / 2, width, height, 1, 1, rotation);
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

        targetDirection.x = player.getX() + 10 - getX() - 25;
        targetDirection.y = player.getY() + 10 - getY() - 25;
        targetDirection.nor();

        Vector2 perp = new Vector2(-velocity.y, velocity.x);
        float dot = perp.dot(targetDirection);
        if (dot > 0) {
            velocity.rotate(100 * delta);
            clockwise = false;
        } else if (dot < 0) {
            velocity.rotate(-100 * delta);
            clockwise = true;
        }

        float rot = !clockwise ? clamp(dot * 500, 10, 500) : clamp(dot * 500, -500, -10);

        rotation += rot * delta;


        // (Optional)check to see if we rotated too far
        perp.set(-velocity.y, velocity.x);
        float newDot = perp.dot(targetDirection);
        if (dot > 0 && newDot < 0 || dot < 0 && newDot > 0) {
            velocity.set(targetDirection).nor();
        }

        //System.out.println(velocity);
        moveBy(velocity.x * 250 * delta, velocity.y * 250 * delta);

    }


    /**
     * This enemy attacks the player
     */
    @Override
    public void attackPlayer() {
        Bullet newBullet = new Bullet(new Vector2(getX() + width / 2, getY() + width / 2), player.
                getX() + 10, player.getY() + 10);
        projectiles.add(newBullet);
        this.getStage().addActor(newBullet);
        timeSinceLastAttack = 0;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    int lives = 5;

    public void hitBy(Projectile p) {
        lives--;
        if (lives == 0)
            destroy();
        else
            texture.setTexture(textures[lives - 1]);
    }

    @Override
    public int getScoreValue() {
        return 100;
    }

    public static float clamp(float val, float min, float max) {
        if (val < min) return min;
        else if (val > max) return max;
        else return val;
    }

}