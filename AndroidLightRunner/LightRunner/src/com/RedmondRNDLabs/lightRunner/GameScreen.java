package com.RedmondRNDLabs.lightRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.renderscript.Font.Style;

import com.RedmondRNDLabs.framework.Game;
import com.RedmondRNDLabs.framework.Graphics;
import com.RedmondRNDLabs.framework.Input.TouchEvent;
import com.RedmondRNDLabs.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}
	
	GameState state = GameState.Ready;
	int totalTime = 0;
	
	public GameScreen(Game game, int difficulty) {
		super(game);

		paint = new Paint();
		paint.setTextSize(36);
		paint.setTextAlign(Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		if (touchEvents.size() > 0) { 
			for (int touches = 0; touches < touchEvents.size(); touches++) { // issues with multi-touch and "dragging"; made this into a for loop
				TouchEvent touch = touchEvents.get(touches);
				if (touch.type == TouchEvent.TOUCH_UP) {

					// WHEN THE GAME IS BEING PLAYED
					if (state == GameState.Running) {
						
						if (isInBounds(0, 0, 120, 36, touch))
							state = GameState.Paused;
							
					}
					// WHEN THE GAME IS PAUSED
					else if (state == GameState.Paused) {
						state = GameState.Running;
					}
				}
				if((touch.type == TouchEvent.TOUCH_DRAGGED ||  touch.type == TouchEvent.TOUCH_DOWN ) && state == GameState.Running){
					
				}
			}
		}
		
		// all update calls that are touch-independent
		if (state == GameState.Running){
			totalTime += deltaTime;			
			
		}
		
		
	}
	

	public boolean isInBounds(int x, int y, int width, int height, TouchEvent e) {
		// This method is now more like a rectangle: x, y, width height
		return (e.x >= x && e.x <= x + width - 1) && (e.y >= y && e.y <= y + height - 1);
	}

	@Override
	public void paint(float deltaTime) {
		
		Graphics g = game.getGraphics();
		g.clearScreen(Color.WHITE);
		if (state == GameState.Ready) {
		}
		if (state == GameState.Running) {
			g.drawString("Running", 600, 400, paint);
		}
		if (state == GameState.Paused) {
			g.drawString("Paused", 600, 400, paint);
		}
		if (state == GameState.GameOver) {
		}
		
		
		// pause button
		g.drawRect(0, 0, 120, 36, Color.GRAY);
		g.drawString("Pause", 5, 30, paint);
		
		// add balls button
		g.drawRect(1160, 0, 120, 36, Color.GRAY);
		g.drawString("+1", 1180, 30, paint);
		
		
		
		g.drawString("Total time: " + totalTime, 500, 30, paint);
	}

	private void nullify() {
		paint = null;
		System.gc();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void backButton() {
	}
}