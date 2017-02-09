package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.supershooter.game.enemy.Enemy;
import com.supershooter.game.projectile.Missile;
import com.supershooter.game.projectile.Projectile;
import com.supershooter.game.screen.GameScreen;

/**
 * The player actor. Accepts input from the keyboard
 * TODO: implement using controller
 * <p>
 * Created by Dan on 1/27/2017.
 */
public class Player extends GameActor {
    private Texture texture;

    private float speed = 300;
    private static Array<Projectile> projectiles = new Array<Projectile>();

    //moving states
    private boolean movingUp;
    private boolean movingRight;
    private boolean movingDown;
    private boolean movingLeft;

    //bullet timing
    private final float reloadTime = .1f;
    private float timeSinceShot = 0;

    //shooting states
    private boolean shootingUp;
    private boolean shootingRight;
    private boolean shootingDown;
    private boolean shootingLeft;


    public Player(Stage stage) {
        //all enemies will attack this player
        Enemy.player = this;
        //initialize texture
        if (texture == null) {
            Pixmap pm = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
            pm.setColor(0, 1, 0, 1);
            pm.fill();
            texture = new Texture(pm);
            pm.dispose();
        }

        //TODO: discuss why/if this should go somewhere else
        stage.addActor(this);
        stage.addListener(new InputListener() {

            /**
             * Called by stage when any key is pressed down
             *
             * @param event the key event
             * @param keycode the key code
             * @return true if the event has been handled
             */
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        movingUp = true;
                        break;
                    case Input.Keys.RIGHT:
                        movingRight = true;
                        break;
                    case Input.Keys.DOWN:
                        movingDown = true;
                        break;
                    case Input.Keys.LEFT:
                        movingLeft = true;
                        break;
                    case Input.Keys.W:
                        shootingUp = true;
                        break;
                    case Input.Keys.D:
                        shootingRight = true;
                        break;
                    case Input.Keys.S:
                        shootingDown = true;
                        break;
                    case Input.Keys.A:
                        shootingLeft = true;
                        break;
                }
                return true;
            }

            /**
             * Called by stage when any key is released
             *
             * @param event the key event
             * @param keycode the key code
             * @return true if the event has been handled
             */
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        movingUp = false;
                        break;
                    case Input.Keys.RIGHT:
                        movingRight = false;
                        break;
                    case Input.Keys.DOWN:
                        movingDown = false;
                        break;
                    case Input.Keys.LEFT:
                        movingLeft = false;
                        break;
                    case Input.Keys.W:
                        shootingUp = false;
                        break;
                    case Input.Keys.D:
                        shootingRight = false;
                        break;
                    case Input.Keys.S:
                        shootingDown = false;
                        break;
                    case Input.Keys.A:
                        shootingLeft = false;
                        break;
                }
                return true;
            }
        });
        respawn();
    }

    /**
     * Updates the state of this actor over time
     *
     * @param delta time passed since last update
     */
    @Override
    public void act(float delta) {
        timeSinceShot += delta;
        updateProjectiles();

        //process moving
        if ((movingUp ^ movingDown && movingLeft ^ movingRight)) {
            //must be a diagonal motion
            moveBy((movingLeft ? -1 : 1) * speed * delta / 1.41f, (movingUp ? -1 : 1) * speed * delta / 1.14f);

        } else {
            //otherwise assume axis direction; assumes up and left
            if (movingUp | movingDown)
                moveBy(0, (movingUp ? -1 : 1) * speed * delta);

            if (movingLeft | movingRight)
                moveBy((movingLeft ? -1 : 1) * speed * delta, 0);

        }

        //player perimeter ([0,0] top left to (screenWidth() - playerwidth), screenHeight() - playerheight)
        if (getX() > Gdx.graphics.getWidth() - 30)
            setX(Gdx.graphics.getWidth() - 30);
        if (getX() < 0)
            setX(0);
        if (getY() > Gdx.graphics.getHeight() - 30)
            setY(Gdx.graphics.getHeight() - 30);
        if(getY() < 0)
            setY(0);

        //process shooting
        if (isShooting() && timeSinceShot > reloadTime) {
            shoot();
        }
    }

    /**
     * Draws the Player to the rendering batch
     *
     * @param batch       the batch to render to
     * @param parentAlpha the alpha value of the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //draw to sprite batch
        batch.draw(texture, getX(), getY());
    }

    public void respawn() {
        //set up the player to spawn in the middle of screen
        isDestroyed = false;

        setX(Gdx.graphics.getWidth() / 2 - 10);
        setY(Gdx.graphics.getHeight() / 2 - 10);
    }

    private boolean isShooting() {
        return shootingUp | shootingRight | shootingDown | shootingLeft;
    }

    private void shoot() {
        timeSinceShot = 0;
        Vector2 currPos = new Vector2(getX() + 7f, getY() + 7f);
        Missile bullet;
        if ((shootingUp ^ shootingDown && shootingLeft ^ shootingRight)) {
            //must be a diagonal motion
            bullet = new Missile(currPos, currPos.x + (shootingLeft ? -1 : 1), currPos.y + (shootingUp ? -1 : 1));
            AudioManager.SHOOT.play(1.0f);
        } else {
            //otherwise assume axis direction; assumes up and left
            if (shootingUp | shootingDown) {
                bullet = new Missile(currPos, currPos.x, currPos.y + (shootingUp ? -1 : 1));
            } else
                bullet = new Missile(currPos, currPos.x + (shootingLeft ? -1 : 1), currPos.y);
            AudioManager.SHOOT.play(1.0f);
        }
        projectiles.add(bullet);
        getStage().addActor(bullet);
    }

    /**
     * Updates the state of all projectiles
     */
    Rectangle enemyRect = new Rectangle();
    private void updateProjectiles() {
        if (isDestroyed)
            return;

        //simple collision - probably not efficient
        if (getStage() != null)
            for (Projectile p : projectiles) {
                for (Actor a : getStage().getActors()) {
                    if (a instanceof Enemy) {
                        Enemy e = (Enemy) a;

                        //check collision with enemy/player
                        enemyRect.set(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                        if (enemyRect.contains(getX() + 10, getY() + 10)) {
                            this.destroy();
                            AudioManager.DIE.play();
                            e.destroy();
                            return;
                        }

                        //check collision with bullet/enemy
                        if (enemyRect.contains(p.getX() + 5, p.getY() + 5) && !isDestroyed()) {
                            GameScreen.hud.addPoints(e.getScoreValue());
                            e.hitBy(p);
                            p.destroy();
                            projectiles.removeValue(p, false);
                        }
                    }

                }
            }
    }

    public void hitBy(Projectile p) {
        this.destroy();
        AudioManager.DIE.play();
        p.destroy();
    }

}
