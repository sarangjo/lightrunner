package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Powerup extends Sprite2 {
	public double totalTime;
	public float a;

	public double timeActive = 0;

	public enum Type {
		ONEHITKO, PRISMPOWERUP, ENEMYSLOW, CLEARSCREEN, SPAWNSTOP, SPAWNMAGNET
	}

	public static final int LM_WIDTH = 50;
	public static final int P_WIDTH = 700;

	// Properties
	public float timeOfEffect;
	public Type type;
	public boolean isActive = false;
	public boolean isOver = false;

	public Aura aura;
	public boolean isAura = false;

	public Powerup(Vector2 newPos, Type newType) {
		super(newPos, 10, 10, "Powerups\\" + newType + ".png");
		type = newType;
		velocity = new Vector2(-1, 0);
		timeOfEffect = World.puhm.get(type);
		aura = new Aura(newPos);
	}

	public void update(float deltaTime) {
		if (!isOver) {
			position.x += velocity.x;
			position.y += velocity.y;

			totalTime += deltaTime;

			// Sinusoidal function for transparency
			a = (float) (0.5f + 0.5f * Math.cos(totalTime * 10.0));

			if (isActive) {
				timeActive += deltaTime;
			}
		}
		if (isAura) {
			if (aura.scale <= Aura.SCALE1)
				aura.update();
		}
	}

	public void end() {
		isOver = true;
		isActive = false;
		position = new Vector2(0, 0);
	}

	@Override
	public void loadContent() {
		aura.loadContent();
		texture = new Texture(Gdx.files.internal(asset));
		bounds.width = texture.getWidth();
		bounds.height = texture.getHeight();
		updateVertices();
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		if (!isActive && !isOver) {
			batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, a);
			batch.draw(texture, position.x, position.y);
			batch.setColor(Color.WHITE);
		}
		if (isAura)
			if (aura.scale < Aura.SCALE1)
				aura.draw(batch);

		batch.end();
	}
}
