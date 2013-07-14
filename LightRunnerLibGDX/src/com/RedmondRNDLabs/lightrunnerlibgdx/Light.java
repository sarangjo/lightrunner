package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the collection of all LightBeams on the screen.
 * @author Daniel Fang
 *
 */
public class Light {

	boolean isMenu;
	ArrayList<LightBeam> beams = new ArrayList<LightBeam>();
	
	/**
	 * Initializes two LightBeams.
	 * <ul>
	 * <li> The first is from the source to the mirror's source. </li>
	 * <li> The second is from the mirror's source to (temporarily) x=0, y=0. </li>
	 * 
	 * @param newOrigin		the source of the LightBeam at the edge of the screen.
	 * @param mirrorCenter	the Vector2 representing the center of the mirror.
	 */
	public Light(Vector2 newOrigin, Vector2 newDst){
		beams.add(new LightBeam(newOrigin, newDst)); // beam from source to player mirror
		beams.add(new LightBeam(newDst)); // reflected beam; in this case, the origin is the destination vector of the first beam
	}
	
	public Light(boolean isMenu){
		this.isMenu = isMenu;
		beams.add(new LightBeam(new Vector2(640, 720), new Vector2(640, 0)));
	}
	
	/**
	 * Calls the corresponding update() methods for LightBeam.
	 * 
	 * @param mirrorLocation	the center of the mirror
	 * @param mirrorAngle		the angle of the mirror
	 */
	public void update(Vector2 mirrorLocation, float mirrorAngle){
		if(isMenu){
			
		} else {
		beams.get(0).updateIncomingBeam(mirrorLocation, 20);
		beams.get(1).updateOutoingBeam(beams.get(0), mirrorAngle, 20, null);
		}
	}
	
	/**
	 * Draws the light beams.
	 * 
	 * @param sr	the ShapeRenderer that has already begun.
	 */
	public void draw(ShapeRenderer sr){
		for(LightBeam lb: beams){
			lb.draw(sr);
		}
	}
	
}
