package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.math.Vector2;

public class Magnet extends Sprite2{

	float pull;
	Vector2 velocity;
	
	public Magnet(Vector2 Position, int newW, int newH, String newAsset, float pullStrength) {
		super(Position, newW, newH, newAsset);
		pull = pullStrength;
		velocity = new Vector2(-1f, 0);
	}
	
	public void update(){
		position.x += velocity.x;
		position.y += velocity.y;
	}
	
	public Vector2 getPull(Vector2 objectPosition){
		//float distance = objectPosition.dst(getCenter());
		return new Vector2(((position.x - objectPosition.x ) * pull), ((position.y - objectPosition.y) * pull));
	}
}
