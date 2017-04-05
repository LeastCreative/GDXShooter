package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Array;
import com.supershooter.game.enemy.Enemy;
import com.supershooter.game.projectile.Missile;
import com.supershooter.game.projectile.Projectile;
import com.supershooter.game.screen.GameScreen;
import com.supershooter.game.state.player.AliveState;
import com.supershooter.game.state.player.DestroyedState;
import com.supershooter.game.state.player.PlayerState;
import com.supershooter.game.state.player.RespawnState;

/**
 * The player actor. Accepts input from the keyboard
 * TODO: implement using controller
 * <p>
 * Created by Dan on 1/27/2017.
 */
public class Player extends GameActor {

    private float speed = 300;

    //moving states
    public boolean movingUp;
    public boolean movingRight;
    public boolean movingDown;
    public boolean movingLeft;

    //bullet timing
    private final float reloadTime = .1f;
    private float timeSinceShot = 0;

    //shooting states
    public boolean shootingUp;
    public boolean shootingRight;
    public boolean shootingDown;
    public boolean shootingLeft;


    //states
    public final AliveState aliveState;
    public final DestroyedState destroyedState;
    public final RespawnState respawnState;

    PlayerState currentState;


    public Array<Projectile> projectiles = new Array<Projectile>();

    public PlayerState getState() {
        return currentState;
    }

    public Player(Stage stage) {
        aliveState = new AliveState(this);
        destroyedState = new DestroyedState(this);
        respawnState = new RespawnState(this);
        currentState = aliveState;

        //all enemies will attack this player
        Enemy.player = this;

        addListener(new EventListener() {
            @Override
            public boolean handle(Event e) {
                return currentState.handle(e);
            }
        });

        //TODO: discuss why/if this should go somewhere else

        stage.addActor(this);
        respawn();
    }

    public void setState(PlayerState newstate) {
        currentState = newstate;
    }

    @Override
    public void act(float delta) {
        timeSinceShot += delta;
        currentState.act(delta);
        checkCollistions();
    }

    public void move(float delta) {
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
        if (getY() < 0)
            setY(0);
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
        currentState.draw(batch, parentAlpha);
    }

    public void respawn() {
        //set up the player to spawn in the middle of screen
        isDestroyed = false;

        setX(Gdx.graphics.getWidth() / 2 - 10);
        setY(Gdx.graphics.getHeight() / 2 - 10);
    }

    boolean isInvincible = false;

    public void setInvincible(boolean value) {
        isInvincible = value;
    }

    public boolean isInvincible() {
        return isInvincible;
    }


    public void hitBy(Projectile p) {
        if (!isInvincible && !isDestroyed) {
            this.destroy();
            AudioManager.DIE.play();
            p.destroy();
        }
    }


    public void shoot() {
        //process shooting
        if (timeSinceShot < reloadTime) {
            return;
        }

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

    public boolean isShooting() {
        return shootingUp | shootingRight | shootingDown | shootingLeft;
    }


    /**
     * Updates the state of all projectiles
     */
    Rectangle enemyRect = new Rectangle();

    public void checkCollistions() {
        //simple collision - probably not efficient
        if (getStage() != null)
            for (Actor a : getStage().getActors()) {
                if (a instanceof Enemy) {
                    Enemy e = (Enemy) a;
                    //check collision with enemy/player
                    enemyRect.set(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                    if (!isInvincible && !isDestroyed) {
                        if (enemyRect.contains(getX() + 10, getY() + 10)) {
                            destroy();
                            AudioManager.DIE.play();
                            e.destroy();
                            return;
                        }
                    }
                    for (Projectile p : projectiles) {
                        //check collision with bullet/enemy
                        if (enemyRect.contains(p.getX() + 5, p.getY() + 5) && !isDestroyed()) {
                            GameScreen.hud.addPoints(e.getScoreValue());
                            e.hitBy(p);
                            p.destroy();
                            projectiles.removeValue(p, false);
                            break;
                        }
                    }
                }
            }
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }


}
