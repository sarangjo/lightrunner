package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.DialogBox.DialogBoxSituation;
import com.picotech.lightrunnerlibgdx.DialogBox.DialogBoxType;

/**
 * The main class that governs the game. COntains WorldRenderer and World
 * 
 * @author Sarang
 * 
 */
public class GameScreen implements Screen, InputProcessor {
	/**
	 * The different states of the game, in chronological order.
	 */
	static enum GameState {
		LOADING, MENU, READY, PLAYING/* , GAMEOVER */
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
	public static boolean dialogBoxActive = false;
	public static DialogBoxSituation situation;

	public static int width, height;
	public final static float DEFAULTW = 1280f, DEFAULTH = 720f;
	public static Vector2 defS = new Vector2(1f, 1f);

	public World world;
	public WorldRenderer renderer;

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
		defS = new Vector2((float) width / (float) DEFAULTW, (float) height
				/ (float) DEFAULTH);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		Input.setMovement(Input.Movement.REGIONMOVE);

		Assets.loadContent();
		update();
		mainMenuBeam = new Vector2(0, height);
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
		renderer.render();
		update();
	}

	public void endIntro() {
		Assets.showIntro = false;
		Assets.introFile.writeString("n", false);
		state = GameState.MENU;
		world = new World();
		renderer = new WorldRenderer(world);
		world.loadContent();
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
			// if (Assets.showIntro)
			world.menu.menuState = Menu.MenuState.INTRODUCTION;
			// else
			// endIntro();
		} else if (state == GameState.MENU) {
			if (world.menu.menuState == Menu.MenuState.INTRODUCTION) {
				if (introCut >= ((Menu.intro == Menu.IntroStyle.LONG) ? Assets.introCuts.length
						: 1)) {
					endIntro();
				}
			} else if (world.menu.menuState == Menu.MenuState.HELP) {
				if (instructionsScreen >= Assets.instructionCuts.length) {
					endIntro();
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
		/*
		 * if (renderer.terminate) { state = GameState.LOADING; }
		 */
	}

	@Override
	public void resize(int width, int height) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		defS.x = (float) width / (float) DEFAULTW;
		defS.y = (float) height / (float) DEFAULTH;
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
			if (dialogBoxActive) {
				dialogBoxTouched(world.db.defaultButton);
			} else if (state == GameState.MENU) {
				if (world.menu.menuState == Menu.MenuState.MAIN) {
					Assets.playSound(Assets.blip);
					// Gdx.app.exit();
					situation = DialogBoxSituation.MAINQUIT;
					world.showDialogBox(DialogBoxType.YESNO);
				} else if (world.menu.menuState == Menu.MenuState.CREDITS
						|| world.menu.menuState == Menu.MenuState.INTRODUCTION
						|| world.menu.menuState == Menu.MenuState.HELP
						|| world.menu.menuState == Menu.MenuState.STATISTICS
						|| world.menu.menuState == Menu.MenuState.OPTIONS
						|| world.menu.menuState == Menu.MenuState.GAMEOVER) {
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
				world.cycleMirrors();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// Debug mode (for desktop)
		if (keycode == Keys.F1) {
			World.debugMode = !World.debugMode;
			return true;
		} else if (keycode == Keys.K && World.debugMode) {
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
		if (!dialogBoxActive) {
			if (pointer == 0
					&& !(world.pauseButton.contains(Input.touchX, Input.touchY) && state == GameState.PLAYING)) {
				Input.touchX = x;
				Input.touchY = GameScreen.height - y;
				Input.update(world, x, y);
				Input.touchDownPt = new Vector2(Input.touchX, Input.touchY);
			}
			screenTouched(x, y, false);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Input.touchX = screenX;
		Input.touchY = height - screenY;
		Input.touchUpPt = new Vector2(Input.touchX, Input.touchY);

		if (dialogBoxActive) {
			dialogBoxTouched(-1);
		} else {
			if (state == GameState.MENU) {

				if (world.menu.menuState == Menu.MenuState.MAIN) {
					// Draws the light in the menu only when a touch is
					// registered.
					world.light.getOutgoingBeam().updateIncomingBeam(
							mainMenuBeam, true, world.player);

					if (isTouched(world.menu.helpButton)) {
						Assets.playSound(Assets.blip);
						instructionsScreen = 0;
						world.menu.menuState = Menu.MenuState.HELP;
					} else if (isTouched(world.menu.creditsButton)) {
						Assets.playSound(Assets.blip);
						world.menu.menuState = Menu.MenuState.CREDITS;
					} else if (isTouched(world.menu.quitButton)) {
						Assets.playSound(Assets.blip);
						// Gdx.app.exit();
						situation = DialogBoxSituation.MAINQUIT;
						world.showDialogBox(DialogBoxType.YESNO);
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
						World.enemiesKilled = 0;
						World.score = 0;
						World.totalTime = 0;
					} else if (isTouched(world.menu.statisticsButton)) {
						Assets.playSound(Assets.blip);
						StatLogger2.readAllStats(false);
						world.menu.menuState = Menu.MenuState.STATISTICS;
					}
				} else if (world.menu.menuState == Menu.MenuState.PAUSE) {
					if (pointer == 2 || isTouched(world.menu.resumeButton)) {
						Assets.playSound(Assets.blip);
						state = GameState.PLAYING;
					} else if (isTouched(world.menu.restartButton)) {
						Assets.playSound(Assets.blip);
						situation = DialogBoxSituation.GAMERESTART;
						world.showDialogBox(DialogBoxType.YESNO);
					} else if (isTouched(world.menu.backMainButton)) {
						Assets.playSound(Assets.blip);
						situation = DialogBoxSituation.GAMEQUIT;
						world.showDialogBox(DialogBoxType.YESNO);
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
						// world.menu.menuState = Menu.MenuState.MAIN;
						introCut = 4;
						endIntro();
					} else
						introCut++;
				} else if (world.menu.menuState == Menu.MenuState.OPTIONS) {
					if (isTouched(world.menu.backMainButton)) {
						Assets.playSound(Assets.blip);
						world.menu.menuState = Menu.MenuState.MAIN;
					} else if (isTouched(world.menu.resetDataButton)) {
						// RESET ALL DATA
						Assets.playSound(Assets.blip);
						situation = DialogBoxSituation.RESETDATA;
						world.showDialogBox(DialogBoxType.YESNO);
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
				} else if (world.menu.menuState == Menu.MenuState.HELP) {
					setInstructionsScreen(230);
				} else if (world.menu.menuState == Menu.MenuState.STATISTICS) {
					if (isTouched(world.menu.backMainButton)) {
						world.menu.menuState = Menu.MenuState.MAIN;
					}
				} else if (world.menu.menuState == Menu.MenuState.GAMEOVER) {
					if (isTouched(world.menu.backMainButton)) {
						Assets.playSound(Assets.blip);
						gameEnd(true);
					}
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
		}
		return true;
	}

	public void resetAll() {
		Assets.resetFiles();
	}

	public void dialogBoxTouched(int customN) {
		// In case of the back button, GameScreen provides a button already.
		int buttonPressed = customN;
		if (customN == -1)
			buttonPressed = world.db.touched();

		boolean endDialog = (buttonPressed >= 0);
		switch (situation) {
		case MAINQUIT:
			if (buttonPressed == 0) {
				// Yes
				Gdx.app.exit();
			}
			break;
		case GAMEQUIT:
			if (buttonPressed == 0) {
				gameEnd(false);
			}
			break;
		case GAMERESTART:
			if (buttonPressed == 0) {
				renderer.terminate = true;
				restart = true;
				state = GameState.READY;
			}
			break;
		case RESETDATA:
			if (buttonPressed == 0) {
				resetAll();
				situation = DialogBoxSituation.DATARESET;
				world.showDialogBox(DialogBoxType.OK);
				endDialog = false;
			}
			break;
		case DATARESET:
			if (buttonPressed >= 0) {
				endDialog = true;
			}
		}
		if (endDialog)
			dialogBoxActive = false;
	}

	public void gameEnd(boolean died) {
		world.menu.menuState = Menu.MenuState.MAIN;
		renderer.terminate = true;
		state = GameState.LOADING;
		// TODO: Switch to MENU?
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
		currentX0 = (int) ((160 - instructionsScreen * 1060) * defS.x);
	}

	public boolean isTouched(Rectangle r) {
		return r.contains(Input.touchX, Input.touchY);
	}

	public boolean isTouched(Sprite2 s) {
		return s.bounds.contains(Input.touchX, Input.touchY);
	}

	/**
	 * The main method for down touches.
	 * 
	 * @param screenX
	 * @param screenY
	 * @param isDragged
	 */
	public void screenTouched(int screenX, int screenY, boolean isDragged) {
		// Updating the distance between the initial down touch and the current
		Input.dragDistance = (isDragged) ? Input.touchDragPt
				.sub(Input.touchDownPt) : Input.touchDragPt;

		// The logic for the scrolling in the instructions screen.
		if (isDragged && state == GameState.MENU
				&& world.menu.menuState == Menu.MenuState.HELP) {
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
