package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * The World class holds all of the players, enemies and environment objects. It
 * handles collisions and drawing methods, as well as loading content.
 */

public class DebugOverlay {

	BitmapFont bf;

	ArrayList<Rectangle> debugOptions;
	boolean[] selectedButtons;
	boolean nothingSelected = true;
	Rectangle switchMirror = new Rectangle(1100, 600, 100, 25);
	/**
	 * There are two types of worlds, the menu world and the in-game world. The
	 * behavior of the light depends on whether the game is in the menu or
	 * playing state.
	 * 
	 * @param isMenu
	 */
	public DebugOverlay() {
		debugOptions = new ArrayList<Rectangle>();
		debugOptions.add(switchMirror);
		selectedButtons = new boolean[debugOptions.size()];
		
	}
	
	/**
	 * Loads all the content of the World.
	 */
	public void loadContent() {

		bf = new BitmapFont();
		bf.setColor(Color.WHITE);
	}

	/**
	 * Updates the entire World. Includes light, enemy movement, and enemy
	 * destruction. Also updates the time functions for frame rate-independent
	 * functions deltaTime and totalTime (which are all in seconds).
	 */
	public void update() {
		if(switchMirror.contains(Input.touchX, Input.touchY)){
			selectedButtons[0] = true;
			nothingSelected = false;
		} else {
			nothingSelected = true;
		}
	}
	public void resetButtons(){
		for(int button = 0; button < selectedButtons.length; button++){
			if(!debugOptions.get(button).contains(Input.touchX, Input.touchY))
				selectedButtons[button] = false;
			Input.touchX = 0;
			Input.touchY = 0;
		}
	}
	/**
	 * Handles all the power-up logic.
	 */
	public void draw(SpriteBatch batch, ShapeRenderer sr) {

			sr.begin(ShapeType.FilledRectangle);
			for(int rect = 0; rect < debugOptions.size(); rect++){
				if(selectedButtons[rect]){
					sr.setColor(Color.WHITE);
				} else {
					sr.setColor(Color.GRAY);
				}
				sr.filledRect(debugOptions.get(rect).x, debugOptions.get(rect).y, debugOptions.get(rect).width, debugOptions.get(rect).height);
			}
			sr.end();

			
			batch.begin();
			// Text drawing
			bf.setColor(Color.WHITE);
			bf.draw(batch, "Switch mirrors!", switchMirror.x, switchMirror.y + 20);

			// testing
			batch.end();
			
		
	}
}
