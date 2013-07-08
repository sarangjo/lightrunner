package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite2{
	static enum Type {
		
	}
	
	int health;
	Vector2 vel;
	boolean alive = true;
	
	public Enemy(Vector2 Position, int newW, int newH, String newAsset) {
		super(Position, newW, newH, newAsset);
		health = 5;
	}
	
	public void update(){
		if(health <= 0){
			alive = false;
		}
	}
}
