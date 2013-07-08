package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public void draw(SpriteBatch batch){
		// this will eventually be changed... the origin (for rotational purposes) 
		// should be the center of the player, not the center of the mirror .png.
		
		// the mirror.png should be CENTERED, so that when we draw the mirror a 
		// set distance away from the player, the mirror will rotate smoothly around the player position vector.
		
		batch.draw(texture, Position.x, Position.y, bounds.width/2, bounds.height/2, bounds.width, bounds.height, 1, 1,
				angle - 90, 0, 0, (int)bounds.width, (int)bounds.height, false, false);
	}

}
