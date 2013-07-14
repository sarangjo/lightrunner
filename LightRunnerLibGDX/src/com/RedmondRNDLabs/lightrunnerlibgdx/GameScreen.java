package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.RedmondRNDLabs.lightrunnerlibgdx.World.MenuState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	static enum GameState{
		Loading, Menu, Ready, Playing, Paused, GameOver
	}
	static enum ControlScheme{
		none, top, right, bottom
	}
	
	public GameState state;
	public static ControlScheme scheme = ControlScheme.none;
	private World world;
	private WorldRenderer renderer;
	private Input input;
	private int width, height;
	

	/**
	 * First method that is called when GameScreen is created
	 */
	@Override
	public void show() {
		state = GameState.Loading;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		input = new Input();
	}
	
	/**
	 * Called once every refresh
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		update();
		renderer.render(state);
	}
	
	/**
	 * Used to create new objects and switch between gamestates
	 */
	public void update(){
		if(state == GameState.Loading){
			world = new World(true);
			renderer = new WorldRenderer(world);
			state = GameState.Menu;
		} else if (state == GameState.Ready){
			world = new World(false);
			renderer = new WorldRenderer(world);
			state = GameState.Playing;
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
		input.update(world, width, height, x, y, state);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(state ==  GameState.Menu){
			// Draws the light in the menu only when a touch is registered.
			world.light.beams.get(1).updateIncomingBeam(new Vector2(0, 720), 0, true);
			if(world.playSelected)
				world.menuState = MenuState.chooseSide;
			if(world.controlsSelected)
				state = GameState.Ready;
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
