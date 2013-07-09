package com.RedmondRNDLabs.lightrunnerlibgdx;

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
	public void update(World w, int width, int height, int touchX, int touchY){
		if(Gdx.input.isTouched()){
				if ( touchX < width / 6){
					w.player.setCenterY(height - touchY);
					w.mirror.setCenterY(height - touchY);
				} else {
					// calculates and sets the mirror angle -- from the touch point to the mirror position
					w.mirror.setMirrorAngle(w.mirror.getCenter(), new Vector2(touchX, height - touchY));
				}
			
		}
	}
}
