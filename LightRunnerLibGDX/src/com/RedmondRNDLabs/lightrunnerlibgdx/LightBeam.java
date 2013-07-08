package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LightBeam {
	int strength;
	Vector2 origin;
	Vector2 dst;
	float angle;
	float spread;
	float beamLength = 1300;
	float[] beamVertices = new float[6];
	Polygon beamPolygon = new Polygon(beamVertices);
	ArrayList<Vector2> vectorPolygon = new ArrayList<Vector2>();
	
	boolean polygonInstantiated = false;
	
	public LightBeam(Vector2 newOrigin){
		origin = newOrigin;
		dst = new Vector2(0, 0);
		for(int i = 0; i < 3; i++){
			vectorPolygon.add(new Vector2(0, 0));
		}
	}
	
	public LightBeam(Vector2 newOrigin, Vector2 newDst){
		origin = newOrigin;
		dst = newDst;
		for(int i = 0; i < 3; i++){
			vectorPolygon.add(new Vector2(0, 0));
		}
	}
	
	public void updateIncomingBeam(Vector2 mirrorLocation){
		followMirror(mirrorLocation);
		calculateAngle();
		
		beamVertices[0] = origin.x - 10;
		beamVertices[1] = origin.y;
		
		beamVertices[2] = origin.x + 10;
		beamVertices[3] = origin.y;
		
		beamVertices[4] = dst.x;
		beamVertices[5] = dst.y;
		
		beamPolygon = new Polygon(beamVertices);
	}
	
	public void updateOutoingBeam(LightBeam sourceBeam, float mirrorAngle, Mirror.Type lightBehavior){
		origin = sourceBeam.dst;

		// calculates the angle of all reflecting beams depending on mirror type

		angle = (2 * mirrorAngle - sourceBeam.angle) * MathUtils.degreesToRadians;
		dst.x = origin.x + (float) (Math.cos(angle) * beamLength );
		dst.y = origin.y + (float) (Math.sin(angle) * beamLength );
		
		beamVertices[0] = origin.x;
		beamVertices[1] = origin.y;
		
		beamVertices[2] = dst.x;
		beamVertices[3] = dst.y - 10;
		
		beamVertices[4] = dst.x;
		beamVertices[5] = dst.y + 10;

		vectorPolygon.set(0, new Vector2(beamVertices[0], beamVertices[1]));
		vectorPolygon.set(1, new Vector2(beamVertices[2], beamVertices[3]));
		vectorPolygon.set(2, new Vector2(beamVertices[4], beamVertices[5]));

		beamPolygon = new Polygon(beamVertices);
	}

	public void draw(ShapeRenderer sr){
		sr.begin(ShapeType.FilledTriangle);
		sr.setColor(Color.YELLOW);
		sr.filledTriangle(beamVertices[0], beamVertices[1], beamVertices[2], beamVertices[3], beamVertices[4], beamVertices[5]);
		sr.end();
		
	}
	
	public void followMirror(Vector2 location){
		dst = location;
	}
	
	public void calculateAngle(){ 
		// calculates the angle of the incoming beam (independent of mirror angle)
		angle = (float) (Math.atan((dst.y - origin.y) / (dst.x - origin.x)) * 180 / Math.PI);
	}
	
}
