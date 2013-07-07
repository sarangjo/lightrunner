package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite2 {

	public Player(float x, float y, String asset) {
		super(x, y, 100, 100, asset);
	}

	public Player(Vector2 Position, String asset) {
		super(Position, 100, 100, asset);
	}
}
