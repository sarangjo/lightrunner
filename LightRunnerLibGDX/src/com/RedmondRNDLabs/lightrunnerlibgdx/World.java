package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * The World class holds all of the players, enemies and environment objects.
 * It handles collisions and drawing methods, as well as loading content.
 */

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	Light light;
	BitmapFont bf;
	static float deltaTime, totalTime;
	
	Vector2 ENEMY_VEL;
	
	ArrayList<Enemy> enemies;
	ArrayList<Enemy> enemiesAlive;
	
	public World(){
		enemies = new ArrayList<Enemy>();
		enemiesAlive = new ArrayList<Enemy>();
		ENEMY_VEL = new Vector2(-.3f, 0);
		player = new Player(new Vector2(0, 0), "characterDirection0.png");
		mirror = new Mirror(new Vector2(100, 0), "mirror.png");
		light = new Light(new Vector2(500, 720), mirror.getCenter());
		
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
			if (Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon, e.p)){
				if(e.alive)
					e.health--;
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
		light.draw(sr);
		
		batch.begin();
		player.draw(batch, mirror.angle - 90);
		mirror.draw(batch);
		bf.draw(batch, "Time: " + (int) (totalTime) + "s", 0, 720);
		bf.draw(batch, "dTime: " + (int) (deltaTime * 1000) + "ms", 225, 720);
		batch.end();
		
		for(Enemy e: enemies)
			e.draw(sr);

	}
}
