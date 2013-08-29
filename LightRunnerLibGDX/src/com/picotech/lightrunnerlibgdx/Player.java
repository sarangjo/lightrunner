package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite2 {
	
	float speed = 5;
	float dstY;
	public static final int MAX_INVENTORY = 1000;
	public static final int MAX_HEALTH = 100;
	ArrayList<Powerup> inventory;
	Rectangle[] inventoryRects = new Rectangle[5];
	int health;
	boolean alive = true;
	
	public Player(float x, float y, String asset) {
		super(x, y, 100, 100, asset);
		dstY = y;
		health = MAX_HEALTH;
		inventory = new ArrayList<Powerup>();
		inventoryRects[0] = new Rectangle(300, 50, 120, 120);
		for(int rect = 1; rect < inventoryRects.length; rect++)
			inventoryRects[rect] = new Rectangle(340 + (100 * rect), 50, 80, 80);
	}

	public Player(Vector2 Position, String asset) {
		super(Position, 100, 100, asset);
		dstY = Position.y;
		health = MAX_HEALTH;
		inventory = new ArrayList<Powerup>();
		inventoryRects[0] = new Rectangle(350, 50, 120, 120);
		for(int rect = 1; rect < inventoryRects.length; rect++)
			inventoryRects[rect] = new Rectangle(400 + (100 * rect), 50, 80, 80);
	}
	
	public void draw(SpriteBatch batch, float angle){
		batch.draw(texture, position.x, position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1,
				angle, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
	}
	
	public void addPowerup(Powerup p){
		// This is for limiting the number of powerups
		/*if(inventory.size() < 5) {
			inventory.add(p);
		} else {
			inventory.add(0, p);
			inventory.remove(5);
		}*/
		inventory.add(p);
	}

	public void drawInventory(SpriteBatch batch) {
		for (int rect = 0; rect < (inventory.size() < 5 ? inventory.size() : 5); rect++) {
			batch.begin();
			batch.draw(Assets.powerupBox, inventoryRects[rect].x,
					inventoryRects[rect].y, inventoryRects[rect].width,
					inventoryRects[rect].height);
			batch.end();
			if (inventory.size() > 0) {
				batch.begin();
				batch.draw(
						inventory.get(rect).texture,
						inventoryRects[rect].x
								+ (inventoryRects[rect].width - inventory
										.get(rect).boundingRect.width) / 2,
						inventoryRects[rect].y
								+ (inventoryRects[rect].height - inventory
										.get(rect).boundingRect.height) / 2,
						inventory.get(rect).texture.getWidth(),
						inventory.get(rect).texture.getHeight());
				batch.end();
			}
		}
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
