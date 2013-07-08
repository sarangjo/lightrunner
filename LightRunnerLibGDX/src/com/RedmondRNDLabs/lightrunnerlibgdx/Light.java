package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;

public class Light {
	
	ArrayList<LightBeam> beams = new ArrayList<LightBeam>();
	
	public Light(Vector2 newOrigin, Vector2 newDst){
		beams.add(new LightBeam(newOrigin, newDst)); // beam from source to player mirror
		beams.add(new LightBeam(newDst)); // reflected beam; in this case, the origin is the destination vector of the first beam
	}
	
	public void update(Vector2 playerLocation, float mirrorAngle){
		beams.get(0).followPlayer(playerLocation);
		beams.get(0).calculateAngle(mirrorAngle);
	}
	
	public void draw(){
		for(LightBeam lb: beams){
			// draw beams from source to destination vectors with a certain spread
		}
	}
	
}
