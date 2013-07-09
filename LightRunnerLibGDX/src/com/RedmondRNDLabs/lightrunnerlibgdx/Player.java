package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite2 {

	public Player(float x, float y, String asset) {
		super(x, y, 100, 100, asset);
	}

	public Player(Vector2 Position, String asset) {
		super(Position, 100, 100, asset);
	}
	
	public void draw(SpriteBatch batch, float angle){
		batch.draw(texture, Position.x, Position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1,
				angle, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
	}
}
