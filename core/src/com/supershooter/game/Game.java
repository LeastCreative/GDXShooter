package com.supershooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supershooter.game.enemy.PingPong;

/**
 * The main game application class, controls game logic
 */
public class Game extends ApplicationAdapter {
    //functional game objects
    static SpriteBatch batch;
    static Stage stage;
    static Hud hud;

    //logical game objects
    static Player player;
    static State state;
    static boolean isRespawning;

    /**
     * Sets up the game objects before ever calling render.
     */
    @Override
    public void create() {
        //setup graphics

        state = new GameState();
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
            public boolean keyUp(InputEvent event, int keycode) {
                return state.keyUp(event, keycode);
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
        state.update(deltaTime);
        state.draw(batch);
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


class GameState extends State {

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Game.state = State.PAUSED;
            Game.hud.setPaused(true);
            Timer.instance().stop();
        }
        return true;
    }

    @Override
    public void update(float deltaTime) {

        //draw actors to stage
        Game.stage.act(deltaTime);
        Game.hud.act(deltaTime);

        //schedule respawn 3 seconds from destroy
        if (Game.player.isDestroyed() && !Game.isRespawning) {
            Game.isRespawning = true;
            if (Game.hud.getLives() > 0)
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Game.isRespawning = false;
                        Game.player.respawn();
                        Game.stage.addActor(Game.player);
                        Game.hud.addLives(-1);
                    }
                }, 3);
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Game.stage.draw();
        Game.hud.draw();
    }

}


class PausedState extends State {


    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Game.state = State.GAME;
            Game.hud.setPaused(false);
            Timer.instance().start();
        }
        return true;
    }


    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void draw(SpriteBatch batch) {
        //clear the screen to white
        Gdx.gl.glClearColor(.4f, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Game.stage.draw();
        Game.hud.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            Game.state = State.GAME;
            Game.hud.setPaused(false);
            Timer.instance().start();
        }

    }

}

abstract class State extends InputListener {

    static final State GAME;
    static final State PAUSED;

    static {
        PAUSED = new PausedState();
        GAME = new GameState();
    }

    public abstract void update(float deltaTime);

    public abstract void draw(SpriteBatch batch);


}


