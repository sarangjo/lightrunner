package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer  {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private World world;
	private ShapeRenderer sr;
	private int width, height;
	
	Sprite2 p;
	
	public WorldRenderer(World world) {		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		this.world = world;
		
		camera = new OrthographicCamera(1, height/width);
		//camera.position.set(.5f, (h/w)/2, 0);
		//camera.update();
		
		batch = new SpriteBatch();
		
		sr = new ShapeRenderer();
		
		loadTextures();
		
	}
	private void loadTextures(){
		world.loadContent();
	}

	public void render() {		
		
		batch.begin();
		
		world.draw(batch);
		
		batch.end();
		
		sr.begin(ShapeType.Line);
		world.light.draw(sr);
		sr.end();
		
		
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		
	}
}

