package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.supershooter.game.enemy.Enemy;

/**
 * The player actor. Accepts input from the keyboard
 * TODO: implement using controller
 * <p>
 * Created by Dan on 1/27/2017.
 */
public class Player extends GameActor {
    private Texture texture;

    private float speed = 200;
    private boolean movingUp;
    private boolean movingRight;
    private boolean movingDown;
    private boolean movingLeft;

    Player(Stage stage) {
        //all enemies will attack this player
        Enemy.player = this;

        //set up the player to spawn in the middle of screen
        this.setX(Gdx.graphics.getWidth() / 2);
        this.setY(Gdx.graphics.getHeight() / 2);

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
                    default:
                        return false;
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
                        return false;
                }
                return true;
            }
        });
    }

    /**
     * Updates the state of this actor over time
     *
     * @param delta time passed since last update
     */
    @Override
    public void act(float delta) {
        //must be a diagonal motion
        if ((movingUp ^ movingDown && movingLeft ^ movingRight)) {
            moveBy((movingLeft ? -1 : 1) * speed * delta / 1.41f, (movingUp ? -1 : 1) * speed * delta / 1.14f);
        }
        //otherwise assume axis direction; assumes up and left
        else {
            if (movingUp | movingDown)
                moveBy(0, (movingUp ? -1 : 1) * speed * delta);
            if (movingLeft | movingRight)
                moveBy((movingLeft ? -1 : 1) * speed * delta, 0);
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

}
