package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Sprite2 {
	Vector2 Position = new Vector2();
	Rectangle bounds = new Rectangle();
	Texture texture;
	
	public Sprite2(Vector2 Position, int newW, int newH)
	{
		this.Position = Position;
		this.bounds.width = newW;
		this.bounds.height = newH;
	}
	public Sprite2(float x, float y, int newW, int newH)
	{
		this(new Vector2(x, y), newW, newH);
	}
	
	public void loadContent(Texture newTexture)
	{
		texture = newTexture;
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(texture, Position.x, Position.y, bounds.width, bounds.height); 
	}
}
