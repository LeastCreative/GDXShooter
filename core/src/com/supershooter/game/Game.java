package com.supershooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supershooter.game.enemy.PingPong;


/**
 * The main game application class, controls game logic
 */
public class Game extends ApplicationAdapter {
    //functional game objects
    private SpriteBatch batch;
    private Stage stage;
    static Hud hud;

    //game objects logical
    private Player player;
    private boolean isRespawning;

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
        for (int i = 0; i < 10; i++) {
            stage.addActor(new PingPong());
        }

        //spawn new enemy every 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stage.addActor(new PingPong());
            }
        }, 3, 4);
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

        //schedule respawn 3 seconds from destroy
        if (player.isDestroyed() && !isRespawning) {
            isRespawning = true;
            if (hud.getLives() > 0)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isRespawning = false;
                        player.respawn();
                        stage.addActor(player);
                        hud.addLives(-1);
                    }
                }, 3);
        }

        hud.act(deltaTime);
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


