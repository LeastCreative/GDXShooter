package com.supershooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.ViewPort;
import com.supershooter.game.enemy.Enemy;
import com.supershooter.game.enemy.PingPong;

import java.util.LinkedList;

/**
 * The main game application class, controls game logic
 */
public class SuperShooter extends ApplicationAdapter {
    //functional game objects
    private SpriteBatch batch;
    private Stage stage;

    //game objects logical
    private Player player;
    private Hud hud;

    /**
     * Sets up the game objects before ever calling render.
     */
    @Override
    public void create() {
        //setup graphics
        OrthographicCamera camera = new OrthographicCamera(1280, 720);
        camera.setToOrtho(true);
        FitViewport viewPort = new FitViewport(1280, 720, camera);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        stage = new Stage(viewPort, batch);
        Gdx.input.setInputProcessor(stage);

        //setup game
        player = new Player(stage);
        hud = new Hud();
        for (int i = 0; i < 20; i++) {
            stage.addActor(new PingPong());
        }
    }

    /**
     * The main game loop.
     * Updates the state of all game objects, and draws them.
     */
    @Override
    public void render() {
        //get time passed since last render in seconds
        float deltaTime = Gdx.graphics.getDeltaTime();

        //clear the screen to white
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw actors to stage
        stage.act(deltaTime);
        stage.draw();
        hud.act(deltaTime);
        if (Math.random() > .95)
            hud.addPoints(10);
        hud.draw();
    }

    /**
     * Tear down
     */
    @Override
    public void dispose() {
        batch.dispose();
    }
}


