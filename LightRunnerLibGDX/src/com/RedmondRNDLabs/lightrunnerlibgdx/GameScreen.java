package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private Input input;
	private int width, height;
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render();

	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world);
		input = new Input();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		input.update(world, width, height, x, y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		input.update(world, width, height, screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
