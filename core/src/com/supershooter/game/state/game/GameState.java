package com.supershooter.game.state.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 1/31/2017.
 */

public abstract class GameState extends InputListener {
    GameScreen screen;

    GameState(GameScreen screen) {
        this.screen = screen;
    }

    public abstract void update(float deltaTime);

    public abstract GameStateCode getNextState();

    public abstract void draw(SpriteBatch batch);

}
