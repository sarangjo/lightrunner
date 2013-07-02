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

		for (int j = 0; j < Assets.difficultySettings.length; j++) {
			for (int i = 0; i < touchEvents.size(); i++) {
				if (isInBounds(150 + 250 * j, 200, 250, 200, touchEvents.get(i))) {
					game.setScreen(new GameScreen(game, j + 1));
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clearScreen(Color.WHITE);
		g.drawString("Welcome to TrickPong!", 600, 400, paint);
		for (int i = 0; i < Assets.difficultySettings.length; i++) {
			g.drawImage(Assets.difficultySettings[i], 150 + 250 * i, 200);
		}
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