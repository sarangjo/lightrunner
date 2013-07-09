package com.RedmondRNDLabs.lightRunner;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.RedmondRNDLabs.framework.Game;
import com.RedmondRNDLabs.framework.Graphics;
import com.RedmondRNDLabs.framework.Graphics.ImageFormat;
import com.RedmondRNDLabs.framework.Screen;
import com.RedmondRNDLabs.framework.Sound;

public class LoadingScreen extends Screen {
	
	public LoadingScreen(Game game) {
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
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clearScreen(Color.WHITE);
		g.drawString("Warming up paddles...", 600, 400, paint);
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
