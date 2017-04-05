package com.supershooter.game.state.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supershooter.game.Player;

/**
 * Created by evenl on 4/2/2017.
 */
public class RespawnState extends PlayerState {
    private final Texture TEXTURE;
    private final float RESPAWN_DELAY = 5;

    private float currentDelay = RESPAWN_DELAY;

    public RespawnState(Player player) {
        Pixmap pm = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pm.setColor(1, 1, 1, 1);
        pm.fill();
        TEXTURE = new Texture(pm);
        pm.dispose();
        this.player = player;
    }


    public void act(float delta) {
        //do stuff
        player.move(delta);
        currentDelay -= delta;

        //check state
        if (currentDelay <= 0) {
            player.setInvincible(false);
            currentDelay = RESPAWN_DELAY;
            player.setState(player.aliveState);
        }
        if (player.isShooting()) {
            player.shoot();
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(TEXTURE, player.getX(), player.getY());
    }
}

