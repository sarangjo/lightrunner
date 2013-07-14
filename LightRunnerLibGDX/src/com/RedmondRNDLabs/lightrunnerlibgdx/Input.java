package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.RedmondRNDLabs.lightrunnerlibgdx.GameScreen.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Input {
	static enum ControlScheme{
		
	}
	
	ControlScheme ctrl;
	
	public Input(){
	}
	
	public Input(Input.ControlScheme newCtrl){
		ctrl = newCtrl;
	}
	
	/**
	 * Responds to all touches.
	 * <p>
	 * Current Configuration:
	 * <ul>
	 * <li> If the touch is in the left sixth of the screen, the player and mirror move there. </li>
	 * <li> If the touch is in the right 5/6th of the screen, the mirror directs itself to point to the touch.
	 * This uses the function setMirrorAngle() to set the angle from the touch to the mirror position. </li>
	 * 
	 * TO CHANGE: Dynamic screen division instead of the 1/6 - 5/6 method. Uses all "empty space" around the character to control the mirror
	 * </ul>
	 * 
	 * @param w
	 *            the current world
	 * @param width
	 *            width of the screen
	 * @param height
	 *            height of the screen
	 * @param touchX
	 *            x-value of the touch
	 * @param touchY
	 *            y-value of the touch
	 */
	public void update(World w, int width, int height, int touchX, int touchY, GameScreen.GameState state){
		if(Gdx.input.isTouched()){
			if(state == GameState.Playing){
				if ( touchX < width / 6){
					//w.player.setCenterY(height - touchY);
				} else {
					// calculates and sets the mirror angle -- from the touch point to the mirror position
					//w.mirror.setMirrorAngle(w.mirror.getCenter(), new Vector2(touchX, height - touchY));
				}
				
				/**
				 * trying out stationary controls
				 */
				w.mirror.setMirrorAngle(w.mirror.getCenter(), new Vector2(touchX, height - touchY));
				w.mirror.rotateAroundPlayer(w.player.getCenter(), (w.player.bounds.width / 2) + 10);
			} else if (state == GameState.Menu){
				w.light.beams.get(0).updateIncomingBeam(new Vector2(touchX, 0), 20);
			}
			
		}
	}
}
