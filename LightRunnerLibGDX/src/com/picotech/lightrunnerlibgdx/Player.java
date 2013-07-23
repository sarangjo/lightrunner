package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite2 {
	
	float speed = 5;
	float dstY;
	public static final int MAX_HEALTH = 100;
	int health;
	boolean alive = true;
	
	public Player(float x, float y, String asset) {
		super(x, y, 100, 100, asset);
		dstY = y;
		health = MAX_HEALTH;
	}

	public Player(Vector2 Position, String asset) {
		super(Position, 100, 100, asset);
		dstY = Position.y;
		health = MAX_HEALTH;
	}
	
	public void draw(SpriteBatch batch, float angle){
		batch.draw(texture, position.x, position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1,
				angle, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
	}
	
	public void update(){
		if(health <= 0){
			alive = false;
		}
		updateVertices();
		if(dstY > position.y + speed){
			position.y += speed;
		} else if (dstY < position.y - speed){
			position.y -= speed;
		}
	}
	public void follow(float newDstY){
		this.dstY = newDstY;
	}
}
