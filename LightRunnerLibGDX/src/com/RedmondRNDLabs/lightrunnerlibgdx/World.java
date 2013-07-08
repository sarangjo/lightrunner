package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player player;
	Mirror mirror;
	
	public World(){
		player = new Player(new Vector2(0, 0), "characterDirection0.png");
		mirror = new Mirror(0, 0, 100, 100, "characterDirection0.png");
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
	}
}
