package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Light {
	
	ArrayList<LightBeam> beams = new ArrayList<LightBeam>();
	
	public Light(Vector2 newOrigin, Vector2 newDst){
		beams.add(new LightBeam(newOrigin, newDst)); // beam from source to player mirror
		beams.add(new LightBeam(newDst)); // reflected beam; in this case, the origin is the destination vector of the first beam
	}
	
	public void update(Vector2 mirrorLocation, float mirrorAngle){
		beams.get(0).updateIncomingBeam(mirrorLocation);
		beams.get(1).updateOutoingBeam(beams.get(0), mirrorAngle, null);
	}
	
	public void draw(ShapeRenderer sr){
		for(LightBeam lb: beams){
			lb.draw(sr);
		}
	}
	
}
