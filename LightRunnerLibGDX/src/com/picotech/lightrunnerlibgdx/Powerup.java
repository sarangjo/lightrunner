package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Powerup extends Sprite2 {
	//public Texture aura;
	public double totalTime;
	public float a;
	
	public Powerup(Vector2 newPos)
	{
		super(newPos, 10, 10);
		velocity = new Vector2(-1, 0);
	}
	
	public void update(float deltaTime)
	{
		position.x += velocity.x;
		position.y += velocity.y;
		
		totalTime += deltaTime;
		
		// Sinusoidal function for "brightness"
		a = (float)(0.5f + 0.5f*Math.cos(totalTime/4.0));
	}
	
	public void draw(ShapeRenderer sr)
	{
		sr.begin(ShapeType.FilledRectangle);
		sr.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, a);
		sr.filledRect(position.x, position.y, 10, 10);
		sr.end();
	}
}
