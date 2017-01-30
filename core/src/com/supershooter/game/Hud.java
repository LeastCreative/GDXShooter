package com.supershooter.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The information about the game displayed during play
 * <p>
 * Created by Dan on 1/29/2017.
 */
class Hud extends Stage {

    private int score;
    private int lives;

    private Label scoreLabel;
    private Label livesLabel;

    Hud() {
        super();
        score = 1220;
        lives = 5;

        Table table = new Table();
        table.top();
        table.moveBy(60, 40);
        scoreLabel = new Label(String.format("%6d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label(lives + "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //set up score row
        table.row();
        table.add(new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        table.add(new Label(" : ", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        table.add(scoreLabel);

        //set up lives rolls
        table.row();
        table.add(new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        table.add(new Label(" : ", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        table.add(livesLabel);

        this.addActor(table);
    }

    void addPoints(int p) {
        this.score += p;
        scoreLabel.setText(score + "");
    }

    void addLives(int l) {
        lives += l;
        livesLabel.setText(lives + "");
    }

}
