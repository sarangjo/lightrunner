package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Assets {
	public static Music soundTrack;
	public static Sound blip;
	public static Sound hit;
	public static Sound died;
	public static Sound clearScreen;
	public static Sound oneHit;
	public static Sound prism;
	public static Sound spawnMagnet;
	public static Sound[] powerups = new Sound[3];
	public static Texture titleScreen;
	public static Texture loadingScreen;
	public static Texture gameOverScreen;
	public static Texture pixel;
	public static Texture powerupBox;
	public static Texture pauseButton;
	public static Rectangle fullScreen;
	public static Texture[] introCuts = new Texture[3];
	public static Texture[] instructionCuts = new Texture[3];

	// public static Color offColor = Color.;

	public static void loadContent() {
		soundTrack = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.mp3"));
		blip = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
		hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		died = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
		clearScreen = Gdx.audio.newSound(Gdx.files.internal("clearScreen.wav"));
		oneHit = Gdx.audio.newSound(Gdx.files.internal("onehit.wav"));
		prism = Gdx.audio.newSound(Gdx.files.internal("prism.wav"));
		spawnMagnet = Gdx.audio.newSound(Gdx.files.internal("spawnMagnet.wav"));
		for (int sound = 0; sound < powerups.length; sound++) {
			powerups[sound] = Gdx.audio.newSound(Gdx.files.internal("Powerup"
					+ sound + ".wav"));
		}
		fullScreen = new Rectangle(0, 0, GameScreen.width, GameScreen.height);
		titleScreen = new Texture("LightRunnerTitle.png");
		loadingScreen = new Texture("LoadingScreen.png");
		gameOverScreen = new Texture("gameover.png");
		pixel = new Texture("pixel.png");
		powerupBox = new Texture("powerupBox.png");
		pauseButton = new Texture("pause.png");
		for (int texture = 1; texture <= introCuts.length; texture++) {
			introCuts[texture - 1] = new Texture("cut" + texture + ".png");
		}
		for (int texture = 0; texture < instructionCuts.length; texture++) {
			instructionCuts[texture] = new Texture("inst" + (texture+1) + ".png");
		}
	}

	public static void drawByPixels(SpriteBatch batch, Rectangle r, Color c) {
		batch.begin();
		batch.setColor(c);
		batch.draw(new TextureRegion(Assets.pixel), r.x, r.y, 0, 0, 1, 1,
				r.width, r.height, 0);
		batch.setColor(Color.WHITE);
		batch.end();
	}
}
