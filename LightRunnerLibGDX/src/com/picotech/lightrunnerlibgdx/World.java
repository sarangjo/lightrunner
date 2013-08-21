package com.picotech.lightrunnerlibgdx;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;

import box2dLight.*;
import com.badlogic.gdx.physics.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.picotech.lightrunnerlibgdx.GameScreen.GameState;
import com.picotech.lightrunnerlibgdx.Powerup.Type;

/**
 * The World class holds all of the players, enemies and environment objects. It
 * handles collisions and drawing methods, as well as loading content.
 */

public class World {

	/*
	 * enum MenuState { PLAY, CHOOSESIDE }
	 * 
	 * MenuState menuState = MenuState.PLAY;
	 */

	Player player;
	Mirror mirror;
	Light light;
	Magnet magnet;
	DebugOverlay debug;
	StatLogger statlogger;
	Menu menu;

	BitmapFont bf;

	float deltaTime, totalTime;
	float loadedContentPercent;

	int enemiesKilled;
	int score;
	int level;
	int powerupf = 2000;

	Vector2 ENEMY_VEL;
	Vector2 LightSource;

	Rectangle pauseButton;

	boolean menuScreen;
	boolean playSelected;
	boolean controlsSelected;
	boolean isClearScreen = false;
	boolean slowActivated = false;
	boolean isIncoming = false;
	boolean playedSound = false;
	boolean debugMode = true;
	ArrayList<Enemy> enemies;
	ArrayList<Enemy> enemiesAlive;

	ArrayList<Powerup> powerups;
	public static HashMap<Type, Integer> puhm = new HashMap<Type, Integer>();

	Color healthBar;

	Random r = new Random();

	/**
	 * There are two types of worlds, the menu world and the in-game world. The
	 * behavior of the light depends on whether the game is in the menu or
	 * playing state.
	 * 
	 * @param isMenu
	 */
	public World(boolean isMenu) {
		level = 1;
		totalTime = 0;

		pauseButton = new Rectangle(GameScreen.width - 90,
				GameScreen.height - 50, 200, 80);

		enemies = new ArrayList<Enemy>();
		enemiesAlive = new ArrayList<Enemy>();
		powerups = new ArrayList<Powerup>();

		menuScreen = isMenu;
		player = new Player(new Vector2(0, 300), "characterDirection0.png");
		mirror = new Mirror(new Vector2(100, 300), "mirror.png");
		magnet = new Magnet(new Vector2(-100, -100), 48, 48, "magnet.png", .05f);
		menu = new Menu();

		debug = new DebugOverlay();
		statlogger = new StatLogger();

		if (menuScreen) {
			player = new Player(new Vector2(-100, -100),
					"characterDirection0.png");
			magnet = new Magnet(new Vector2(-1000, 400), 48, 48, "magnet.png",
					0);
			light = new Light(true);
			level = 40;
		} else {
			setLight();
			healthBar = new Color();
		}

		// Spawning enemies
		for (int i = 0; i < level; i++) {
			enemies.add(new Enemy(new Vector2(r.nextInt(950) + 300, r
					.nextInt(700)), 50, 50, level));
			enemies.get(enemies.size() - 1).loadContent();
		}

		// Power-ups
		if (!menuScreen) {
			for (Powerup pu : powerups)
				pu.loadContent();
			powerupf = r.nextInt(500) + 1500;
		}
		// HashMap values
		puhm.put(Powerup.Type.CLEARSCREEN, 5);
		puhm.put(Powerup.Type.ENEMYSLOW, 12);
		puhm.put(Powerup.Type.LIGHTMODIFIER, 15);
		puhm.put(Powerup.Type.PRISMPOWERUP, 18);
		puhm.put(Powerup.Type.INCOMINGACTIVE, 10);
	}

	private void setLight() {
		Random r = new Random();
		if (GameScreen.scheme == GameScreen.LightScheme.TOP) {
			LightSource = new Vector2(r.nextInt(640) + 420, 720);
		} else if (GameScreen.scheme == GameScreen.LightScheme.RIGHT) {
			LightSource = new Vector2(1280, r.nextInt(700 + 10));
		} else if (GameScreen.scheme == GameScreen.LightScheme.BOTTOM) {
			LightSource = new Vector2(r.nextInt(640) + 420, 0);
		}

		light = new Light(LightSource, mirror.getCenter());
	}

	/**
	 * Loads all the content of the World.
	 */
	public void loadContent() {
		player.loadContent();
		mirror.loadContent();
		magnet.loadContent();
		menu.loadContent();

		for (Powerup pu : powerups) {
			pu.loadContent();
		}

		bf = new BitmapFont();
		bf.scale(1);
		bf.setColor(Color.WHITE);

		if (debugMode)
			debug.loadContent();
	}

	/**
	 * Updates the entire World. Includes light, enemy movement, and enemy
	 * destruction. Also updates the time functions for frame rate-independent
	 * functions deltaTime and totalTime (which are all in seconds).
	 */
	public void update() {
		// Miscellaneous time updating functions.
		deltaTime = Gdx.graphics.getDeltaTime();
		totalTime += deltaTime;

		if ((debug.nothingSelected && debugMode) || !debugMode) {
			player.update();
			mirror.rotateAroundPlayer(player.getCenter(),
					(player.bounds.width / 2) + 2
							+ (light.getOutgoingBeam().isPrism ? 40 : 0));
		}
		// Updating light, player, and the mirror.
		light.update(mirror, player);
		magnet.update();

		// Updates all enemies in "enemies".
		for (Enemy e : enemies) {
			e.update();
			for (int beam = (isIncoming) ? 0 : 1; beam < light.beams.size(); beam++) {
				if (Intersector.overlapConvexPolygons(
						light.beams.get(beam).beamPolygon, e.p)) {
					if (e.alive) {
						e.health--;
						e.losingHealth = true;
						Assets.hit.play(.1f);
					} else {
						enemiesKilled++;
					}
				}
				if (Intersector.overlapConvexPolygons(player.p, e.p)) {
					if (player.alive)
						player.health--;
				}
			}
			// adds the number of enemies still alive to a new ArrayList
			if (e.alive) {
				enemiesAlive.add(e);
				e.isSlow = slowActivated;
			}

			// magnets
			if (e.getCenter().dst(magnet.getCenter()) < 500) {
				e.velocity.set(magnet.getPull(e.getCenter()));
			}
		}

		// Depending on the MenuState, it will either show the Play
		// button or the Top-Right-Bottom buttons.
		float dstX = light.getOutgoingBeam().dst.x;
		/*
		 * if (menuState == MenuState.CHOOSESIDE) { // Style 1: Manual
		 * light-source choosing.
		 * 
		 * if (dstX > 17 && dstX < 433) { GameScreen.scheme =
		 * GameScreen.selectedScheme = GameScreen.LightScheme.TOP;
		 * //GameScreen.selectedScheme = GameScreen.LightScheme.TOP;
		 * controlsSelected = true; playBlip(); } else if (dstX > 465 && dstX <
		 * 815) { GameScreen.scheme = GameScreen.selectedScheme =
		 * GameScreen.LightScheme.RIGHT; //GameScreen.selectedScheme =
		 * GameScreen.LightScheme.RIGHT; controlsSelected = true; playBlip(); }
		 * else if (dstX > 847 && dstX < 1200) { GameScreen.scheme =
		 * GameScreen.selectedScheme = GameScreen.LightScheme.BOTTOM;
		 * //GameScreen.selectedScheme = GameScreen.LightScheme.BOTTOM;
		 * controlsSelected = true; playBlip(); } else { controlsSelected =
		 * false; playedSound = false; }
		 * 
		 * // Style 2: Randomized light-source choosing. int schemeN =
		 * r.nextInt(3) + 1; GameScreen.scheme = GameScreen.selectedScheme =
		 * GameScreen.LightScheme .values()[schemeN]; controlsSelected = true;
		 * playedSound = true; GameScreen.state = GameScreen.GameState.READY; }
		 */
		if (menuScreen) {
			if (dstX > menu.playButton.x - 100
					&& dstX < menu.playButton.x + menu.playButton.width + 100) {
				playSelected = true;
				playBlip();
				// selectControls();
			} else {
				playSelected = false;
				playedSound = false;
			}
		}

		// removes the "dead" enemies from the main ArrayList
		enemies.retainAll(enemiesAlive);
		enemiesAlive.clear();

		// temporarily spawns new enemies, which get progressively faster
		if (enemies.size() < level) {
			enemies.add(new Enemy(new Vector2(1280, r.nextInt(700)), 50, 50,
					level));
			enemies.get(enemies.size() - 1).isSlow = slowActivated;
			enemies.get(enemies.size() - 1).loadContent();
		}

		// Time-wise level changing
		if (totalTime > 5 * level)
			level++;

		setScore();

		// Powerups.
		updatePowerups();

		// Debugging overlay.
		if (debugMode) {
			debug.update();
			if (debug.selectedButtons[0]) {
				System.out.println("Changed mirror.");
				if (mirror.type == Mirror.Type.CONVEX)
					mirror.type = Mirror.Type.FLAT;
				else if (mirror.type == Mirror.Type.FLAT)
					mirror.type = Mirror.Type.FOCUS;
				else if (mirror.type == Mirror.Type.FOCUS)
					mirror.type = Mirror.Type.CONVEX;
			} else if (debug.selectedButtons[1]) {
				System.out.println("Reset magnet.");
				magnet.setCenter(new Vector2(1280, r.nextInt(720)));
			} else if (debug.selectedButtons[2]) {
				System.out.println("Added powerup.");
				addPowerup();
			}
			debug.resetButtons();
		}
	}

	public void selectControls() {
		// Randomized light-source choosing.
		int schemeN = r.nextInt(3) + 1;
		GameScreen.scheme = GameScreen.selectedScheme = GameScreen.LightScheme
				.values()[schemeN];
		controlsSelected = true;
		playedSound = true;
		GameScreen.state = GameScreen.GameState.READY;
	}

	// writes to StatLogger
	public void toStatLogger(StatLogger sl) {
		sl.update(score, (int) totalTime, enemiesKilled);
	}

	public void setScore() {
		// Score algorithm, changed as of 8/20/13
		score = (int) (totalTime * 2 + enemiesKilled * 5);
	}

	private void playBlip() {
		if (!playedSound) {
			Assets.blip.play(.5f);
			playedSound = true;
		}
	}

	/**
	 * Handles all the power-up logic.
	 */
	private void updatePowerups() {
		// Randomizing spawns
		if ((int) (totalTime * 100) % powerupf == 0) {
			addPowerup();
		}

		for (int i = 0; i < powerups.size(); i++) {
			Powerup pu = powerups.get(0);
			pu.update(deltaTime);

			// Collision with player
			if (pu.position.x < player.position.x + player.bounds.width
					&& pu.position.y + pu.bounds.height > player.position.y
					&& pu.position.y < player.position.y + player.bounds.height) {

				switch (pu.type) {
				case LIGHTMODIFIER:
					light.getOutgoingBeam().setWidth(Powerup.LM_WIDTH);
					break;
				case PRISMPOWERUP:
					GameScreen.scheme = GameScreen.LightScheme.LEFT;
					light.getOutgoingBeam().setWidth(Powerup.P_WIDTH);
					mirror.setType(Mirror.Type.PRISM, "prism.png");
					break;
				case ENEMYSLOW:
					slowActivated = true;
					for (Enemy e : enemies)
						e.isSlow = true;
					break;
				case CLEARSCREEN:
					isClearScreen = true;
					for (int j = 0; j < enemies.size(); j++) {
						if (enemies.get(j).alive)
							enemiesKilled++;
					}
					setScore();
					break;
				case INCOMINGACTIVE:
					isIncoming = true;
					break;
				}
				pu.isActive = true;
				pu.isAura = true;
				pu.position = new Vector2(10000, 10000);
			}

			// Ending power-ups
			if (pu.timeActive > pu.timeOfEffect) {
				pu.end();

				switch (pu.type) {
				case LIGHTMODIFIER:
					light.getOutgoingBeam().setWidth(Light.L_WIDTH);
					break;
				case PRISMPOWERUP:
					GameScreen.scheme = GameScreen.selectedScheme;
					setLight();
					light.getOutgoingBeam().setWidth(Light.L_WIDTH);
					light.getOutgoingBeam().isPrism = false;
					mirror.setType(Mirror.Type.FLAT, "mirror.png");
					break;
				case ENEMYSLOW:
					slowActivated = false;
					for (Enemy e : enemies) {
						e.isSlow = false;
					}
					break;
				case CLEARSCREEN:
					isClearScreen = false;
					break;
				case INCOMINGACTIVE:
					isIncoming = false;
					break;
				}

				powerups.remove(i);
			}
		}
		if (isClearScreen) {
			enemiesAlive.clear();
			enemies.clear();
		}
	}

	public void addPowerup() {

		int x = r.nextInt(Powerup.Type.values().length);
		powerups.add(new Powerup(new Vector2(1300, r.nextInt(600) + 50),
				Powerup.Type.values()[x]));

		// powerups.add(new Powerup(new Vector2(1300, r.nextInt(600) + 50),
		// Powerup.Type.INCOMINGACTIVE));
		powerups.get(powerups.size() - 1).loadContent();
		powerupf = r.nextInt(500) + 2500;
	}

	/**
	 * Draws the entire world.
	 * 
	 * @param batch
	 *            the SpriteBatch from WorldRenderer
	 * @param sr
	 *            the ShapeRenderer to render light and enemies
	 */
	public void draw(SpriteBatch batch, ShapeRenderer sr) {

		for (Enemy e : enemies)
			e.draw(batch);
		// e.draw(sr);

		if (GameScreen.state == GameState.MENU) { // this draws all the graphics
													// for the menu
			menu.draw(batch);
			/*
			 * if (menuState == MenuState.PLAY) {
			 * sr.begin(ShapeType.FilledRectangle); if (playSelected)
			 * sr.setColor(Color.WHITE); else sr.setColor(Color.LIGHT_GRAY);
			 * sr.filledRect(playButton.x, playButton.y, playButton.width,
			 * playButton.height); sr.end(); batch.begin();
			 * bf.setColor(Color.BLACK); bf.draw(batch, "Play", 610, 160);
			 * batch.end(); } else if (menuState == MenuState.CHOOSESIDE) {
			 * sr.begin(ShapeType.FilledRectangle);
			 * sr.setColor(Color.LIGHT_GRAY); sr.filledRect(topButton.x,
			 * topButton.y, topButton.width, topButton.height);
			 * sr.filledRect(rightButton.x, rightButton.y, rightButton.width,
			 * rightButton.height); sr.filledRect(bottomButton.x,
			 * bottomButton.y, bottomButton.width, bottomButton.height); if
			 * (GameScreen.scheme != GameScreen.LightScheme.NONE) {
			 * sr.setColor(Color.WHITE); if (GameScreen.scheme ==
			 * GameScreen.LightScheme.TOP) { sr.filledRect(topButton.x,
			 * topButton.y, topButton.width, topButton.height); } else if
			 * (GameScreen.scheme == GameScreen.LightScheme.RIGHT) {
			 * sr.filledRect(rightButton.x, rightButton.y, rightButton.width,
			 * rightButton.height); } else if (GameScreen.scheme ==
			 * GameScreen.LightScheme.BOTTOM) { sr.filledRect(bottomButton.x,
			 * bottomButton.y, bottomButton.width, bottomButton.height); } }
			 * sr.end();
			 * 
			 * batch.begin(); bf.setColor(Color.BLACK); bf.draw(batch, "Top",
			 * 290, 160); bf.draw(batch, "Right", 590, 160); bf.draw(batch,
			 * "Bottom", 890, 160); batch.end(); }
			 */
		} else { // this draws everything needed in game
			if (debugMode)
				debug.draw(batch, sr);

			batch.begin();
			player.draw(batch, mirror.angle - 90);
			mirror.draw(batch);
			magnet.draw(batch);

			// Text drawing
			bf.setColor(Color.WHITE);
			bf.draw(batch, "Score: " + score, 0, 720);
			bf.draw(batch, "Enemies Killed: " + enemiesKilled, 225, 720);
			bf.draw(batch, "Level: " + level, 1000, 720);

			// testing
			bf.draw(batch, "pu: "
					+ (powerups.size() > 0 ? powerups.get(0).timeActive
							: "No powerups."), 550, 720);

			batch.end();
			if (GameScreen.state == GameState.PLAYING) {
				Assets.drawByPixels(batch, pauseButton);
			}

			healthBar.set(1 - player.health / 100, player.health / 100, 0, 1);

			// drawing health bar
			sr.begin(ShapeType.FilledRectangle);
			sr.setColor(healthBar);
			sr.filledRect(100, 20, player.health * 10, 10);
			sr.end();
		}

		light.draw(sr);

		// testing powerups
		if (!menuScreen)
			for (int i = 0; i < powerups.size(); i++)
				powerups.get(i).draw(batch);
	}
}
