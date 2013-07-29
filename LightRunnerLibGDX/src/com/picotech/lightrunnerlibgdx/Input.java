package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;

public class Input {
	GameScreen.Movement ctrl;
	public static int touchX, touchY;

	public Input() {
	}

	public Input(GameScreen.Movement newCtrl) {
		ctrl = newCtrl;
	}

	/**
	 * Responds to all touches.
	 * <p>
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
	public void update(World w, int width, int height, int touchX, int touchY,
			GameScreen.GameState state) {
		if (Gdx.input.isTouched()) {
			if (state == GameState.PLAYING) {
				switch (ctrl) {
				case DUALMOVE:
					// STYLE 1: Mobile controls
					if (touchX < width / 6) {
						w.player.setCenterY(height - touchY);
					} else {
						// calculates and sets the mirror angle -- from the
						// touch point to the mirror position
						w.mirror.setMirrorAngle(w.mirror.getCenter(),
								new Vector2(touchX, height - touchY));
					}
					break;
				case MIRRORMOVE:
					// STYLE 2: Stationary controls
					w.mirror.setMirrorAngle(w.mirror.getCenter(), new Vector2(
							touchX, height - touchY));
					w.mirror.rotateAroundPlayer(
							w.player.getCenter(),
							(w.player.bounds.width / 2)
									+ 10
									+ (w.light.getOutgoingBeam().isPrism ? 40
											: 0));

					break;
				case PLAYERMOVE:
					// STYLE 3: Stationary mirror, movable player
					w.player.setCenterY(height - touchY);

					break;
				case REGIONMOVE:
					// STYLE 4: Region around player governs movement, else
					// mirror movement
					if (height - touchY > w.player.getCenter().y - 200
							&& height - touchY < w.player.getCenter().y + 200
							&& touchX < width / 6) {
						w.player.follow(height - touchY
								- w.player.bounds.height / 2);
					} else {
						w.mirror.setMirrorAngle(w.mirror.getCenter(),
								new Vector2(touchX, height - touchY));
					}
					w.mirror.rotateAroundPlayer(
							w.player.getCenter(),
							(w.player.bounds.width / 2)
									+ 2
									+ (w.light.getOutgoingBeam().isPrism ? 40
											: 0));

					break;
				}
			} else if (state == GameState.MENU) {
				// Sets the beam to pass through the touch.
				float X = (720 * (640 - touchX) / (/* 720 - */touchY));
				w.light.beams.get(1).updateIncomingBeam(
						new Vector2(640 - X, 0), true, w.player);
			}

		}
	}
}
