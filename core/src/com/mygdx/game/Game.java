package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

public class Game extends ApplicationAdapter {
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture racingImage;
    Texture carImage;
    Rectangle car;
    Vector3 touchPos;
    Array<Rectangle> numbrSpaw;
    Array<Rectangle> raisingspawn;
    long lastRacingtime;
    Texture background;
    Texture textureNumbre;
    int oneNumber;
    int secondNumber;
    int numbr;
    int[] coordinates = {20, 270, 520};
    String string = oneNumber + "+" + secondNumber;
    BitmapFont font;
    int score = 0;
    int i = 0;
    int d = 0;


    @Override

    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 860);
        batch = new SpriteBatch();
        font = new BitmapFont();
        racingImage = new Texture("rasing.png");
        carImage = new Texture("car.png");
        textureNumbre = new Texture("2.png");
        background = new Texture("bg.png");
        car = new Rectangle();
        car.x = 10;
        car.y = 20;
        car.width = 64;
        car.height = 64;
        touchPos = new Vector3();
        raisingspawn = new Array<Rectangle>();
        numbrSpaw = new Array<Rectangle>();
        spawnRacing();
    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);

        font.draw(batch, string, 1000, 800);

        font.draw(batch, score + "", 1000, 750);

        for (Rectangle rasingspaw : raisingspawn) {

            batch.draw(racingImage, rasingspaw.x, rasingspaw.y);
        }
        for (Rectangle numbrSpawn : numbrSpaw) {

            batch.draw(textureNumbre, numbrSpawn.x, numbrSpawn.y);
        }
        batch.draw(carImage, car.x, car.y);


        batch.end();
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            car.x = touchPos.x - 32 / 2;
        }
        if (car.x > 680 - car.width) car.x = 680 - car.width * 2;
        if (car.x < 0) car.x = 0;


        while (numbrSpaw.size < 1) {
            spawnNumbrs();
        }
        if (TimeUtils.nanoTime() - lastRacingtime > 400000000) spawnRacing();
        Iterator<Rectangle> iter = raisingspawn.iterator();
        Iterator<Rectangle> iter2 = numbrSpaw.iterator();


        textureNumbre = new Texture(numbr + ".png");
        while (iter.hasNext()) {
            Rectangle rasingspaw = iter.next();
            rasingspaw.y -= 225 * Gdx.graphics.getDeltaTime();
            if (rasingspaw.y + 64 < 0) iter.remove();
        }
        while (iter2.hasNext()) {
            Rectangle numbrSpawn = iter2.next();
            numbrSpawn.y -= 200 * Gdx.graphics.getDeltaTime();
            if (numbrSpawn.y + 64 < 0) {
                iter2.remove();
                i = 0;
                d = 0;
            }
            if (numbrSpawn.overlaps(car)) {
                numbrSpaw = new Array<Rectangle>();
                i = 0;
                d = 0;
                spawnNumbrs();
                score++;
            }
        }
    }

    private void spawnRacing() {
        Rectangle rasingspaw = new Rectangle();
        rasingspaw.x = 0;
        rasingspaw.y = 50;
        rasingspaw.width = 64;
        rasingspaw.height = 64;
        raisingspawn.add(rasingspaw);
        lastRacingtime = TimeUtils.nanoTime();


    }

    private void spawnNumbrs() {
        spawnNumbr();
        oneNumber = MathUtils.random(1, 10);
        secondNumber = MathUtils.random(1, 10);
        numbr = oneNumber + secondNumber;
        string = oneNumber + "+" + secondNumber;
        d++;
    }


    private void spawnNumbr() {
        Rectangle numbrSpawn = new Rectangle();
        numbrSpawn.x = coordinates[MathUtils.random(0, 2)];
        numbrSpawn.y = 1000;
        numbrSpawn.width = 64;
        numbrSpawn.height = 64;
        numbrSpaw.add(numbrSpawn);


    }

    @Override
    public void dispose() {
        super.dispose();
        racingImage.dispose();
        carImage.dispose();
        textureNumbre.dispose();

    }
}
