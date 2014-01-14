package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Mirror extends Sprite2 {
	public float distance;
	
	static enum Type {
		FLAT, FOCUS, CONVEX, PRISM
	}
	
	public final static Type START_TYPE = Type.FOCUS; 
	//Type type = Type.FLAT;
	Type type = START_TYPE;
	float angle = 0f;
	
	public Mirror(float x, float y, int newW, int newH, String asset) {
		super(x, y, newW, newH, asset);
	}
	
	public Mirror(Vector2 Position, String asset) {
		super(Position, (int)(100 * GameScreen.defS.x), (int)(100 * GameScreen.defS.y), asset);
	}
	
	public void setMirrorAngle(Vector2 src, Vector2 dst) {
		angle = (float) (Math.atan((dst.y - src.y) / (dst.x - src.x)) * 180 / Math.PI);
	}
	public void setMirrorDistance(float newD){
		distance = newD;
	}
	
	public void setType(Type type, String asset){
		this.type = type;
		this.asset = asset;
		loadContent();
	}
	
	public void rotateAroundPlayer(Vector2 playerVector, float newD){
		setMirrorDistance(newD);
		setCenterX(playerVector.x + (float) (newD * Math.cos(angle * MathUtils.degreesToRadians)));
		setCenterY(playerVector.y + (float) (newD * Math.sin(angle * MathUtils.degreesToRadians)));
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture, position.x, position.y, bounds.width / 2, bounds.height / 2, bounds.width, bounds.height, 1, 1,
				angle, 0, 0, (int) bounds.width, (int) bounds.height, false, false);
	}

}
