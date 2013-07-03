package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen {

	private World world;
	private WorldRenderer renderer;
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

}
