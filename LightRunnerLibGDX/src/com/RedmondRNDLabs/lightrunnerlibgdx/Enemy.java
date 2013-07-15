package com.RedmondRNDLabs.lightrunnerlibgdx;

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
	Vector2 vel;
	boolean alive;
	boolean losingHealth;

	public Enemy(Vector2 Position, int newW, int newH, String newAsset, int level) {
		super(Position, newW, newH, newAsset);
		float enemySpawning = (float)MathUtils.random(11 - level/3, 30);
		if(enemySpawning < 10 && enemySpawning > 3){
			type = Type.Fast;
		} else if (enemySpawning <= 3) {
			type = Type.Random;
		} else {
			type = Type.Normal;
		}
		if(type == Type.Normal){
			maxHealth = 50;
			vel = new Vector2(-1.0f, MathUtils.random(-.2f, .2f));
		} else if (type == Type.Fast){
			maxHealth = 25;
			vel = new Vector2(-7.5f, MathUtils.random(-.1f, .1f));
		} else if(type == Type.Random){
			maxHealth = 10;
			vel = new Vector2(-5.0f, 0);
		} else {
			maxHealth = 50;
		}
		alive = true;
		
		health = maxHealth;
	}

	/**
	 * Updates the enemy, moving it and checking if it is still alive.
	 */
	public void update() {
		losingHealth = false;
		if(type == Type.Random){
			vel.x += (float)Math.sin(Position.x / 100);
			vel.y += (float)Math.sin(Position.y / 25);
		}
		Position.x += vel.x;
		Position.y += vel.y;
		if (health <= 5 || Position.x + bounds.width < 0 || Position.y + bounds.height < 0 || Position.y - bounds.height > 720) {
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
			if(type == Type.Normal){
				sr.begin(ShapeType.FilledRectangle);
				sr.setColor((health - 5) / 5f, 0, 0, 1);
				sr.filledRect(Position.x, Position.y, bounds.width, bounds.height);
			} else if(type == Type.Fast) {
				sr.begin(ShapeType.FilledRectangle);
				sr.setColor(0, (health - 5) / 5f,  0 , 1);
				sr.filledRect(Position.x, Position.y, bounds.width, bounds.height);
			} else if(type == Type.Random) {
				sr.begin(ShapeType.FilledRectangle);
				sr.setColor(0, 0, (health - 5)/ 5f, 1);
				sr.filledRect(Position.x, Position.y, bounds.width, bounds.height);
			}
			if(losingHealth || health < maxHealth){
				sr.setColor(Color.WHITE);
				sr.filledRect(Position.x, Position.y + bounds.height + 2, ((health/(float)maxHealth) * bounds.width), 5);
			}
			sr.end();
		}
	}
}
