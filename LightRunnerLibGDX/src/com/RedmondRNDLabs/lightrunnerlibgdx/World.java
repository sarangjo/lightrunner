package com.RedmondRNDLabs.lightrunnerlibgdx;

import java.util.ArrayList;
import java.util.Random;

import com.RedmondRNDLabs.lightrunnerlibgdx.GameScreen.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The World class holds all of the players, enemies and environment objects. It
 * handles collisions and drawing methods, as well as loading content.
 */

public class World {

	enum MenuState {
		play, chooseSide
	}

	
	GameScreen.GameState state;
	MenuState menuState = MenuState.play;
	Player player;
	Mirror mirror;
	Light light;
	BitmapFont bf;

	float deltaTime, totalTime;
	float loadedContentPercent;

	Vector2 ENEMY_VEL;
	Vector2 LightSource;

	Rectangle playButton;
	Rectangle topButton, rightButton, bottomButton;

	boolean menuScreen;
	boolean playSelected;
	boolean controlsSelected;

	ArrayList<Enemy> enemies;
	ArrayList<Enemy> enemiesAlive;

	/**
	 * There are two types of worlds, the menu world and the in-game world. The
	 * behavior of the light depends on whether the game is in the menu or
	 * playing state.
	 * 
	 * @param isMenu
	 */
	public World(boolean isMenu) {
		totalTime = 0;

		playButton = new Rectangle(390, 100, 500, 100);

		topButton = new Rectangle(190, 100, 300, 100);
		rightButton = new Rectangle(490, 100, 300, 100);
		bottomButton = new Rectangle(790, 100, 300, 100);

		enemies = new ArrayList<Enemy>();
		enemiesAlive = new ArrayList<Enemy>();
		ENEMY_VEL = new Vector2(-.3f, 0);
		menuScreen = isMenu;
		player = new Player(new Vector2(0, 300), "characterDirection0.png");
		mirror = new Mirror(new Vector2(100, 300), "mirror.png");

		if (menuScreen)
			light = new Light(true);
		else
			setLight();

		// temporarily used for spawning enemies
		for (int i = 0; i < 25; i++)
			enemies.add(new Enemy(new Vector2(MathUtils.random(300, 1250),
					MathUtils.random(0, 700)), 50, 50, ENEMY_VEL, ""));
	}

	private void setLight() {
		Random r = new Random();
		if (GameScreen.scheme == GameScreen.ControlScheme.top) {
			LightSource = new Vector2(r.nextInt(840) + 220, 720);
		} else if (GameScreen.scheme == GameScreen.ControlScheme.right) {
			LightSource = new Vector2(1280, r.nextInt(700 + 10));
		} else if (GameScreen.scheme == GameScreen.ControlScheme.bottom) {
			LightSource = new Vector2(r.nextInt(840) + 220, 0);
		}

		// light = new Light(new Vector2(640, 720), mirror.getCenter());
		light = new Light(LightSource, mirror.getCenter());
	}

	/**
	 * Loads all the content of the World.
	 */
	public void loadContent() {
		player.loadContent();
		mirror.loadContent();
		bf = new BitmapFont();
		bf.scale(1);
		bf.setColor(Color.WHITE);
	}

	/**
	 * Updates the entire World. Includes light, enemy movement, and enemy
	 * destruction. Also updates the time functions for frame rate-independent
	 * functions deltaTime and totalTime (which are all in seconds).
	 */
	public void update() {

		light.update(mirror.getCenter(), mirror.angle);

		for (Enemy e : enemies) {
			e.update();
			// overlapConvexPolygons not currently working:
			// Intersector.overlapConvexPolygons(light.beams.get(1).beamPolygon,
			// e.p)
			// Intersector.isPointInPolygon(light.beams.get(1).vectorPolygon,
			// e.Position
			// Intersector.intersectSegmentCircle(light.beams.get(1).origin,
			// light.beams.get(1).dst, e.getCenter(), 50)
			if (menuScreen) {
				if (Intersector.overlapConvexPolygons(
						light.beams.get(0).beamPolygon, e.p)) {
					if (e.alive)
						e.health--;
				}

			} else {
				if (Intersector.overlapConvexPolygons(
						light.beams.get(1).beamPolygon, e.p)) {
					if (e.alive)
						e.health--;
				}
			}

			// adds the number of enemies still alive to a new ArrayList
			if (e.alive)
				enemiesAlive.add(e);
		
		}
		
		// Depending on the MenuState, it will either show the Play
		// button or the Top-Right-Bottom buttons.
		float dstX = light.beams.get(0).dst.x;
		if (menuState == MenuState.chooseSide) {
			if (dstX > 17 && dstX < 433) {
				GameScreen.scheme = GameScreen.ControlScheme.top;
				controlsSelected = true;
			} else if (dstX > 465 && dstX < 815) {
				GameScreen.scheme = GameScreen.ControlScheme.right;
				controlsSelected = true;
			} else if (dstX > 847 && dstX < 1200) {
				GameScreen.scheme = GameScreen.ControlScheme.bottom;
				controlsSelected = true;
			} else {
				controlsSelected = false;
			}
		}
		if (menuState == MenuState.play) {
			if (dstX > playButton.x - 100
					&& dstX < playButton.x + playButton.width + 100)
				playSelected = true;
			else
				playSelected = false;
		}

		
		// removes the "dead" enemies from the main ArrayList
		enemies.retainAll(enemiesAlive);
		enemiesAlive.clear();

		// temporarily spawns new enemies, which get progressively faster
		if (enemies.size() < 25)
			enemies.add(new Enemy(new Vector2(MathUtils.random(800, 1250),
					MathUtils.random(0, 700)), 50, 50, new Vector2(
					ENEMY_VEL.x -= .005f, 0), ""));

		// misc time functions
		deltaTime = Gdx.graphics.getDeltaTime();
		totalTime += deltaTime;
	}

	public void draw(SpriteBatch batch, ShapeRenderer sr) {

		for (Enemy e : enemies)
			e.draw(sr);

		if (menuScreen) {
			if (menuState == MenuState.play) {
				sr.begin(ShapeType.FilledRectangle);
				if (playSelected)
					sr.setColor(Color.WHITE);
				else
					sr.setColor(Color.LIGHT_GRAY);
				sr.filledRect(playButton.x, playButton.y, playButton.width,
						playButton.height);
				sr.end();
				batch.begin();
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Play", 610, 160);
				batch.end();
			} else if (menuState == MenuState.chooseSide) {
				sr.begin(ShapeType.FilledRectangle);
				sr.setColor(Color.LIGHT_GRAY);
				sr.filledRect(topButton.x, topButton.y, topButton.width,
						topButton.height);
				sr.filledRect(rightButton.x, rightButton.y, rightButton.width,
						rightButton.height);
				sr.filledRect(bottomButton.x, bottomButton.y,
						bottomButton.width, bottomButton.height);
				if (GameScreen.scheme != GameScreen.ControlScheme.none) {
					sr.setColor(Color.WHITE);
					if (GameScreen.scheme == GameScreen.ControlScheme.top) {
						sr.filledRect(topButton.x, topButton.y,
								topButton.width, topButton.height);
					} else if (GameScreen.scheme == GameScreen.ControlScheme.right) {
						sr.filledRect(rightButton.x, rightButton.y,
								rightButton.width, rightButton.height);
					} else if (GameScreen.scheme == GameScreen.ControlScheme.bottom) {
						sr.filledRect(bottomButton.x, bottomButton.y,
								bottomButton.width, bottomButton.height);
					}
				}
				sr.end();

				batch.begin();
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Top", 290, 160);
				bf.draw(batch, "Right", 590, 160);
				bf.draw(batch, "Bottom", 890, 160);
				batch.end();
			}
		} else {
			batch.begin();
			player.draw(batch, mirror.angle - 90);
			mirror.draw(batch);
			bf.setColor(Color.WHITE);
			bf.draw(batch, "Time: " + (int) (totalTime) + "s", 0, 720);
			bf.draw(batch, "dTime: " + (int) (deltaTime * 1000) + "ms", 225,
					720);
			batch.end();
		}

		light.draw(sr);

	}
}
