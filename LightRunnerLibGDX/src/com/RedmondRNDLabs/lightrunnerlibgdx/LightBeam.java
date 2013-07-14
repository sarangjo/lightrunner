package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class LightBeam {
	int strength;
	// Origin and Destination locations.
	Vector2 origin;
	Vector2 dst;
	float angle;
	float beamLength = 1300;
	float[] beamVertices = new float[6];
	Polygon beamPolygon = new Polygon(beamVertices);
	ArrayList<Vector2> vectorPolygon = new ArrayList<Vector2>();
	
	boolean polygonInstantiated = false;
	
	/** 
	 * Default constructor with the destination being (0,0).
	 * 
	 * @param newOrigin	the origin of the beam
	 */
	public LightBeam(Vector2 newOrigin){
		origin = newOrigin;
		dst = new Vector2(0, 0);
		for(int i = 0; i < 3; i++){
			vectorPolygon.add(new Vector2(0, 0));
		}
	}
	
	/**
	 * Constructor that sets both origin and destination vectors.
	 *  
	 * @param newOrigin	the new origin vector2
	 * @param newDst	the new destination vector2
	 */
	public LightBeam(Vector2 newOrigin, Vector2 newDst){
		origin = newOrigin;
		dst = newDst;
		for(int i = 0; i < 3; i++){
			vectorPolygon.add(new Vector2(0, 0));
		}
	}
	
	/**
	 * Calculates the orientation, position, and size of the beam if it is an incoming one.
	 * The polygon points are predetermined to set the faux 'origin' of the beam to be "width" pixels wide. 
	 * 
	 * @param mirrorLocation	the location of the mirror
	 * @param width				the desired initial width of the beam
	 */
	public void updateIncomingBeam(Vector2 newDst, int width){
		dst = newDst;
		calculateAngle();
		
		// This is the algorithm for incoming beams coming from the top.
		// The x value of the first point of the polygon is offset left by 10 at the top edge.
		beamVertices[0] = origin.x - width/2;
		beamVertices[1] = origin.y;
		
		// The x value of the second point is offset right by 10 to create a triangle.
		beamVertices[2] = origin.x + width/2;
		beamVertices[3] = origin.y;
		
		beamVertices[4] = dst.x;
		beamVertices[5] = dst.y;
		
		beamPolygon = new Polygon(beamVertices);
	}
	
	/**
	 * Calculates the orientation, location, and size of the beam if it is an outgoing beam.
	 *  
	 * @param sourceBeam	the source beam
	 * @param mirrorAngle	the angle of the mirror
	 * @param lightBehavior	the type of the mirror, to determine the behavior of the beam
	 */
	public void updateOutoingBeam(LightBeam sourceBeam, float mirrorAngle, int width, Mirror.Type lightBehavior){
		origin = sourceBeam.dst;

		// calculates the angle of all reflecting beams depending on mirror type

		angle = (2 * mirrorAngle - sourceBeam.angle) * MathUtils.degreesToRadians;
		
		// trigonometry to calculate where the outgoing beam ends, whichh varies with the beamLength
		dst.x = origin.x + (float) (Math.cos(angle) * beamLength );
		dst.y = origin.y + (float) (Math.sin(angle) * beamLength );
		
		beamVertices[0] = origin.x;
		beamVertices[1] = origin.y;
		
		beamVertices[2] = dst.x;
		beamVertices[3] = dst.y - width / 2;
		
		beamVertices[4] = dst.x;
		beamVertices[5] = dst.y + width / 2;

		vectorPolygon.set(0, new Vector2(beamVertices[0], beamVertices[1]));
		vectorPolygon.set(1, new Vector2(beamVertices[2], beamVertices[3]));
		vectorPolygon.set(2, new Vector2(beamVertices[4], beamVertices[5]));

		beamPolygon = new Polygon(beamVertices);
	}

	/**
	 * Draws the LightBeam given a ShapeRenderer.
	 * 
	 * @param sr	the ShapeRenderer that will draw the LightBeam; should not be begun
	 */
	public void draw(ShapeRenderer sr){
		sr.begin(ShapeType.FilledTriangle);
		sr.setColor(Color.YELLOW);
		sr.filledTriangle(beamVertices[0], beamVertices[1], beamVertices[2], beamVertices[3], beamVertices[4], beamVertices[5]);
		sr.end();
		
	}
	
	/** 
	 * Sets the destination Vector to the location of the mirror
	 * 
	 * @param mirrorLocation the location of the mirror
	 */
	
	/** 
	 * Calculates the angle of the incoming beam, independent of mirror angle.
	 */
	public void calculateAngle(){ 
		angle = (float) (Math.atan((dst.y - origin.y) / (dst.x - origin.x)) * 180 / Math.PI);
	}
	
}
