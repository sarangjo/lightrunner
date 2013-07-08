package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LightBeam {
	Vector2 origin;
	Vector2 dst;
	float angle;
	float spread;
	float beamLength = 1000;
	
	public LightBeam(Vector2 newOrigin){
		origin = newOrigin;
		dst = new Vector2(0, 0);
	}
	
	public LightBeam(Vector2 newOrigin, Vector2 newDst){
		origin = newOrigin;
		dst = newDst;
	}
	
	public void updateIncomingBeam(Vector2 mirrorLocation){
		followMirror(mirrorLocation);
		calculateAngle();
	}
	
	public void updateOutoingBeam(LightBeam sourceBeam, float mirrorAngle, Mirror.Type lightBehavior){
		origin = sourceBeam.dst;

		// calculates the angle of all reflecting beams depending on mirror type

		angle = (2 * mirrorAngle - sourceBeam.angle) * MathUtils.degreesToRadians;
		dst.x = origin.x + (float) (Math.cos(angle) * beamLength );
		dst.y = origin.y + (float) (Math.sin(angle) * beamLength );
	}

	public void draw(ShapeRenderer sr){
		sr.setColor(Color.RED);
		sr.line(origin.x, origin.y, dst.x, dst.y);
	}
	
	public void followMirror(Vector2 location){
		dst = location;
	}
	
	public void calculateAngle(){ 
		// calculates the angle of the incoming beam (independent of mirror angle)
		angle = (float) (Math.atan((dst.y - origin.y) / (dst.x - origin.x)) * 180 / Math.PI);
	}
	
}
