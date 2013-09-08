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
		LOADING, MENU, READY, PLAYING, GAMEOVER
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

	public World world;
	public WorldRenderer renderer;
	public static int width, height;
	public boolean restart = false;

	public static int introCut;
	public static int instructionsScreen;
	int currentX0;

	public static float musicVolume = 0f;
	public static float sfxVolume = 0f;

	private Vector2 mainMenuBeam;

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
				} else if (instructionsScreen < 0) {
					instructionsScreen = 0;
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
		// state = GameState.PLAYING;
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
						|| world.menu.menuState == Menu.MenuState.INTRODUCTION
						|| world.menu.menuState == Menu.MenuState.INSTRUCTIONS) {
					world.menu.menuState = Menu.MenuState.MAIN;

				} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
					GameScreen.state = GameScreen.GameState.PLAYING;
				}
			} else if (state == GameState.PLAYING) {
				state = GameState.MENU;
				world.menu.menuState = Menu.MenuState.PAUSE;
			}
			return true;
		} else if (state == GameState.PLAYING && World.debugMode) {
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
		} else if (keycode == Keys.K && World.debugMode){
			world.player.alive = false;
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
			Input.touchDownPt = new Vector2(Input.touchX, Input.touchY);
		}
		screenTouched(x, y, false);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Input.touchX = screenX;
		Input.touchY = height - screenY;
		Input.touchUpPt = new Vector2(Input.touchX, Input.touchY);
		if (state == GameState.MENU) {
			if (world.menu.menuState == Menu.MenuState.MAIN) {
				// Draws the light in the menu only when a touch is registered.
				world.light.getOutgoingBeam().updateIncomingBeam(mainMenuBeam,
						true, world.player);

				if (isTouched(world.menu.instructionsButton)) {
					Assets.playSound(Assets.blip);
					instructionsScreen = 0;
					world.menu.menuState = Menu.MenuState.INSTRUCTIONS;

				} else if (isTouched(world.menu.creditsButton)) {
					Assets.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.CREDITS;
				} else if (isTouched(world.menu.quitButton)) {
					Assets.playSound(Assets.blip);
					Gdx.app.exit();
				} else if (isTouched(world.menu.gearButton)) {
					Assets.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.OPTIONS;
				} else if (isTouched(world.menu.playIntroButton)) {
					Assets.playSound(Assets.blip);
					introCut = 0;
					world.menu.introTime = 0f;
					world.menu.introAlpha = 0f;
					world.menu.menuState = Menu.MenuState.INTRODUCTION;
				} else if (isTouched(world.menu.playButton)) {
					Assets.playSound(Assets.blip);
					world.selectControls();
					state = GameState.READY;
				}
			} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
				if (pointer == 2 || isTouched(world.menu.resumeButton)) {
					Assets.playSound(Assets.blip);
					state = GameState.PLAYING;
				} else if (isTouched(world.menu.restartButton)) {
					Assets.playSound(Assets.blip);
					renderer.terminate = true;
					restart = true;
					state = GameScreen.GameState.READY;
				} else if (isTouched(world.menu.backMainButton)) {
					Assets.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
					renderer.terminate = true;
					state = GameScreen.GameState.LOADING;
				} else if (isTouched(world.menu.musicPButton)) {
					Assets.playSound(Assets.blip);
					System.out.println("set volume to " + musicVolume);
					musicVolume = (musicVolume <= 0.5f) ? 1f : 0f;
					Assets.soundTrack.setVolume(musicVolume);
				} else if (isTouched(world.menu.sfxPButton)) {
					Assets.playSound(Assets.blip);
					System.out.println("sfx volume " + sfxVolume);
					sfxVolume = (sfxVolume <= 0.25f) ? 0.5f : 0f;
					// World.soundFX = !World.soundFX;
				}
			} else if (world.menu.menuState == Menu.MenuState.CREDITS) {
				if (isTouched(world.menu.backMainButton)) {
					Assets.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
				}
			} else if (world.menu.menuState == Menu.MenuState.INTRODUCTION) {
				if (isTouched(world.menu.skipButton)) {
					world.menu.menuState = Menu.MenuState.MAIN;
					introCut = 4;
				}
				introCut++;
			} else if (world.menu.menuState == Menu.MenuState.OPTIONS) {
				if (isTouched(world.menu.backMainButton)) {
					Assets.playSound(Assets.blip);
					world.menu.menuState = Menu.MenuState.MAIN;
				} else if (isTouched(world.menu.musicOButton)) {
					Assets.playSound(Assets.blip);
					System.out.println("set volume to " + musicVolume);
					musicVolume = (musicVolume <= 0.5f) ? 1f : 0f;
					world.menu.setMusicValue(musicVolume);
				} else if (isTouched(world.menu.sfxOButton)) {
					Assets.playSound(Assets.blip);
					System.out.println("sfx volume " + sfxVolume);
					float temp = (sfxVolume <= 0.25f) ? 0.5f : 0f;
					world.menu.setSFXValue(temp * 2);
					// World.soundFX = !World.soundFX;
				}
			} else if (world.menu.menuState == Menu.MenuState.INSTRUCTIONS) {
				setInstructionsScreen(230);
			}

		} else if (state == GameState.PLAYING) {
			// 2 represents a triple touch.
			if (pointer == 2 || isTouched(world.pauseButton)) {
				Assets.playSound(Assets.blip);
				state = GameState.MENU;
				world.menu.menuState = Menu.MenuState.PAUSE;
				System.out.println("paused");
			}
			if (world.player.inventory.size() > 0
					&& isTouched(world.player.inventoryRects[0])) {
				// Assets.playSound(Assets.blip);
				world.usePowerup(world.player.inventory.get(0));
				world.player.inventory.remove(0);
			}
		}
		return true;
	}

	/**
	 * This method is called when a touch is released in instructions menu. <br>
	 * If the touch is moved in either direction more than 300 pixels, then the
	 * instruction screen changes.
	 */
	private void setInstructionsScreen(int dragDistance) {
		// Increasing the index of the instruction screen.
		if (Input.dragDistance.x < -dragDistance) {
			instructionsScreen++;
		}
		// Decreasing the index of the instruction screen.
		if (Input.dragDistance.x > dragDistance) {
			instructionsScreen -= (instructionsScreen > 0) ? 1 : 0;
		}
		setCurrentX0();
		world.menu.x0 = currentX0;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Input.update(world, screenX, screenY);
		Input.touchX = screenX;
		Input.touchY = GameScreen.height - screenY;
		Input.touchDragPt = new Vector2(Input.touchX, Input.touchY);
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

	/**
	 * Sets the current live x0.
	 */
	public void setCurrentX0() {
		currentX0 = 160 - instructionsScreen * 1060;
	}

	public boolean isTouched(Rectangle r) {
		return r.contains(Input.touchX, Input.touchY);
	}

	public boolean isTouched(Sprite2 s) {
		return s.bounds.contains(Input.touchX, Input.touchY);
	}

	public void screenTouched(int screenX, int screenY, boolean isDragged) {
		// Updating the distance between the initial down touch and the current
		Input.dragDistance = (isDragged) ? Input.touchDragPt
				.sub(Input.touchDownPt) : Input.touchDragPt;

		// The logic for the scrolling in the instructions screen.
		if (isDragged && state == GameState.MENU
				&& world.menu.menuState == Menu.MenuState.INSTRUCTIONS) {
			setCurrentX0();
			world.menu.x0 = currentX0 + (int) Input.dragDistance.x;
		}

		// The logic for the music volume scroll bar.
		if (state == GameState.MENU
				&& world.menu.menuState == Menu.MenuState.OPTIONS) {
			// touchDownPt is the Vector2 where a touch down is registered.
			// It is only necessary for the down touch to be in the scaledRect.
			if (world.menu.musicVolume.scaledRect.contains(Input.touchDownPt.x,
					Input.touchDownPt.y)) {
				float newMVolume = world.menu.musicVolume.touched(Input.touchX,
						Input.touchY);
				if (newMVolume >= 0) {
					world.menu.setMusicValue(newMVolume);
				}
			} else if (world.menu.sfxVolume.scaledRect.contains(
					Input.touchDownPt.x, Input.touchDownPt.y)) {
				float newSVolume = world.menu.sfxVolume.touched(Input.touchX,
						Input.touchY);
				if (newSVolume >= 0) {
					world.menu.setSFXValue(newSVolume);
				}
			}
		}
	}
}
