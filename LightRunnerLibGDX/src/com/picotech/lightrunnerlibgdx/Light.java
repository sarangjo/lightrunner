package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.Mirror.Type;

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
	public static int L_WIDTH = 20;

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
		setLWidth();
		// beam from source to player mirror
		beams.add(new LightBeam(sourceOrigin, mirrorCenter, L_WIDTH,
				LightBeam.Type.INCOMING));
		// reflected beam; in this case, the origin is the destination vector of
		// the first beam
		beams.add(new LightBeam(mirrorCenter, L_WIDTH, LightBeam.Type.OUTGOING));
	}

	/**
	 * The constructor used when it is currently the menu.
	 */
	public Light(boolean isMenu) {
		setLWidth();
		this.isMenu = isMenu;
		beams.add(new LightBeam(new Vector2(0, 0), new Vector2(0, 0), L_WIDTH,
				LightBeam.Type.INCOMING));
		beams.add(new LightBeam(new Vector2(640, 720), new Vector2(640, 0),
				L_WIDTH, LightBeam.Type.OUTGOING));
	}

	/**
	 * Sets the width of the light.
	 */
	public void setLWidth() {
		L_WIDTH = (int) (20 * GameScreen.defS.x);
	}

	/**
	 * Returns the incoming beam.
	 * 
	 * @return the incoming beam
	 */
	public LightBeam getIncomingBeam() {
		return beams.get(0);
	}

	/**
	 * Returns the outgoing beam.
	 * 
	 * @return the outgoing beam
	 */
	public LightBeam getOutgoingBeam() {
		return beams.get(1);
	}

	/**
	 * Calls the corresponding update() methods for LightBeam.
	 * 
	 * @param mirror
	 *            the mirror
	 * @param player
	 *            the player
	 */
	public void update(Mirror mirror, Player player) {
		if (!isMenu) {
			if (mirror.type == Type.CONVEX) {
				if (beams.size() < 4)
					beams.add(new LightBeam(new Vector2(GameScreen.height * 8/9f, GameScreen.height), new Vector2(
							GameScreen.height * 8/9f, 0), L_WIDTH, LightBeam.Type.OUTGOING));
			}
			beams.get(0).updateIncomingBeam(mirror.getCenter(), false, player);
			for (int beam = 1; beam < beams.size(); beam++) {
				beams.get(beam).updateOutgoingBeam(beams.get(0), mirror.angle,
						mirror.type, beam);
			}
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
