package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;

public class Input {

	/**
	 * Movement schemes.
	 */
	static enum Movement {
		DUALMOVE, MIRRORMOVE, PLAYERMOVE, REGIONMOVE
	}

	static enum Type {
		UP, DOWN, DRAG
	}

	static Movement ctrl;
	// The x and y values of the touch.
	public static int touchX, touchY;
	public static Vector2 touchDownPt = new Vector2(),
			touchDragPt = new Vector2(), touchUpPt = new Vector2();
	public static Vector2 dragDistance = new Vector2();

	static Vector2 mouseVector = new Vector2();

	public static void setMovement(Movement newCtrl) {
		ctrl = newCtrl;
	}

	/**
	 * Handles single/dragged touches.
	 * <p>
	 * 
	 * @param w
	 *            the current world
	 * @param width
	 *            width of the screen
	 * @param buttonHeight
	 *            height of the screen
	 * @param touchX
	 *            x-value of the touch
	 * @param touchY
	 *            y-value of the touch
	 */
	public static void update(World world, int touchX, int touchY) {
		if (Gdx.input.isTouched()) {
			mouseVector.x = touchX;
			mouseVector.y = GameScreen.height - touchY;
			if (GameScreen.state == GameState.PLAYING) {
				switch (ctrl) {
				case DUALMOVE:
					// STYLE 1: Mobile controls
					if (touchX < GameScreen.width / 6) {
						world.player.setCenterY(GameScreen.height - touchY);
					} else {
						// calculates and sets the mirror angle -- from the
						// touch point to the mirror position
						world.mirror.setMirrorAngle(world.mirror.getCenter(),
								mouseVector);
					}
					break;
				case MIRRORMOVE:
					// STYLE 2: Stationary controls
					world.mirror.setMirrorAngle(world.mirror.getCenter(),
							mouseVector);
					world.mirror
							.rotateAroundPlayer(
									world.player.getCenter(),
									((world.player.bounds.width / 2) + 10 + (world.light
											.getOutgoingBeam().isPrism ? 40 : 0)));

					break;
				case PLAYERMOVE:
					// STYLE 3: Stationary mirror, movable player
					world.player.setCenterY(mouseVector.y);

					break;
				case REGIONMOVE:
					// STYLE 4: Region around player governs movement, else
					// mirror movement
					if (GameScreen.height - touchY > world.player.getCenter().y
							- 200 * GameScreen.defS.y
							&& GameScreen.height - touchY < world.player
									.getCenter().y
									+ (200 * GameScreen.defS.y)
							&& touchX < GameScreen.width / 6) {
						world.player.follow(GameScreen.height - touchY
								- world.player.bounds.height / 2);
					} else if (!(world.player.inventory.size() > 0 && world.player.inventoryRects[0]
							.contains(touchX, GameScreen.height - touchY))) {
						world.mirror.setMirrorAngle(world.mirror.getCenter(),
								mouseVector);
					}
					world.mirror
							.rotateAroundPlayer(
									world.player.getCenter(),
									(world.player.bounds.width / 2)
											+ 2
											+ (world.light.getOutgoingBeam().isPrism ? 40
													: 0));

					break;
				}
			} else if (GameScreen.state == GameState.MENU
					&& world.menu.menuState == Menu.MenuState.MAIN) {
				// Sets the beam to pass through the touch.
				float X = GameScreen.height * (float)(GameScreen.width/2 - touchX) / ((float)(touchY));
				world.light.beams.get(1).updateIncomingBeam(
						new Vector2(GameScreen.width/2 - X, 0), true, world.player);
			}

		}
	}
}
