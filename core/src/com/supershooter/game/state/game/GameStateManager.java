package com.supershooter.game.state.game;

import com.supershooter.game.screen.GameScreen;

/**
 * Created by evenl on 1/31/2017.
 */

public class GameStateManager  {
    private GameStateCode current;
    private final GameState[] states;

    public GameStateManager(GameScreen screen) {
        this.current = GameStateCode.RUNNING;

        //setup state array
        this.states = new GameState[GameStateCode.values().length];
        states[GameStateCode.RUNNING.ordinal()] = new RunningState(screen);
        states[GameStateCode.PAUSED.ordinal()] = new PausedState(screen);
    }

    /**
     * @return the current game state
     */
    public GameState current() {
        return states[current.ordinal()];
    }


}
