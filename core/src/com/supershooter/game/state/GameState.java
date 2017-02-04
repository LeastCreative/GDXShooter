package com.supershooter.game.state;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by evenl on 1/31/2017.
 */
public abstract class GameState extends InputListener {
    static StateCode current = StateCode.RUNNING;

    private static final GameState PAUSED;
    private static final GameState GAME;

    static {
        GAME = new RunningState();
        PAUSED = new PausedState();
    }

    enum StateCode {
        RUNNING, PAUSED
    }

    public static GameState current() {
        switch (current) {
            case RUNNING:
                return GAME;
            case PAUSED:
                return PAUSED;
            default:
                throw new IllegalArgumentException(current.toString() + " is not valid");
        }
    }

    public abstract void update(float deltaTime);

    public abstract void draw(SpriteBatch batch);

}
