package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite2{
	static enum Type {
		
	}
	
	int health;
	Vector2 vel;
	boolean alive = true;
	
	public Enemy(Vector2 Position, int newW, int newH, Vector2 newVel, String newAsset) {
		super(Position, newW, newH, newAsset);
		vel = newVel;
		health = (int) bounds.height;
	}
	
	public void update(){
		Position.x += vel.x;
		Position.y += vel.y;
		if(health <= 5){
			alive = false;
		}
		updateVertices();
		bounds.width = health;
		bounds.height = health;
	}
	
	public void draw(ShapeRenderer sr){
		sr.begin(ShapeType.FilledRectangle);
		sr.setColor((health - 5) / 5f, 0, 0, 1);
		sr.filledRect(Position.x, Position.y, bounds.width, bounds.height);
		sr.end();
	}
}
