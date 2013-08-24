package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;

public class WorldRenderer {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private StatLogger statlogger;
	private ShapeRenderer sr;
	private int width, height;
	public boolean terminate = false;

	// INTRO variables
	public float switchTime = 5f;
	public float fadeBufferTime = 0.75f;
	public float introTime = 0f;
	public float introAlpha = 0f;

	// private Texture titleScreen;

	public WorldRenderer(World world) {
		this.world = world;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1, height / width);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		loadContent();

	}

	private void loadContent() {
		// titleScreen = new
		// Texture(Gdx.files.internal("LightRunnerTitle.png"));
		world.loadContent();
	}

	public void render(GameState state) {
		if (state == GameState.PLAYING || state == GameState.MENU) {
			if (state == GameState.PLAYING
					|| (state == GameState.MENU && world.menu.menuState == Menu.MenuState.MAIN))
				world.update();

			world.draw(batch, sr);
			if (world.player.alive == false) {
				state = GameState.GAMEOVER;

				// this is new
				try {
					world.updateStatLogger(world.statlogger);
					world.statlogger.writeCumulativeToFile();
					world.statlogger.writeHighToFile();
					// line below just for testing
					// System.out.print("Wrote to file");
				} catch (Exception e) {
				}
				// to remove later
				terminate = true;
			}
		}
		batch.begin();
		if (state == GameState.LOADING) {
			batch.draw(Assets.loadingScreen, 0, 0);
		} else if (state == GameState.GAMEOVER) {
			batch.draw(Assets.gameOverScreen, 0, 0);
		} else if (state == GameState.MENU
				&& world.menu.menuState == Menu.MenuState.INTRODUCTION
				&& GameScreen.introCut < 3) {
			if (introTime <= fadeBufferTime) {
				// fading in
				introAlpha = introTime / fadeBufferTime;
			}
			if (introTime >= switchTime - fadeBufferTime) {
				// fading out
				introAlpha = 1 - (introTime - (switchTime - fadeBufferTime))
						/ (fadeBufferTime);
			}
			batch.end();
			Assets.drawByPixels(batch, Assets.fullScreen, Color.BLACK);
			batch.begin();
			batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b,
					introAlpha);
			// This style scales it to the entire screen.
			// batch.draw(Assets.introCuts[GameScreen.introCut], 0, 0, width,
			// height);
			// This style draws it in the center.
			batch.draw(Assets.introCuts[GameScreen.introCut], (width 
					- Assets.introCuts[GameScreen.introCut].getWidth())/2,
					(height - Assets.introCuts[GameScreen.introCut].getHeight()) / 2);
			introTime += Gdx.graphics.getDeltaTime();
			if (introTime >= switchTime) {
				// switching screens
				introTime = 0f;
				GameScreen.introCut++;
			}
		}
		batch.end();
	}
}
