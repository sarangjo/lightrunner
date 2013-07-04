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
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer  {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private World world;
	
	Sprite2 p;
	
	public WorldRenderer(World world) {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		this.world = world;
		
		camera = new OrthographicCamera(1, h/w);
		//camera.position.set(.5f, (h/w)/2, 0);
		//camera.update();
		
		batch = new SpriteBatch();
		
		loadTextures();
		
	}
	private void loadTextures(){
		/*
		texture = new Texture(Gdx.files.internal("characterDirection0.png"));
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 480, 320);
		
		sprite = new Sprite(texture, 0, 0, region.getRegionWidth(), region.getRegionHeight());
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(0, 0);
		*/
		
		world.loadContent(new Texture(Gdx.files.internal("characterDirection0.png")));
	}

	public void render() {		
		
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		world.draw(batch);
		
		batch.end();
	}
}

