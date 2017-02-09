package com.supershooter.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.supershooter.game.AudioManager;
import com.supershooter.game.Game;
import com.supershooter.game.Hud;
import com.supershooter.game.Player;
import com.supershooter.game.enemy.PingPong;
import com.supershooter.game.enemy.Weirdo;
import com.supershooter.game.state.GameState;

/**
 * Created by evenl on 2/1/2017.
 */
public class GameScreen extends ScreenAdapter {
    final Game game;
    //functional game objects
    static SpriteBatch batch;
    public static Stage stage;
    public static Hud hud;

    //logical game objects
    public static Player player;
    public static boolean isRespawning;

    public GameScreen(final Game game) {
        this.game = game;
        AudioManager.MUSIC.loop(1);
        reset();
    }

    public void reset() {
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

        for (int i = 0; i < 8; i++) {
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
                stage.addActor(new Weirdo());
            }
        }, 2, 5);
        stage.addActor(new Weirdo());

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                stage.addActor(new PingPong());
            }
        }, .5f, .7f);


    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        GameState.current().update(delta);
        GameState.current().draw(batch);
        this.pause();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        hud.dispose();
    }
}
