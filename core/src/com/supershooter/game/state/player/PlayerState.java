package com.supershooter.game.state.player;

/**
 * Created by evenl on 4/2/2017.
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.supershooter.game.AudioManager;
import com.supershooter.game.Player;
import com.supershooter.game.enemy.Enemy;
import com.supershooter.game.projectile.Missile;
import com.supershooter.game.projectile.Projectile;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 1/31/2017.
 */
public abstract class PlayerState extends InputListener {
    protected float speed = 300;

    //moving states
    static boolean movingUp;
    static boolean movingRight;
    static boolean movingDown;
    static boolean movingLeft;

    //bullet timing
    protected final float reloadTime = .1f;
    protected float timeSinceShot = 0;

    //shooting states
    static boolean shootingUp;
    static boolean shootingRight;
    static boolean shootingDown;
    static boolean shootingLeft;


    static PlayerStateCode current = PlayerStateCode.ALIVE;

    private static final PlayerState ALIVE;
    private static final PlayerState DESTROYED;
    private static final PlayerState RESPAWNING;

    static {
        ALIVE = new AliveState();
        DESTROYED = new DestroyedState();
        RESPAWNING = new RespawnState();
    }

    enum PlayerStateCode {
        ALIVE, DESTROYED, RESPAWNING,
    }

    public synchronized static PlayerState current() {
        switch (current) {
            case ALIVE:
                return ALIVE;
            case DESTROYED:
                return DESTROYED;
            case RESPAWNING:
                return RESPAWNING;

            default:
                throw new IllegalArgumentException(current.toString() + " is not valid");
        }
    }

    public abstract void update(Player player, float deltaTime);

    public abstract void draw(SpriteBatch batch);

    protected boolean isShooting() {
        return shootingUp | shootingRight | shootingDown | shootingLeft;
    }

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
        return false;
    }

    /**
     * Called by stage when any key is released
     *
     * @param event   the key event
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
        return false;
    }

    protected void shoot(Player player) {
        timeSinceShot = 0;
        Vector2 currPos = new Vector2(player.getX() + 7f, player.getY() + 7f);
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
        player.projectiles.add(bullet);
        if (player.getStage() != null)
            player.getStage().addActor(bullet);
    }

    /**
     * Updates the state of all projectiles
     */
    Rectangle enemyRect = new Rectangle();

    protected void updateProjectiles(Player player) {
        //simple collision - probably not efficient
        if (player.getStage() != null)
            for (Actor a : player.getStage().getActors()) {
                if (a instanceof Enemy) {
                    Enemy e = (Enemy) a;
                    //check collision with enemy/player
                    enemyRect.set(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                    if (!player.isInvincible()) {
                        if (enemyRect.contains(player.getX() + 10, player.getY() + 10)) {
                            player.destroy();
                            AudioManager.DIE.play();
                            e.destroy();
                            return;
                        }
                    }
                    for (Projectile p : player.projectiles) {
                        //check collision with bullet/enemy
                        if (enemyRect.contains(p.getX() + 5, p.getY() + 5) && !player.isDestroyed()) {
                            GameScreen.hud.addPoints(e.getScoreValue());
                            e.hitBy(p);
                            p.destroy();
                            player.projectiles.removeValue(p, false);
                        }
                    }

                }
            }
    }

}