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
public class AliveState extends PlayerState {
    private final Texture texture;

    public AliveState(Player player) {
        this.player = player;
        Pixmap pm = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pm.setColor(0, 1, 0, 1);
        pm.fill();
        texture = new Texture(pm);
        pm.dispose();
    }

    public void act(float delta) {
        //do stuff
        player.move(delta);

        //check state
        if (player.isDestroyed()) {
            player.setState(player.destroyedState);
        }

       if( player.isShooting()){
           player.shoot();
       }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, player.getX(), player.getY());
    }


}
