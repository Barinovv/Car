package ru.bav.cargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture racingImage;
	private Vector3 touchPos;
	private Array<Rectangle> numbrSpaw;
	private Array<Rectangle> raisingspawn;
	private long lastRacingtime;
	private Texture background;
	private Texture textureNumbre;
	private int oneNumber;
	private int secondNumber;
	private int numbr;
	private int[] coordinates = {20, 270, 520};
	private String string = oneNumber + "+" + secondNumber;
	private BitmapFont font;
	private int score = 0;
	int i = 0;
	int d = 0;

	private Rectangle rasingspaw = new Rectangle();
	private Rectangle numbrSpawn = new Rectangle();

	// создание объека "Automobile"
	private Automobile car = new Automobile(10,20,64,64,"car.png");

	// создание объекта "Road"
	private Road road = new Road(0,50, 64, 64, rasingspaw);

	private Number num = new Number(coordinates[MathUtils.random(0, 2)], 1000, 64, 64, numbrSpawn);

	@Override

	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 860);

		batch = new SpriteBatch();
		font = new BitmapFont();

		racingImage = new Texture("rasing.png");
		textureNumbre = new Texture("2.png");
		background = new Texture("bg.png");

		touchPos = new Vector3();

		raisingspawn = new Array<Rectangle>();
		numbrSpaw = new Array<Rectangle>();

		raisingspawn.add(road);
		lastRacingtime = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		carRender();
		roadRender();
		numRender();

		batch.begin();

		batch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);

		font.draw(batch, string, 1000, 800);
		font.draw(batch, score + "", 1000, 750);


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

	/*
	 * отрисовка цифр
	 * */
	private void numRender() {

		batch.begin();

		for (Rectangle numbrSpawn : numbrSpaw) {
			batch.draw(textureNumbre, numbrSpawn.x, numbrSpawn.y);
		}

		batch.end();

	}

	/*
	 * отрисовка объектов "Road"
	 * */
	private void roadRender() {

		batch.begin();

		for (Rectangle rasingspaw : raisingspawn) {
			batch.draw(racingImage, rasingspaw.x, rasingspaw.y);
		}

		batch.end();
	}

	/*
	 * отрисовка объекта "Automobile"
	 * */
	private void carRender() {

		batch.begin();
		batch.draw(car.sheet, car.x, car.y, car.width, car.height);
		batch.end();

	}

	//*  Спавн дороги.
	private void spawnRacing() {

		raisingspawn.add(road);
		lastRacingtime = TimeUtils.nanoTime();

	}

	//* рандом примера и спавн цифры.
	private void spawnNumbrs() {
		spawnNumbr();
		oneNumber = MathUtils.random(1, 10);
		secondNumber = MathUtils.random(1, 10);
		numbr = oneNumber + secondNumber;
		string = oneNumber + "+" + secondNumber;
		d++;
	}

	//* спавн падающих цифр цифр.
	private void spawnNumbr() {

		numbrSpaw.add(num);

	}

	@Override
	public void dispose() {
		super.dispose();
		racingImage.dispose();
		textureNumbre.dispose();

	}
}
