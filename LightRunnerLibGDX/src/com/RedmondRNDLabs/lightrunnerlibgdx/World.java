package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	Light light;
	BitmapFont bf;
	long startTime, currentTime, deltaTime, totalTime;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	
	public World(){
		player = new Player(new Vector2(0, 0), "characterDirection0.png");
		mirror = new Mirror(new Vector2(50, 0), "characterDirection0.png");
		light = new Light(new Vector2(500, 720), mirror.getCenter());
		for(int i = 0; i < 25; i++){
			enemies.add(new Enemy(new Vector2(MathUtils.random(300, 1250), MathUtils.random(0, 700)), 50, 50, new Vector2(-.3f, 0), ""));
		}
		startTime = TimeUtils.millis();
		currentTime = startTime;
	}
	
	public void loadContent()
	{
		player.loadContent();
		mirror.loadContent();
		bf = new BitmapFont();
		bf.scale(2);
		bf.setColor(Color.BLACK);
	}
	
	public void update() {
		light.update(mirror.getCenter(), mirror.angle);
		for (Enemy e : enemies) {
			e.update();
			// overlapConvexPolygons not currently working: Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon, e.p)
			//Intersector.isPointInPolygon(light.beams.get(1).vectorPolygon, e.Position
			//Intersector.intersectSegmentCircle(light.beams.get(1).origin, light.beams.get(1).dst, e.getCenter(), 50)
			if (Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon, e.p)){
				if(e.alive){
					e.health--;
				}
			}
		}
		deltaTime = TimeUtils.millis() - currentTime;
		currentTime = TimeUtils.millis();
		totalTime += deltaTime;
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer sr)
	{
		batch.begin();
		player.draw(batch);
		mirror.draw(batch);
		bf.draw(batch, "Time: " + (int)((totalTime)/ 1000) + "s", 0, 720);
		bf.draw(batch, "dTime: " + deltaTime + "ms", 225, 720);
		batch.end();
		
		light.draw(sr);
		
		
		for(Enemy e: enemies){
			if(e.alive)
				e.draw(sr);
		}

	}
}
