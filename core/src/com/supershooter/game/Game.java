package com.supershooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.supershooter.game.screen.MenuScreen;

/**
 * The main game application class, controls game logic
 */
public class Game extends com.badlogic.gdx.Game {


    /**
     * Sets up the game objects before ever calling render.
     */
    @Override
    public void create() {
        this.screen = new MenuScreen(this);
    }

    /**
     * The main game loop.
     * Updates the state of all game objects, and draws them.
     */
    @Override
    public void render() {
        //get time passed since last render in seconds
        float deltaTime = Gdx.graphics.getDeltaTime();
        screen.render(deltaTime);
    }

    /**
     * Tear down
     */
    @Override
    public void dispose() {
        screen.dispose();
    }
}





