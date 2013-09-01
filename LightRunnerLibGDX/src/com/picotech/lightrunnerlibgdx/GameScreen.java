package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.Menu.MenuState;

/**
 * The main class that governs the game. COntains WorldRenderer and World
 * 
 * @author Sarang
 * 
 */
public class GameScreen implements Screen, InputProcessor {
	/**
	 * The different states of the game.
	 */
	static enum GameState {
		LOADING, /* INTRO, */MENU, READY, PLAYING, /* PAUSED, */GAMEOVER
	}

	/**
	 * Where the light comes from.
	 */
	static enum LightScheme {
		NONE, TOP, RIGHT, BOTTOM, LEFT
	}

	public static GameState state;
	public static LightScheme scheme = LightScheme.NONE;
	public static LightScheme selectedScheme;

	private World world;
	private WorldRenderer renderer;
	public static int width, height;
	public boolean restart = false;

	public static int introCut;
	public static int instructionsScreen;

	public static float musicVolume = 0f;

	private Vector2 mainMenuBeam;
	public Vector2 touchDownPt;

	// Are these from the top-left corner or the bottom-left corner?
	// public static int touchX, touchY;

	/**
	 * First method that is called when GameScreen is created.
	 */
	@Override
	public void show() {
		state = GameState.LOADING;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		Input.setMovement(Input.Movement.REGIONMOVE);

		Assets.loadContent();
		update();
		mainMenuBeam = new Vector2(0, 720);
		Assets.soundTrack.play();
		Assets.soundTrack.setVolume(musicVolume);
		introCut = 0;
	}

	/**
	 * Called once every refresh
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render(state);
		update();
	}

	/**
	 * Used to create new objects and switch between game states
	 */
	public void update() {
		if (state == GameState.LOADING) {
			state = GameState.MENU;
			world = new World();
			renderer = new WorldRenderer(world);
			world.loadContent();
			world.menu.menuState = Menu.MenuState.INTRODUCTION;
		} else if (state == GameState.MENU) {
			if (world.menu.menuState == Menu.MenuState.INTRODUCTION) {
				if (introCut >= Assets.introCuts.length) {
					state = GameState.MENU;
					world = new World();
					renderer = new WorldRenderer(world);
					world.loadContent();
				}
			} else if (world.menu.menuState == Menu.MenuState.INSTRUCTIONS) {
				if (instructionsScreen >= Assets.instructionCuts.length) {
					state = GameState.MENU;
					world = new World();
					renderer = new WorldRenderer(world);
					world.loadContent();
				}
			}
		} else if (state == GameState.READY) {
			world = new World();
			renderer = new WorldRenderer(world);
			if (restart)
				world.selectControls();
			state = GameState.PLAYING;
		}
		// to remove
		if (renderer.terminate) {
			state = GameState.LOADING;
		}

	}

	@Override
	public void resize(int width, int height) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void hide() {

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

	// INPUT CONTROLLER:
	@Override
	public boolean keyDown(int keycode) {
		// Android back button handling
		if (keycode == Keys.BACK) {
			if (state == GameState.MENU) {
				if (world.menu.menuState == Menu.MenuState.MAIN)
					Gdx.app.exit();
				else if (world.menu.menuState == Menu.MenuState.CREDITS
						|| world.menu.menuState == Menu.MenuState.INTRODUCTION) {
					world.menu.menuState = Menu.MenuState.MAIN;
				} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
					GameScreen.state = GameScreen.GameState.PLAYING;
				}
			} else if (state == GameState.PLAYING) {
				state = GameState.MENU;
				world.menu.menuState = Menu.MenuState.PAUSE;
			}
			return true;
		} else if (state == GameState.PLAYING) {
			if (keycode == Keys.P)
				world.addPowerup();
			else if (keycode == Keys.M)
				world.addMagnet(0.1f);
			else if (keycode == Keys.S)
				world.changeMirrors();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// Debug mode (for desktop)
		if (keycode == Keys.F1) {
			World.debugMode = !World.debugMode;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (pointer == 0
				&& !(world.pauseButton.contains(Input.touchX, Input.touchY) && state == GameState.PLAYING)) {
			Input.touchX = x;
			Input.touchY = GameScreen.height - y;
			Input.update(world, x, y);
			this.touchDownPt = new Vector2(Input.touchX, Input.touchY);
		}
		screenTouched(x, y, false);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Input.touchX = screenX;
		Input.touchY = height - screenY;
		if (state == GameState.MENU) {
			if (world.menu.menuState == Menu.MenuState.MAIN) {
				// Draws the light in the menu only when a touch is registered.
				world.light.getOutgoingBeam().updateIncomingBeam(mainMenuBeam,
						true, world.player);

				if (isTouched(world.menu.instructionsButton)) {
					world.playSound(Assets.blip);
					instructionsScreen = 0;
					world.menu.menuState = Menu.MenuState.INSTRUCTIONS;
				} else if (isTouched(world.menu.creditsButton)) {
					world.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.CREDITS;
				} else if (isTouched(world.menu.quitButton)) {
					world.playSound(Assets.blip);
					Gdx.app.exit();
				} else if (isTouched(world.menu.gearButton)) {
					world.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.OPTIONS;
				} else if (isTouched(world.menu.playIntroButton)) {
					world.playSound(Assets.blip);
					introCut = 0;
					world.menu.introTime = 0f;
					world.menu.introAlpha = 0f;
					world.menu.menuState = Menu.MenuState.INTRODUCTION;
				} else if (isTouched(world.menu.playButton)) {
					world.playSound(Assets.blip);
					world.selectControls();
					state = GameState.READY;
				}
			} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
				if (pointer == 2 || isTouched(world.menu.resumeButton)) {
					world.playSound(Assets.blip);
					state = GameState.PLAYING;
				} else if (isTouched(world.menu.restartButton)) {
					world.playSound(Assets.blip);
					renderer.terminate = true;
					restart = true;
					state = GameScreen.GameState.READY;
				} else if (isTouched(world.menu.backMainButton)) {
					world.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
					renderer.terminate = true;
					state = GameScreen.GameState.LOADING;
				} else if (isTouched(world.menu.musicPButton)) {
					world.playSound(Assets.blip);
					System.out.println("set volume to " + musicVolume);
					musicVolume = (musicVolume <= 0.5f) ? 1f : 0f;
					Assets.soundTrack.setVolume(musicVolume);
				} else if (isTouched(world.menu.sfxPButton)) {
					world.playSound(Assets.blip);
					System.out.println("sfx on? " + World.soundFX);
					World.soundFX = !World.soundFX;
				}
			} else if (world.menu.menuState == Menu.MenuState.CREDITS) {
				if (isTouched(world.menu.backMainButton)) {
					world.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
				}
			} else if (world.menu.menuState == Menu.MenuState.INTRODUCTION) {
				introCut++;
			} else if (world.menu.menuState == Menu.MenuState.OPTIONS) {
				if (isTouched(world.menu.backMainButton)) {
					world.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
				}
			} else if (world.menu.menuState == Menu.MenuState.INSTRUCTIONS) {
				instructionsScreen++;
			}

		} else if (state == GameState.PLAYING) {
			// 2 represents a triple touch.
			if (pointer == 2 || isTouched(world.pauseButton)) {
				world.playSound(Assets.blip);
				state = GameState.MENU;
				world.menu.menuState = Menu.MenuState.PAUSE;
				System.out.println("registered");
			}
			if (world.player.inventory.size() > 0
					&& isTouched(world.player.inventoryRects[0])) {
				// world.playSound(Assets.blip);
				world.usePowerup(world.player.inventory.get(0));
				world.player.inventory.remove(0);
			}
		}
		return true;
	}

	public boolean isTouched(Rectangle r) {
		return r.contains(Input.touchX, Input.touchY);
	}

	public boolean isTouched(Sprite2 s) {
		return s.bounds.contains(Input.touchX, Input.touchY);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Input.update(world, screenX, screenY);
		Input.touchX = screenX;
		Input.touchY = GameScreen.height - screenY;
		screenTouched(screenX, screenY, true);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void screenTouched(int screenX, int screenY, boolean isDragged) {
		// The logic for the music volume scroll bar.
		if ((state == GameState.MENU && world.menu.menuState == Menu.MenuState.OPTIONS)) {
			// touchDownPt is the Vector2 where a touch down is registered.
			// It is only necessary for the down touch to be in the scaledRect.
			if (world.menu.musicVolume.scaledRect.contains(touchDownPt.x,
					touchDownPt.y)) {
				ScrollBar s = world.menu.musicVolume;
				if ((Input.touchX >= s.scaledRect.x + s.scroller.getWidth() / 2)
						&& (Input.touchX <= s.scaledRect.x + s.scaledRect.width
								- s.scroller.getWidth() / 2)) {
					// diffX is the difference in x-values of the touch and the
					// leftmost point of the scroll bar.
					float diffX = Input.touchX
							- (s.position.x + s.scroller.getWidth() / 2);
					world.menu.setMusicValue(diffX
							/ (s.scaledRect.width - s.scroller.getWidth()));
				} else {
					// At this point it is certain that the down touch was in
					// the region, and the dragged touch is now being
					// registered. So, the music can be set even if the x value
					// of the touch is outside the scaledRect.
					if (Input.touchX < s.scaledRect.x) {
						world.menu.setMusicValue(0);
					} else if (Input.touchX > s.scaledRect.x
							+ s.scaledRect.width) {
						world.menu.setMusicValue(1);
					}
				}
			}
		}
	}
}
