package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Mirror extends Sprite2 {
	static enum Type {
		// mirror that creates prisms, etc
	}
	
	float angle = 0f;
	
	public Mirror(float x, float y, int newW, int newH, String asset) {
		super(x, y, newW, newH, asset);
	}
	
	public Mirror(Vector2 Position, String asset) {
		super(Position, 100, 100, asset);
	}
	
	public void setMirrorAngle(Vector2 src, Vector2 dst) {
		angle = (float) (Math.atan((dst.y - src.y) / (dst.x - src.x)) * 180 / Math.PI);
	}
	
	public void rotateAroundPlayer(Vector2 playerVector, float distance){
		setCenterX(playerVector.x + (float) (distance * Math.cos(angle * MathUtils.degreesToRadians)));
		setCenterY(playerVector.y + (float) (distance * Math.sin(angle * MathUtils.degreesToRadians)));
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture, Position.x, Position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1,
				angle, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
	}

}
