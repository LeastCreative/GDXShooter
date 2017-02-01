package com.supershooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supershooter.game.enemy.PingPong;
import com.supershooter.game.state.GameState;

/**
 * The main game application class, controls game logic
 */
public class Game extends ApplicationAdapter {
    //functional game objects
    static SpriteBatch batch;
    public static Stage stage;
    public static Hud hud;

    //logical game objects
    public static Player player;
    public static boolean isRespawning;

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

        //this forwards the key to whatever state is currently on
        stage.addListener(new InputListener() {
            @Override
            public boolean handle(Event e) {
                return GameState.current().handle(e);
            }
        });

        //spawn new enemy every 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stage.addActor(new PingPong());
            }
        }, .5f, .5f);
    }

    /**
     * The main game loop.
     * Updates the state of all game objects, and draws them.
     */
    @Override
    public void render() {
        //get time passed since last render in seconds
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState.current().update(deltaTime);
        GameState.current().draw(batch);
    }

    /**
     * Tear down
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        hud.dispose();
    }
}





