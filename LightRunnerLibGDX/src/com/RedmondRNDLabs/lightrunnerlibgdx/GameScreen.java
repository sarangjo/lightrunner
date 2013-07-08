package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
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
		if (x < width && y < height) {
			if ( x < width / 6){
				world.player.setCenter(x, height - y);
				world.mirror.setCenter(x + 15, height - y);
			} else {
				// calculates and sets the mirror angle -- from the touch point to the player position
				world.mirror.setMirrorAngle(world.player.getCenter(), new Vector2(x, height - y));
			}
			// System.out.println("Angle: " + world.m1.angle  + " dstX: " + x + " dstY: " 
			// 		+ y + " p1x: " + world.p1.Position.x + " p1y: " + world.p1.Position.y);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (screenX < width && screenY < height) {
			if ( screenX < width / 6){
				world.player.setCenter(screenX, height - screenY);
				world.mirror.setCenter(screenX + 15, height - screenY);
			} else {
				// calculates and sets the mirror angle -- from the touch point to the player position
				world.mirror.setMirrorAngle(world.player.Position, new Vector2(screenX, height - screenY));
			}
			// System.out.println("Angle: " + world.m1.angle  + " dstX: " + screenX + " dstY: " 
			//		+ screenY + " p1x: " + world.p1.Position.x + " p1y: " + world.p1.Position.y);
			return true;
		}
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
