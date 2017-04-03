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
public class Hud extends Stage {

    private Integer score;
    private Integer lives;

    private Label scoreLabel;
    private Label livesLabel;
    private Label pauseLabel;

    public Hud() {
        super();
        score = 0;
        lives = 5;

        Table table = new Table();
        table.top();
        table.moveBy(60, 40);
        scoreLabel = new Label(score.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label(lives.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

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

        //setup pause lable
        pauseLabel = new Label("PAUSE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pauseLabel.setPosition(getWidth() / 2 - pauseLabel.getWidth() / 2, getHeight() / 2);
        pauseLabel.setVisible(false);
        this.addActor(pauseLabel);
    }


    public int getLives() {
        return lives;
    }

    public void setPaused(boolean paused) {
        pauseLabel.setVisible(paused);
    }

    public void addPoints(int p) {
        this.score += p;
        scoreLabel.setText(score.toString());
    }

    public void addLives(int l) {
        lives += l;
        livesLabel.setText(lives.toString());
    }

}
