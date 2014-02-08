package com.picotech.lightrunnerlibgdx;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.picotech.lightrunnerlibgdx.Menu.IntroStyle;

public class Assets {
	// Sounds
	public static Music soundTrack;
	public static Sound blip;
	public static Sound hit;
	public static Sound died;
	public static Sound clearScreen;
	public static Sound oneHit;
	public static Sound prism;
	public static Sound spawnMagnet;
	public static Sound[] powerups = new Sound[3];
	// Universal textures
	public static Texture titleScreen;
	public static Texture loadingScreen;
	public static Texture gameOverScreen;
	public static Texture pixel;
	public static Texture powerupBox;
	public static Texture pauseButton;
	public static Rectangle fullScreen;
	public static Texture[] introCuts = new Texture[4];
	public static Texture introCutShort;
	public static Texture[] instructionCuts = new Texture[5];
	public static Texture play;
	// Font
	public static BitmapFont font;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

	public static boolean playedSound = false;
	public static boolean showIntro = true;

	// Files
	public static FileHandle introFile = Gdx.files.local("introFile.txt");
	public static FileHandle highScoresFile = Gdx.files.local("highScores.txt");
	public static FileHandle eKilledFile = Gdx.files.local("eKilled.txt");
	public static FileHandle timesFile = Gdx.files.local("times.txt");
	public static FileHandle cumulFile = Gdx.files.local("cumulative.txt");
	public static FileHandle optionsFile = Gdx.files.local("options.txt");

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
		for (int texture = 0; texture < introCuts.length; texture++) {
			introCuts[texture] = new Texture("cut" + (texture + 1) + ".png");
		}
		introCutShort = new Texture("cut4.png");
		for (int texture = 1; texture <= instructionCuts.length; texture++) {
			instructionCuts[texture - 1] = new Texture("inst" + texture
					+ ".png");
		}
		play = new Texture("vertPlay.png");

		if (Gdx.app.getType() == Application.ApplicationType.Android)
			font = new BitmapFont();
		else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			font = TrueTypeFontFactory.createBitmapFont(
					Gdx.files.internal("Fonts\\archangelsk.ttf"),
					FONT_CHARACTERS, 12.8f, 7.2f, .2f, GameScreen.width,
					GameScreen.height);
		}
		System.out.println("Font created");
		font.setColor(1f, 0f, 0f, 1f);
		font.scale(1);
		font.setColor(Color.WHITE);

		checkForShowIntro();
		setMusicAndSFX();
	}

	private static void checkForShowIntro() {
		if (introFile.exists()) {
			// If the file has a 0 at the beginning then it
			String shouldShowIntro = introFile.readString().substring(0, 1);
			showIntro = (shouldShowIntro.equals("y")) ? true : false;
		} else {
			// If it doesn't even exist then it's probably the first time.
			try {
				introFile.delete();
			} catch (GdxRuntimeException e) {
			}
			try {
				introFile.file().createNewFile();
			} catch (IOException e) {
			}
			introFile.writeString("y", false);
		}
		Menu.intro = (showIntro) ? IntroStyle.LONG : IntroStyle.SHORT;
	}

	public static void drawByPixels(SpriteBatch batch, Rectangle r, Color c) {
		/*
		 * Modified code Vector2 scale = new Vector2((float)GameScreen.width /
		 * (float)GameScreen.DEFAULTW, (float)GameScreen.height /
		 * (float)GameScreen.DEFAULTH); Rectangle mod = new Rectangle(r.x *
		 * scale.x, r.y * scale.y, r.width scale.x, r.height * scale.y); end
		 * modified code
		 */
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

	public static void text(SpriteBatch batch, String s, float x, float y,
			Color color) {
		batch.begin();
		font.setColor(color);
		font.draw(batch, s, x, y);
		batch.end();
	}

	public static void text(SpriteBatch batch, String s, Vector2 pos,
			Color color) {
		text(batch, s, pos.x, pos.y, color);
	}

	public static void textWhite(SpriteBatch batch, String s, float x, float y) {
		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch, s, x, y);
		batch.end();
	}

	public static void textWhite(SpriteBatch batch, String s, Vector2 pos) {
		textWhite(batch, s, pos.x, pos.y);
	}

	public static float fontHeight() {
		float scale = font.getScaleY();
		switch (Gdx.app.getType()) {
		case Android:
			return scale * 12;
		case Desktop:
			return scale * 16;
		}
		return scale * 12;
	}

	public static float fontWidth() {
		float scale = font.getScaleX();
		switch (Gdx.app.getType()) {
		case Android:
			return scale * 7.5f;
		case Desktop:
			return scale * 10;
		}
		return scale * 10;
	}

	public static float fontX(float x, float width, String text) {
		float textWidth = textWidth(text);
		return x + ((width - textWidth) / 2);
	}

	public static float textWidth(String text) {
		float width = 0;
		for (char c : text.toCharArray()) {
			if (c == 'i' || c == 'j' || c == 'l' || c == 't')
				width += fontWidth() / 2;
			else
				width += fontWidth();
		}
		return width;
	}

	public static float fontY(float y, float height) {
		return y + ((height - fontHeight()) / 2);
	}

	public static float fontX(Rectangle r, String text) {
		return fontX(r.x, r.width, text);
	}

	public static float fontY(Rectangle r) {
		return fontY(r.y, r.height);
	}

	public static float androidFontY(Rectangle r) {
		return r.y + (r.height - fontHeight()) / 2 + fontHeight();
	}

	public static Vector2 fontPos(Rectangle r, String text) {
		switch (Gdx.app.getType()) {
		case Android:
			return new Vector2(fontX(r, text), androidFontY(r));
		case Desktop:
			return new Vector2(fontX(r, text), fontY(r));
		}
		return null;
	}

	public static void resetFiles() {
		// StatLogger files
		if (highScoresFile.exists())
			highScoresFile.delete();
		if (eKilledFile.exists())
			eKilledFile.delete();
		if (timesFile.exists())
			timesFile.delete();
		if (Assets.cumulFile.exists())
			Assets.cumulFile.delete();

		try {
			highScoresFile.file().createNewFile();
			eKilledFile.file().createNewFile();
			timesFile.file().createNewFile();
			Assets.cumulFile.file().createNewFile();
		} catch (IOException e) {
			// TODO: Put something here
			e.printStackTrace();
		}
		// Intro File
		Assets.introFile.delete();
	}

	public static void setMusicAndSFX() {
		try {
			String text = optionsFile.readString();
			try {
				int comma = text.indexOf(',');
				float music = Float.parseFloat(text.substring(0, comma));
				float sfx = Float.parseFloat(text.substring(comma + 1));
				GameScreen.musicVolume = music;
				GameScreen.sfxVolume = sfx;
			} catch (IndexOutOfBoundsException e3) {
				e3.printStackTrace();
			}
		} catch (GdxRuntimeException e) {
			try {
				optionsFile.file().createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void saveMusicAndSFX() {
		if (!optionsFile.exists())
			try {
				optionsFile.file().createNewFile();
			} catch (IOException e) {
				// TODO: Put something here
				e.printStackTrace();
			}
		optionsFile.writeString(GameScreen.musicVolume + ","
				+ GameScreen.sfxVolume, false);
	}
}
