package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;
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
	public static Texture[] instructionCuts = new Texture[10];
	public static Texture inst4;
	public static BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

	public static boolean playedSound = false;

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
		for (int texture = 1; texture <= instructionCuts.length; texture++) {
			instructionCuts[texture - 1] = new Texture("Controls\\Slide" + texture + ".png");
		}
		inst4 = new Texture("vertPlay.png");

		if (Gdx.app.getType() == Application.ApplicationType.Android)
			font = new BitmapFont();
		else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			font = TrueTypeFontFactory.createBitmapFont(
					Gdx.files.internal("Fonts\\archangelsk.ttf"), FONT_CHARACTERS,
					12.8f, 7.2f, .2f, GameScreen.width, GameScreen.height);
		}
		System.out.println("Font created");
		font.setColor(1f, 0f, 0f, 1f);
		font.scale(1);
		font.setColor(Color.WHITE);
	}

	public static void drawByPixels(SpriteBatch batch, Rectangle r, Color c) {
		batch.begin();
		batch.setColor(c);
		batch.draw(new TextureRegion(Assets.pixel), r.x, r.y, 0, 0, 1, 1,
				r.width, r.height, 0);
		batch.setColor(Color.WHITE);
		batch.end();
	}

	public static void playSound(Sound s) {
		if (!playedSound && GameScreen.sfxVolume > 0) {
			s.play(GameScreen.sfxVolume);
			playedSound = true;
		}
	}

	public static void setTextScale(float newScale) {
		font.setScale(newScale);
	}

	public static void text(SpriteBatch batch, String s, int x, int y,
			Color color) {
		batch.begin();
		font.setColor(color);
		font.draw(batch, s, x, y);
		batch.end();
	}

	public static void text(SpriteBatch batch, String s, float x, float y) {
		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch, s, x, y);
		batch.end();
	}
}
