package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	Light light;
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	boolean lightIntersection = false;
	
	public World(){
		player = new Player(new Vector2(0, 0), "characterDirection0.png");
		mirror = new Mirror(new Vector2(50, 0), "characterDirection0.png");
		light = new Light(new Vector2(500, 720), mirror.getCenter());
		for(int i = 0; i < 25; i++){
			enemies.add(new Enemy(new Vector2(MathUtils.random(300, 1250), MathUtils.random(0, 700)), 100, 100, ""));
		}
	}
	
	public void loadContent()
	{
		player.loadContent();
		mirror.loadContent();
	}
	
	public void update() {
		light.update(mirror.getCenter(), mirror.angle);
		for (Enemy e : enemies) {
			e.Position.x -= .3f;
			if (Intersector.isPointInPolygon(light.beams.get(1).beamPolygon, e.Position))
				e.health--;
			e.update();
		}
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer sr)
	{
		batch.begin();
		player.draw(batch);
		mirror.draw(batch);
		batch.end();
		
		light.draw(sr);
		
		sr.begin(ShapeType.FilledCircle);
		sr.setColor(Color.BLACK);
		for(Enemy e: enemies){
			if(e.alive)
				sr.filledCircle(e.Position.x, e.Position.y, e.health);
		}
		sr.end();

	}
}
