package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.RedmondRNDLabs.lightrunnerlibgdx.GameScreen.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The World class holds all of the players, enemies and environment objects.
 * It handles collisions and drawing methods, as well as loading content.
 */

public class World {

	
	GameScreen.GameState state;
	Player player;
	Mirror mirror;
	Light light;
	BitmapFont bf;
	
	float deltaTime, totalTime;
	float loadedContentPercent;
	
	Vector2 ENEMY_VEL;

	Rectangle playButton;
	
	boolean menuScreen;
	boolean playSelected;
	
	ArrayList<Enemy> enemies;
	ArrayList<Enemy> enemiesAlive;
	
	/**
	 * There are two types of worlds, the menu world and the in-game world.
	 * The behavior of the light depends on whether the game is in the menu or playing state.
	 * @param isMenu
	 */
	public World(boolean isMenu){
		totalTime = 0;
		playButton = new Rectangle(400, 100, 500, 100);
		enemies = new ArrayList<Enemy>();
		enemiesAlive = new ArrayList<Enemy>();
		ENEMY_VEL = new Vector2(-.3f, 0);
		menuScreen = isMenu;
		player = new Player(new Vector2(0, 300), "characterDirection0.png");
		mirror = new Mirror(new Vector2(100, 300), "mirror.png");
		
		if(menuScreen)
			light = new Light(true);
		else
			light = new Light(new Vector2(640, 720), mirror.getCenter());
		
		// temporarily used for spawning enemies
		for(int i = 0; i < 25; i++)
			enemies.add(new Enemy(new Vector2(MathUtils.random(300, 1250), MathUtils.random(0, 700)), 50, 50, ENEMY_VEL, ""));
	}
	
	/**
	 * Loads all the content of the World.
	 */
	public void loadContent()
	{
		player.loadContent();
		mirror.loadContent();
		bf = new BitmapFont();
		bf.scale(1);
		bf.setColor(Color.WHITE);
		
	}
	
	/**
	 * Updates the entire World. Includes light, enemy movement, and enemy destruction.
	 * Also updates the time functions for frame rate-independent functions
	 * deltaTime and totalTime are all in seconds.
	 */
	public void update() {
		
		light.update(mirror.getCenter(), mirror.angle);
		
		for (Enemy e : enemies) {
			e.update();
			// overlapConvexPolygons not currently working: Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon, e.p)
			//Intersector.isPointInPolygon(light.beams.get(1).vectorPolygon, e.Position
			//Intersector.intersectSegmentCircle(light.beams.get(1).origin, light.beams.get(1).dst, e.getCenter(), 50)
			if(menuScreen){
				if (Intersector.overlapConvexPolygons(light.beams.get(0).beamPolygon, e.p)){
					if(e.alive)
						e.health--;
				}
				
				if(light.beams.get(0).dst.x > playButton.x - 100 && light.beams.get(0).dst.x < playButton.x + playButton.width + 100)
					playSelected = true;
				else 
					playSelected = false;
				
			} else {
				if (Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon, e.p)){
					if(e.alive)
						e.health--;
				}
			}
			
			
			// adds the number of enemies still alive to a new ArrayList
			if(e.alive)
				enemiesAlive.add(e);
		}
		
		// removes the "dead" enemies from the main ArrayList
		enemies.retainAll(enemiesAlive);
		enemiesAlive.clear();
		
		
		// temporarily spawns new enemies, which get progressively faster
		if(enemies.size() < 25)
			enemies.add(new Enemy(new Vector2(MathUtils.random(800, 1250), MathUtils.random(0, 700)), 50, 50, new Vector2(ENEMY_VEL.x -= .005f, 0), ""));
		
		// misc time functions
		deltaTime = Gdx.graphics.getDeltaTime();
		totalTime += deltaTime;
	}

	public void draw(SpriteBatch batch, ShapeRenderer sr) {

		for (Enemy e : enemies)
			e.draw(sr);
		
		if (menuScreen) {
			sr.begin(ShapeType.FilledRectangle);
			if (playSelected) 
				sr.setColor(Color.WHITE);
			else
				sr.setColor(Color.LIGHT_GRAY);
			sr.filledRect(playButton.x, playButton.y, playButton.width, playButton.height);
			sr.end();
			batch.begin();
			bf.setColor(Color.BLACK);
			bf.draw(batch, "Play", 610, 160);
			batch.end();
		} else {
			batch.begin();
			player.draw(batch, mirror.angle - 90);
			mirror.draw(batch);
			bf.setColor(Color.WHITE);
			bf.draw(batch, "Time: " + (int) (totalTime) + "s", 0, 720);
			bf.draw(batch, "dTime: " + (int) (deltaTime * 1000) + "ms", 225, 720);
			batch.end();
		}
		
		light.draw(sr);


	}
}
