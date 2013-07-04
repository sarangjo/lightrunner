package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite2 {

	public Player(float x, float y) {
		super(x, y, 100, 100);
	}

	public Player(Vector2 Position) {
		super(Position, 100, 100);
	}
}
