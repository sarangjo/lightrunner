package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite2 {
	/**
	 * Represents the different types of Enemies.
	 */
	static enum Type {
		Normal, Fast, Random
	}
	
	Type type = Type.Normal;
	int health;
	int maxHealth;
	boolean alive;
	boolean losingHealth;

	public Enemy(Vector2 Position, int newW, int newH, int level) {
		super(Position, newW, newH);
		float enemySpawning = (float)MathUtils.random(11 - level/3, 30);
		if(enemySpawning < 10 && enemySpawning > 3){
			type = Type.Fast;
			maxHealth = 25;
			velocity = new Vector2(-7.5f, MathUtils.random(-.1f, .1f));
		} else if (enemySpawning <= 3) {
			type = Type.Random;
			maxHealth = 10;
			velocity = new Vector2(-5.0f, 0);
		} else {
			type = Type.Normal;
			maxHealth = 50;
			velocity = new Vector2(-1.0f, MathUtils.random(-.2f, .2f));
		}
		
		alive = true;
		health = maxHealth;
	}

	/**
	 * Updates the enemy, moving it and checking if it is still alive.
	 */
	public void update() {
		losingHealth = false;
		
		// played around with some sinusoidal functions for the random blocks
		if(type == Type.Random){
			velocity.x += (float)Math.sin(position.x / 100);
			velocity.y += (float)Math.sin(position.y / 25);
		}
		
		position.x += velocity.x;
		position.y += velocity.y;
		if (health <= 5 || position.x + bounds.width < 0 || position.y + bounds.height < 0 || position.y - bounds.height > 720) {
			alive = false;
			Assets.died.play();
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
			if(type == Type.Normal){
				sr.setColor((health - 5) / 5f, 0, 0, 1);
				sr.filledRect(position.x, position.y, bounds.width, bounds.height);
			} else if(type == Type.Fast) {
				sr.setColor(0, (health - 5) / 5f,  0 , 1);
				sr.filledRect(position.x, position.y, bounds.width, bounds.height);
			} else if(type == Type.Random) {
				sr.setColor(0, 0, (health - 5) / 5f, 1);
				sr.filledRect(position.x, position.y, bounds.width, bounds.height);
			}
			if(losingHealth || health < maxHealth){
				sr.setColor(Color.WHITE);
				sr.filledRect(position.x, position.y + bounds.height + 2, ((health/(float)maxHealth) * bounds.width), 5);
			}
			sr.end();
		}
	}
}
