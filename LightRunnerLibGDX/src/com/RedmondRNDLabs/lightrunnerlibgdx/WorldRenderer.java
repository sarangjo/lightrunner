package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.RedmondRNDLabs.lightrunnerlibgdx.GameScreen.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WorldRenderer  {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private GameScreen.GameState state;
	private ShapeRenderer sr;
	private int width, height;
	private Texture titleScreen;
	
	public WorldRenderer(World world) {		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		this.world = world;
		
		camera = new OrthographicCamera(1, height/width);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		loadTextures();
		
	}
	
	private void loadTextures()
	{
		titleScreen = new Texture(Gdx.files.internal("LightRunnerTitle.png"));
		world.loadContent();
	}

	public void render(GameState state) {	
		world.update();
		world.draw(batch, sr);
		if(state == GameState.Menu){
			batch.begin();
			batch.draw(titleScreen, 150, 100);
			batch.end();
		}
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}

