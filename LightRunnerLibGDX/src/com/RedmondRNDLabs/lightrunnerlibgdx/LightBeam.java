package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.math.Vector2;

public class LightBeam {
	Vector2 origin;
	Vector2 dst;
	float angle;
	float spread;
	
	public LightBeam(Vector2 newOrigin){
		origin = newOrigin;
	}
	
	public LightBeam(Vector2 newOrigin, Vector2 newDst){
		origin = newOrigin;
		dst = newDst;
	}
	
	public void update(){
		
	}
	
	public void followPlayer(Vector2 location){
		dst = location;
	}
	
	public void calculateAngle(float mirrorAngle){ 
		// calculates the angle of the first beam
	}
	
	public void calculateAngle(LightBeam sourceBeam, Mirror.Type lightBehavior){ 
		// calculates the angle of all reflecting beams depending on mirror type
	}
}
