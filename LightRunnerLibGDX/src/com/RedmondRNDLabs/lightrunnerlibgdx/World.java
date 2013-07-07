package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player p1;
	Mirror m1;
	
	public World(){
		p1 = new Player(new Vector2(0, 0), "characterDirection0.png");
		m1 = new Mirror(0, 0, 100, 100, "characterDirection0.png");
	}
	
	public void loadContent()
	{
		p1.loadContent();
		m1.loadContent();
	}
	
	public void draw(SpriteBatch batch)
	{
		p1.draw(batch);
		m1.draw(batch);
	}
}
