package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

/**
 * The main class that governs the game. COntains WorldRenderer and World
 * 
 * @author Sarang
 * 
 */
public class GameScreen implements Screen, InputProcessor {
	/**
	 * The different states of the game.
	 */
	static enum GameState {
		LOADING, INTRO, MENU, READY, PLAYING, /* PAUSED, */GAMEOVER
	}

	/**
	 * Where the light comes from.
	 */
	static enum LightScheme {
		NONE, TOP, RIGHT, BOTTOM, LEFT
	}

	public static GameState state;
	public static LightScheme scheme = LightScheme.NONE;
	public static LightScheme selectedScheme;

	private World world;
	private WorldRenderer renderer;
	private Input input;
	public static int width, height;
	public static int introCut;
	public boolean restart = false;

	public static float musicVolume = 1f;

	private Vector2 mainMenuBeam;

	// Are these from the top-left corner or the bottom-left corner?
	// public static int touchX, touchY;

	/**
	 * First method that is called when GameScreen is created.
	 */
	@Override
	public void show() {
		state = GameState.LOADING;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		input = new Input(Input.Movement.REGIONMOVE);

		Assets.loadContent();
		update();
		mainMenuBeam = new Vector2(0, 720);
		Assets.soundTrack.play();
		introCut = 0;
	}

	/**
	 * Called once every refresh
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render(state);
		update();
	}

	/**
	 * Used to create new objects and switch between game states
	 */
	public void update() {
		if (state == GameState.LOADING) {
			state = GameState.INTRO;
			world = new World();
			renderer = new WorldRenderer(world);
			world.loadContent();
		} else if (state == GameState.INTRO) {
			if(introCut >= 3){
				state = GameState.MENU;
				world = new World();
				renderer = new WorldRenderer(world);
				world.loadContent();
			}
		} else if (state == GameState.READY) {
			world = new World();
			renderer = new WorldRenderer(world);
			if (restart)
				world.selectControls();
			state = GameState.PLAYING;
		}
		// to remove
		if (renderer.terminate) {
			state = GameState.LOADING;
		}

	}

	@Override
	public void resize(int width, int height) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	// INPUT CONTROLLER:
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (pointer == 0
				&& !(world.pauseButton.contains(Input.touchX, Input.touchY) && state == GameState.PLAYING))
			input.update(world, width, height, x, y, state);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Input.touchX = screenX;
		Input.touchY = height - screenY;
		if (state == GameState.MENU) {
			if (world.menu.menuState == Menu.MenuState.MAIN) {
				// Draws the light in the menu only when a touch is registered.
				world.light.getOutgoingBeam().updateIncomingBeam(mainMenuBeam,
						true, world.player);

				// Various button presses.
				// if (world.playSelected)
				if (world.menu.playButton.contains(Input.touchX, Input.touchY)) {
					world.playBlip();
					world.selectControls();
					state = GameState.READY;
				} else if (world.menu.creditsButton.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					world.menu.menuState = Menu.MenuState.CREDITS;
				} else if (world.menu.quitButton.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					Gdx.app.exit();
				}
			} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
				if (pointer == 2
						|| world.menu.resumeButton.contains(Input.touchX,
								Input.touchY)) {
					world.playBlip();
					state = GameState.PLAYING;
				} else if (world.menu.restartButton.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					renderer.terminate = true;
					restart = true;
					state = GameScreen.GameState.READY;
				} else if (world.menu.backMainButton.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					world.menu.menuState = Menu.MenuState.MAIN;
					renderer.terminate = true;
					state = GameScreen.GameState.LOADING;
				} else if (world.menu.musicButton.bounds.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					System.out.println("set volume to " + musicVolume);
					musicVolume = (musicVolume == 0) ? 1f : 0f;
					Assets.soundTrack.setVolume(musicVolume);
				} else if (world.menu.sfxButton.bounds.contains(Input.touchX,
						Input.touchY)) {
					world.playBlip();
					System.out.println("sfx on? " + World.soundFX);
					World.soundFX = !World.soundFX;
				}
			}
			
		} else if (state == GameState.PLAYING) {
			// 2 represents a triple touch.
			if (pointer == 2
					|| world.pauseButton.contains(Input.touchX, Input.touchY)) {
				world.playBlip();
				state = GameState.MENU;
				world.menu.menuState = Menu.MenuState.PAUSE;
				System.out.println("registered");
			}
			if (world.player.inventory.size() > 0
					&& world.player.inventoryRects[0].contains(Input.touchX,
							Input.touchY)) {
				world.playBlip();
				world.usePowerup(world.player.inventory.get(0));
				world.player.inventory.remove(0);
			}
		} else if (state == GameState.INTRO) {
			introCut++;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		input.update(world, width, height, screenX, screenY, state);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public boolean inputUpdate(World w, int width, int height, int screenX,
			int screenY, GameState state) {
		return false;
	}
}
