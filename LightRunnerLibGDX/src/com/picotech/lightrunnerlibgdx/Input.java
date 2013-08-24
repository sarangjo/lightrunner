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
	
	Movement ctrl;
	// The x and y values of the touch.
	public static int touchX, touchY; 
	
	public Input(Movement newCtrl) {
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
	 * @param height
	 *            height of the screen
	 * @param touchX
	 *            x-value of the touch
	 * @param touchY
	 *            y-value of the touch
	 */
	
	public void update(World world, int width, int height, int touchX, int touchY,
			GameScreen.GameState state) {
		if (Gdx.input.isTouched()) {
			if (state == GameState.PLAYING) {
				switch (ctrl) {
				case DUALMOVE:
					// STYLE 1: Mobile controls
					if (touchX < width / 6) {
						world.player.setCenterY(height - touchY);
					} else {
						// calculates and sets the mirror angle -- from the
						// touch point to the mirror position
						world.mirror.setMirrorAngle(world.mirror.getCenter(),
								new Vector2(touchX, height - touchY));
					}
					break;
				case MIRRORMOVE:
					// STYLE 2: Stationary controls
					world.mirror.setMirrorAngle(world.mirror.getCenter(),
							new Vector2(touchX, height - touchY));
					world.mirror
							.rotateAroundPlayer(
									world.player.getCenter(),
									(world.player.bounds.width / 2)
											+ 10
											+ (world.light.getOutgoingBeam().isPrism ? 40
													: 0));

					break;
				case PLAYERMOVE:
					// STYLE 3: Stationary mirror, movable player
					world.player.setCenterY(height - touchY);

					break;
				case REGIONMOVE:
					// STYLE 4: Region around player governs movement, else
					// mirror movement
					if (height - touchY > world.player.getCenter().y - 200
							&& height - touchY < world.player.getCenter().y + 200
							&& touchX < width / 6) {
						world.player.follow(height - touchY
								- world.player.bounds.height / 2);
					} else if (!(world.player.inventory.size() > 0 && world.player.inventoryRects[0]
							.contains(touchX, height - touchY))) {
						world.mirror.setMirrorAngle(world.mirror.getCenter(),
								new Vector2(touchX, height - touchY));
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
			} else if (state == GameState.MENU && world.menu.menuState == Menu.MenuState.MAIN) {
				// Sets the beam to pass through the touch.
				float X = (720 * (640 - touchX) / (/*height - */touchY));
				world.light.beams.get(1).updateIncomingBeam(
						new Vector2(640 - X, 0), true, world.player);
			}

		}
	}
}
