package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Input {
	static enum Controls{
		
	}
	
	Controls ctrl;
	
	public Input(){
	}
	
	public Input(Input.Controls newCtrl){
		ctrl = newCtrl;
	}
	
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
