package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
// put all the players, enemies, and environment objects in here
	Player p1;
	
	public World(){
		p1 = new Player(new Vector2(0, 0));
		
	}
	
	public void loadContent(Texture pTexture)
	{
		p1.loadContent(pTexture);
		
	}
	
	public void draw(SpriteBatch batch)
	{
		p1.draw(batch);
	}
}
