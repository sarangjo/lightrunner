package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	Light light;
	
	
	boolean lightIntersection = false;
	
	public World(){
		player = new Player(new Vector2(0, 0), "characterDirection0.png");
		mirror = new Mirror(new Vector2(50, 0), "characterDirection0.png");
		light = new Light(new Vector2(500, 720), mirror.getCenter());
	}
	
	public void loadContent()
	{
		player.loadContent();
		mirror.loadContent();
	}
	
	public void update(){
		light.update(mirror.getCenter(), mirror.angle);
		if(Intersector.isPointInPolygon(light.beams.get(1).beamPolygon, new Vector2(300, 300))){
			lightIntersection = true;
		} else {
			lightIntersection = false;
		}
	}
	public void draw(SpriteBatch batch)
	{
		player.draw(batch);
		mirror.draw(batch);
	}
}
