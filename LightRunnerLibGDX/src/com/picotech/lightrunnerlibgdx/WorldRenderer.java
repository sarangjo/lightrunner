package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;

public class WorldRenderer  {
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private GameScreen.GameState state;
	private ShapeRenderer sr;
	private int width, height;
	boolean terminate = false;
	//private Texture titleScreen;
	
	public WorldRenderer(World world) {	
		this.world = world;		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1, height/width);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		loadContent();
		
	}
	
	private void loadContent()
	{
		//titleScreen = new Texture(Gdx.files.internal("LightRunnerTitle.png"));
		world.loadContent();
	}

	public void render(GameState state) {	
		world.update();
		world.draw(batch, sr);
		if(world.player.alive == false){
			state = GameState.GAMEOVER;
			// to remove later
			terminate = true;
		}
		if(state == GameState.MENU){
			batch.begin();
			batch.draw(Assets.titleScreen, 150, 100);
			batch.end();
		}
		else if(state == GameState.LOADING) {
			batch.begin();
			batch.draw(Assets.loadingScreen, 0, 0);
			batch.end();
		} else if (state == GameState.GAMEOVER){
			
		}
	}
}

