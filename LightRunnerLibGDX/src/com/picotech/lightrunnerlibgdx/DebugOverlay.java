package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DebugOverlay {

	BitmapFont bf;

	ArrayList<Rectangle> debugOptions;
	boolean[] selectedButtons;
	boolean nothingSelected = true;
	Rectangle switchMirror = new Rectangle(1100, 600, 100, 25);
	Rectangle spawnMagnet = new Rectangle(1100, 550, 100, 25);
	Rectangle spawnPowerup = new Rectangle(1100, 500, 100, 25);
	Rectangle killPlayer = new Rectangle(1100, 650, 100, 25);

	public DebugOverlay() {
		debugOptions = new ArrayList<Rectangle>();
		debugOptions.add(switchMirror);
		debugOptions.add(spawnMagnet);
		debugOptions.add(spawnPowerup);
		debugOptions.add(killPlayer);
		selectedButtons = new boolean[debugOptions.size()];
	}

	public void loadContent() {
		bf = new BitmapFont();
		bf.setColor(Color.WHITE);
	}

	public void update() {
		for (int button = 0; button < debugOptions.size(); button++) {
			if (debugOptions.get(button).contains(Input.touchX, Input.touchY))
				selectedButtons[button] = true;
		}
		nothingSelected = true;
		for (boolean b : selectedButtons) {
			if (b)
				nothingSelected = false;
		}
	}

	public void resetButtons() {
		for (int button = 0; button < selectedButtons.length; button++) {
			if (!debugOptions.get(button).contains(Input.touchX, Input.touchY))
				selectedButtons[button] = false;
			Input.touchX = -1;
			Input.touchY = -1;
		}
	}

	public void draw(SpriteBatch batch, ShapeRenderer sr) {

		sr.begin(ShapeType.Filled);
		for (int rect = 0; rect < debugOptions.size(); rect++) {
			if (selectedButtons[rect])
				sr.setColor(Color.WHITE);
			else
				sr.setColor(Color.GRAY);
			sr.rect(debugOptions.get(rect).x, debugOptions.get(rect).y,
					debugOptions.get(rect).width, debugOptions.get(rect).height);
		}
		sr.end();

		// Text drawing
		batch.begin();
		bf.setColor(Color.WHITE);
		bf.draw(batch, "Switch mirrors!", switchMirror.x, switchMirror.y + 20);
		bf.draw(batch, "Spawn magnet!", spawnMagnet.x, spawnMagnet.y + 20);
		bf.draw(batch, "Spawn Powerup!", spawnPowerup.x, spawnPowerup.y + 20);
		bf.draw(batch, "KILL", killPlayer.x, killPlayer.y + 20);
		batch.end();

	}
}
