package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.supershooter.game.state.player.PlayerState;

/**
 * The player actor. Accepts input from the keyboard
 * TODO: implement using controller
 * <p>
 * Created by Dan on 1/27/2017.
 */
public class Player extends GameActor {
    private Texture texture;
    private Texture invincibleTexture;

    public Array<Projectile> projectiles = new Array<Projectile>();


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
        if (invincibleTexture == null) {
            Pixmap pm = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
            pm.setColor(1, 1, 1, 1);
            pm.fill();
            invincibleTexture = new Texture(pm);
            pm.dispose();
        }

        //TODO: discuss why/if this should go somewhere else
        stage.addActor(this);
        respawn();
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
        batch.draw(invincible ? invincibleTexture : texture, getX(), getY());
    }

    public void respawn() {
        //set up the player to spawn in the middle of screen
        isDestroyed = false;

        setX(Gdx.graphics.getWidth() / 2 - 10);
        setY(Gdx.graphics.getHeight() / 2 - 10);
    }

    boolean invincible = false;

    public void setInvincible(boolean value) {
        invincible = value;
    }

    public boolean isInvincible() {
        return invincible;
    }


    public void hitBy(Projectile p) {
        if (!invincible && !isDestroyed) {
            this.destroy();
            AudioManager.DIE.play();
            p.destroy();
        }
    }

}
