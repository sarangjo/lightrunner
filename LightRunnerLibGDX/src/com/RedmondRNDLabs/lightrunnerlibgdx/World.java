package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	Light light;
	
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
	
	public void draw(SpriteBatch batch)
	{
		player.draw(batch);
		mirror.draw(batch);
		light.update(mirror.getCenter(), mirror.angle);
	}
}
