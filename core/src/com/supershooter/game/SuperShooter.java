package com.supershooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

public class SuperShooter extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapes;
    Camera camera;
    Square[] squares;

    @Override
    public void create() {
        camera = new OrthographicCamera(1280, 720);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapes = new ShapeRenderer();
        shapes.setProjectionMatrix(camera.combined);
        img = new Texture("super-shooter.png");
        squares = new Square[98];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square(-640, 40 * i);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw sprites to sprite renderer
        batch.begin();
        batch.draw(img, 0 - img.getWidth() / 2, 0);
        batch.end();

        //draw squares to shape renderer
        shapes.begin(ShapeType.Filled);
        shapes.setColor(1, 0, 0, 1);
        for (int i = 0; i < squares.length; i++) {
            squares[i].update();
            squares[i].draw(shapes);
        }
        shapes.end();
    }

    void updateRect() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}


class Square {
    int x;
    int y;
    int speed = 5;

    //0:up, 1:right, 2:down, 3:left
    int direction = 0;


    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer target) {
        target.rect(x, y, 20, 20);
    }

    public void update() {
        switch (direction) {
            case 0:
                y -= speed;
                if (y <= -360)
                    direction++;
                break;
            case 1:
                x += speed;
                if (x >= 620)
                    direction++;
                break;
            case 2:
                y += speed;
                if (y >= 340)
                    direction++;
                break;
            case 3:
                x -= speed;
                if (x <= -640)
                    direction = 0;
                break;
        }
    }
}