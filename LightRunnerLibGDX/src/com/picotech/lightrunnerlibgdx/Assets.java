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
	public static Texture titleScreen;
	public static Texture loadingScreen;
	public static Texture pixel;
	public static Texture powerupBox;
	public static Texture pauseButton;
	public static Color activeColor = Color.WHITE;

	// public static Color offColor = Color.;

	public static void loadContent() {
		soundTrack = Gdx.audio.newMusic(Gdx.files.internal("soundtrack.mp3"));
		blip = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
		hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
		died = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));

		titleScreen = new Texture("LightRunnerTitle.png");
		loadingScreen = new Texture("LoadingScreen.png");
		pixel = new Texture("pixel.png");
		powerupBox = new Texture("powerupBox.png");
		pauseButton = new Texture("pause.png");
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
