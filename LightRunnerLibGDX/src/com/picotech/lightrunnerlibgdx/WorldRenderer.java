package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;

public class WorldRenderer {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	// private StatLogger2 statlogger;
	private ShapeRenderer sr;
	private int width, height;
	public boolean terminate = false;

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
		world.loadContent();
	}

	public void render(GameState state) {
		if (state == GameState.PLAYING || state == GameState.MENU) {
			if (state == GameState.PLAYING
					|| (state == GameState.MENU && (world.menu.menuState == Menu.MenuState.MAIN || world.menu.menuState == Menu.MenuState.OPTIONS)))
				if(!GameScreen.dialogBoxActive)
					world.update();

			world.draw(batch, sr);
			if (world.player.alive == false) {
				state = GameState.GAMEOVER;

				// to remove later
				terminate = true;
			}
		}
		batch.begin();
		if (state == GameState.LOADING) {
			batch.draw(Assets.loadingScreen, 0, 0);
		} else if (state == GameState.GAMEOVER) {
			StatLogger2.endGame(world.score, world.enemiesKilled,
					(int) world.totalTime);
			batch.draw(Assets.gameOverScreen, 0, 0);
		}

		batch.end();
		Assets.setTextScale(2f);
		Assets.text(batch, "deltaTouch: " + Input.dragDistance, 0, 0);
	}
}
