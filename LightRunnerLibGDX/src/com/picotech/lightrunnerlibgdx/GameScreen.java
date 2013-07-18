package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.World.MenuState;

public class GameScreen implements Screen, InputProcessor {

	static enum GameState {
		LOADING, MENU, READY, PLAYING, PAUSED, GAMEOVER
	}

	static enum LightScheme {
		NONE, TOP, RIGHT, BOTTOM
	}
	
	static enum Movement {
		DUALMOVE, MIRRORMOVE, PLAYERMOVE, REGIONMOVE
	}

	public GameState state;
	public static LightScheme scheme = LightScheme.NONE;

	// public static Music soundTrack;
	// public static Sound blip;
	// public static Sound hit;
	// public static Sound died;

	private World world;
	private WorldRenderer renderer;
	private Input input;
	private int width, height;

	/**
	 * First method that is called when GameScreen is created
	 */
	@Override
	public void show() {
		state = GameState.LOADING;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		input = new Input(Movement.REGIONMOVE);

		loadContent();
		update();

		Assets.soundTrack.play();
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
			world = new World(true);
			renderer = new WorldRenderer(world);
			world.loadContent();
			state = GameState.MENU;
		} else if (state == GameState.READY) {
			world = new World(false);
			renderer = new WorldRenderer(world);
			state = GameState.PLAYING;
		}
	}

	/**
	 * Loads all the non-object-specific content in the game.
	 */
	public void loadContent() {
		// TODO: Currently loading content here, find a new spot.
		Assets.soundTrack = Gdx.audio.newMusic(Gdx.files
				.internal("soundtrack.mp3"));
		Assets.blip = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
		Assets.hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		Assets.died = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));

		Assets.titleScreen = new Texture(
				Gdx.files.internal("LightRunnerTitle.png"));
		Assets.loadingScreen = new Texture(
				Gdx.files.internal("LoadingScreen.png"));
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
		input.update(world, width, height, x, y, state);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (state == GameState.MENU) {
			// Draws the light in the menu only when a touch is registered.
			world.light.getOutgoingBeam().updateIncomingBeam(new Vector2(0, 720),
					true);
			if (world.playSelected)
				world.menuState = MenuState.chooseSide;
			if (world.controlsSelected)
				state = GameState.READY;
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

}
