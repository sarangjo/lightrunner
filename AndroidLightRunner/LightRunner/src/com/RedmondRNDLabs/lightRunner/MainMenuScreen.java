package com.RedmondRNDLabs.lightRunner;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.RedmondRNDLabs.framework.Game;
import com.RedmondRNDLabs.framework.Graphics;
import com.RedmondRNDLabs.framework.Graphics.ImageFormat;
import com.RedmondRNDLabs.framework.Image;
import com.RedmondRNDLabs.framework.Input.TouchEvent;
import com.RedmondRNDLabs.framework.Screen;

public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) {
		super(game);

		paint = new Paint();
		paint.setTextSize(36);
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clearScreen(Color.WHITE);
		g.drawString("Welcome to LightRunner!", 600, 400, paint);
	}

	public boolean isInBounds(int x, int y, int width, int height, TouchEvent e) {
		// This method is now more like a rectangle: x, y, width height
		return (e.x >= x && e.x <= x + width - 1)
				&& (e.y >= y && e.y <= y + height - 1);
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