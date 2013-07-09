package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WorldRenderer  {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private ShapeRenderer sr;
	private int width, height;
	
	public WorldRenderer(World world) {		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		this.world = world;
		
		camera = new OrthographicCamera(1, height/width);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
		loadTextures();
		
	}
	
	private void loadTextures(){
		world.loadContent();
	}

	public void render() {		
		world.update();
		world.draw(batch, sr);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}

