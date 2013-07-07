package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Mirror extends Sprite2 {

	float angle = 0f;
	public Mirror(float x, float y, int newW, int newH, String asset) {
		super(x, y, newW, newH, asset);
		
	}
	
	public void setMirrorAngle(Vector2 src, Vector2 dst) {
		angle = (float) (Math.atan((dst.y - src.y) / (dst.x - src.x)) * 180 / Math.PI);
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture, Position.x, Position.y, bounds.width/2, bounds.height/2, bounds.width, bounds.height, 1, 1, angle - 90, 0, 0, (int)bounds.width, (int)bounds.height, false, false);
	}

}
