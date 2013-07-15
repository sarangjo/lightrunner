package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite2 {
	/**
	 * Represents the different types of Enemies.
	 */
	static enum Type {

	}

	int health;
	int maxHealth;
	Vector2 vel;
	boolean alive;
	boolean losingHealth;

	public Enemy(Vector2 Position, int newW, int newH, Vector2 newVel, String newAsset) {
		super(Position, newW, newH, newAsset);
		maxHealth = 50;
		alive = true;
		vel = newVel;
		health = maxHealth;
	}

	/**
	 * Updates the enemy, moving it and checking if it is still alive.
	 */
	public void update() {
		losingHealth = false;
		Position.x += vel.x;
		Position.y += vel.y;
		if (health <= 5 || Position.x < 0 || Position.y < 0 || Position.y > 720) {
			alive = false;
			GameScreen.died.play();
		}
		updateVertices();
	}

	/**
	 * Draws the Enemy as a filled rectangle. The color is a function of the
	 * health.
	 * 
	 * @param sr	the ShapeRenderer to use to draw the circle.
	 */
	public void draw(ShapeRenderer sr) {
		if (alive) {
			sr.begin(ShapeType.FilledRectangle);
			sr.setColor((health - 5) / 5f, 0, 0, 1);
			sr.filledRect(Position.x, Position.y, bounds.width, bounds.height);

			if(losingHealth || health < maxHealth){
				sr.setColor(Color.WHITE);
				sr.filledRect(Position.x, Position.y + bounds.height + 2, health - 5, 5);
			}
			sr.end();
		}
	}
}
