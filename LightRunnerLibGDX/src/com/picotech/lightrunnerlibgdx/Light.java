package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the collection of all LightBeams on the screen.
 * 
 * @author Daniel Fang
 * 
 */
public class Light {

	boolean isMenu;
	/**
	 * An ArrayList representing the incoming and outgoing beams of light. <br>
	 * [0] is the incoming beam and [1] is the outgoing beam.
	 */
	ArrayList<LightBeam> beams = new ArrayList<LightBeam>();

	/**
	 * Initializes two LightBeams.
	 * <ul>
	 * <li>The first is from the source to the mirror's source.</li>
	 * <li>The second is from the mirror's source to (temporarily) x=0, y=0.</li>
	 * 
	 * @param sourceOrigin
	 *            the source of the LightBeam at the edge of the screen.
	 * @param mirrorCenter
	 *            the Vector2 representing the center of the mirror.
	 */
	public Light(Vector2 sourceOrigin, Vector2 mirrorCenter) {
		beams.add(new LightBeam(sourceOrigin, mirrorCenter, 20)); // beam from
																	// source to
		// player mirror
		beams.add(new LightBeam(mirrorCenter, 20)); // reflected beam; in this
													// case, the
		// origin is the destination vector
		// of the first beam
	}

	public Light(boolean isMenu) {
		this.isMenu = isMenu;
		beams.add(new LightBeam(new Vector2(0, 0), new Vector2(0, 0), 20));
		beams.add(new LightBeam(new Vector2(640, 720), new Vector2(640, 0), 20));
	}

	public LightBeam getIncomingBeam() {
		return beams.get(0);
	}

	public LightBeam getOutgoingBeam() {
		return beams.get(1);
	}

	/**
	 * Calls the corresponding update() methods for LightBeam.
	 * 
	 * @param mirrorLocation
	 *            the center of the mirror
	 * @param mirrorAngle
	 *            the angle of the mirror
	 */
	public void update(Vector2 mirrorLocation, float mirrorAngle) {
		if (!isMenu) {
			beams.get(0).updateIncomingBeam(mirrorLocation, false);
			beams.get(1).updateOutgoingBeam(beams.get(0), mirrorAngle, null);
		}
	}

	/**
	 * Draws the light beams.
	 * 
	 * @param sr
	 *            the ShapeRenderer that has already begun.
	 */
	public void draw(ShapeRenderer sr) {
		for (LightBeam lb : beams) {
			lb.draw(sr);
		}
	}

}
