package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer  {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private World world;
	
	public WorldRenderer(World world) {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		this.world = world;
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		loadTextures();
		
	}
	private void loadTextures(){
		texture = new Texture(Gdx.files.internal("characterDirection0.png"));
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 480, 320);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
	}

	public void render() {		
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
}

