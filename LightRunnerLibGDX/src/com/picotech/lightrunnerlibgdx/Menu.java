package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends Sprite2 {
	public enum MenuState {
		MAIN, PAUSE, OPTIONS, INTRODUCTION, INSTRUCTIONS, CREDITS, STATISTICS
	}

	MenuState menuState = MenuState.MAIN;

	public BitmapFont bf;

	// INTRO variables
	public float switchTime = 5f;
	public float fadeBufferTime = 0.75f;
	public float introTime = 0f;
	public float introAlpha = 0f;

	// MainMenu
	public Rectangle playButton, instructionsButton, statisticsButton,
			creditsButton, quitButton;
	float buffer;
	Rectangle grey;
	public Sprite2 gearButton, playIntroButton;

	// Pause
	public Rectangle resumeButton, restartButton, backMainButton;
	public Sprite2 musicPButton, sfxPButton;

	// Options
	public Sprite2 musicOButton, sfxOButton;
	public ScrollBar musicVolume;
	
	// Instructions
	public Rectangle BackButton, NextButton;
	public int x0 = 160;

	private float fadingScale;

	public Menu() {
		super(new Vector2(0, 0), 1280, 720);
		// Initializes the rectangular buttons to be a particular x, y, width,
		// height

		instructionsButton = new Rectangle(1050, 510, 200, 90);
		statisticsButton = new Rectangle(1050, 380, 200, 90);
		creditsButton = new Rectangle(1050, 250, 200, 90);
		quitButton = new Rectangle(1050, 120, 200, 90);

		buffer = 1280 - (creditsButton.x + creditsButton.width);
		grey = new Rectangle(creditsButton.x - buffer, 0,
				1280 - (quitButton.x - buffer), 720);
		gearButton = new Sprite2(grey.x + 90, 30, "gear.png");
		playIntroButton = new Sprite2(0, 0, "playIntro.png");

		// playButton is way bigger, and will eventually be a .png
		playButton = new Rectangle(0, 0, grey.x, 720);

		// Pause
		resumeButton = new Rectangle(800, 460, 400, 100);
		restartButton = new Rectangle(800, 310, 400, 100);
		backMainButton = new Rectangle(800, 160, 400, 100);
		musicPButton = new Sprite2(resumeButton.x + 60, backMainButton.y - 120,
				"music.png");
		sfxPButton = new Sprite2(resumeButton.x + resumeButton.width - 140,
				backMainButton.y - 120, "sfx.png");
		// Options
		musicOButton = new Sprite2(200, 410, "music.png");
		sfxOButton = new Sprite2(200, 310, "sfx.png");
		musicVolume = new ScrollBar(new Vector2(musicOButton.position.x + 160,
				musicOButton.position.y - 10), GameScreen.musicVolume, 800f);

		// Instructions
		BackButton = new Rectangle();
		NextButton = new Rectangle();

		fadingScale = 0;
	}

	@Override
	public void loadContent() {
		bf = new BitmapFont();
		bf.scale(1);
		bf.setColor(Color.BLACK);

		musicPButton.loadContent();
		sfxPButton.loadContent();
		musicOButton.loadContent();
		sfxOButton.loadContent();

		musicVolume.loadContent();

		gearButton.loadContent();
		playIntroButton.loadContent();
	}

	/**
	 * Draws and updates the menu.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		switch (menuState) {
		case CREDITS:
			// also needs a back button
			// this is completely spitball
			// white font on black background to make our names pop

			// LightRunner logo here
			Assets.drawByPixels(batch, Assets.fullScreen, Color.BLACK);
			Assets.drawByPixels(batch, this.backMainButton, Color.GRAY);

			batch.begin();
			batch.draw(Assets.titleScreen, Assets.fullScreen.width / 2
					- (Assets.titleScreen.getWidth() / 2), 440);

			bf.setColor(Color.WHITE);
			// repositioned names, "Special thanks to StudentRND"
			bf.draw(batch, "Cameron Akker", 380, 540);
			bf.draw(batch, "Daniel Fang", 380, 460);
			bf.draw(batch, "Sarang Joshi", 380, 380);
			bf.draw(batch, "Adarsh Karnati", 380, 300);
			bf.draw(batch, "Atticus Liu", 380, 220);
			bf.draw(batch, "Special thanks to StudentRND", 435, 90);
			bf.draw(batch, "Main", backMainButton.x + backMainButton.width / 2
					- 30, getPauseY(backMainButton));
			batch.end();
			break;
		case INSTRUCTIONS:
			if (GameScreen.instructionsScreen < Assets.instructionCuts.length) {
				Assets.drawByPixels(batch, Assets.fullScreen, Color.BLACK);
				
				batch.begin();
				batch.setColor(Color.WHITE);
				
				// Style 1: Only draws one image at once.
				/*batch.draw(
						Assets.instructionCuts[GameScreen.instructionsScreen], 160, 90);
						//(GameScreen.width - Assets.introCuts[GameScreen.instructionsScreen]
						//		.getWidth()) / 2,
						//(GameScreen.height - Assets.introCuts[GameScreen.instructionsScreen]
						//		.getHeight()) / 2);

				*/
				
				// Style 2: Draws the entire instruction "reel"
				for (int i = 0; i < Assets.instructionCuts.length; i++) {
					batch.draw(Assets.instructionCuts[i], x0 + 1060*i, 90);
				}
				batch.end();
			}
			break;
		case INTRODUCTION:
			// two-fold: three plot .png's come here
			// then put the instruction .png's
			// will take place as a sequence
			if (GameScreen.introCut < Assets.introCuts.length) {
				if (introTime <= fadeBufferTime) {
					// fading in
					introAlpha = introTime / fadeBufferTime;
				} else if (introTime >= switchTime - fadeBufferTime) {
					// fading out
					introAlpha = 1
							- (introTime - (switchTime - fadeBufferTime))
							/ (fadeBufferTime);
				} else if (introTime >= fadeBufferTime
						&& introTime <= switchTime - fadeBufferTime) {
					introAlpha = 1f;
				}
				Assets.drawByPixels(batch, Assets.fullScreen, Color.BLACK);
				batch.begin();
				batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b,
						introAlpha);
				// This style scales it to the entire screen.
				// batch.draw(Assets.introCuts[GameScreen.introCut], 0, 0,
				// width,
				// height);
				// This style draws it in the center.
				batch.draw(
						Assets.introCuts[GameScreen.introCut],
						(GameScreen.width - Assets.introCuts[GameScreen.introCut]
								.getWidth()) / 2,
						(GameScreen.height - Assets.introCuts[GameScreen.introCut]
								.getHeight()) / 2);
				introTime += Gdx.graphics.getDeltaTime();
				if (introTime >= switchTime) {
					// switching screens
					introTime = 0f;
					GameScreen.introCut++;
				}
				batch.end();
			}
			break;
		case MAIN:
			Assets.drawByPixels(batch, grey, new Color(Color.WHITE.r,
					Color.WHITE.g, Color.WHITE.b, 0.5f));

			Assets.drawByPixels(batch, playButton, new Color(Color.WHITE.r,
					Color.WHITE.g, Color.WHITE.b, 0f));
			Assets.drawByPixels(batch, instructionsButton, Color.GRAY);
			Assets.drawByPixels(batch, statisticsButton, Color.GRAY);
			// Assets.drawByPixels(batch, optionsButton, Color.GRAY);
			Assets.drawByPixels(batch, creditsButton, Color.GRAY);
			Assets.drawByPixels(batch, quitButton, Color.GRAY);

			batch.begin();
			batch.draw(Assets.titleScreen,
					grey.x / 2 - (Assets.titleScreen.getWidth() / 2), 440);
			gearButton.draw(batch);
			playIntroButton.draw(batch);
			batch.end();

			// Text
			batch.begin();
			bf.setColor(Color.WHITE);
			// bf.draw(batch, "Play", 500, getMainY(playButton));
			bf.draw(batch, "Instructions", 1080, getMainY(instructionsButton));
			bf.draw(batch, "Statistics", 1090, getMainY(statisticsButton));
			// bf.draw(batch, "Options", 1095, getMainY(optionsButton));
			bf.draw(batch, "Credits", 1100, getMainY(creditsButton));
			bf.draw(batch, "Quit", 1120, getMainY(quitButton));
			bf.setColor(1, 1, 1,
					(float) (0.5f + 0.5f * Math.cos(fadingScale += .1)));
			bf.setScale(5);
			bf.draw(batch, "Tap anywhere to play", 150, 350);
			bf.setScale(2f);
			batch.end();
			break;
		case OPTIONS:
			Assets.drawByPixels(batch, Assets.fullScreen, new Color(
					Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b,
					0.3f));

			// control music and sound (volumes)
			Assets.drawByPixels(batch, backMainButton, Color.GRAY);

			batch.begin();
			batch.draw(Assets.titleScreen, 150, 460);

			musicOButton.draw(batch);
			sfxOButton.draw(batch);

			batch.setColor(Color.WHITE);
			bf.draw(batch, "Main", backMainButton.x + backMainButton.width / 2
					- 30, getPauseY(backMainButton));
			//bf.draw(batch, "Value:" + musicVolume.value, 400, 400);
			batch.end();

			Assets.drawByPixels(batch, musicOButton.bounds, new Color(
					Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b,
					GameScreen.musicVolume / 2));
			if (World.soundFX)
				Assets.drawByPixels(batch, sfxOButton.bounds, new Color(
						Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 0.5f));

			musicVolume.draw(batch);

			break;
		case PAUSE:
			Assets.drawByPixels(batch, Assets.fullScreen, new Color(
					Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b,
					0.3f));
			Assets.drawByPixels(batch, resumeButton, Color.GRAY);
			Assets.drawByPixels(batch, restartButton, Color.GRAY);
			Assets.drawByPixels(batch, backMainButton, Color.GRAY);

			batch.begin();
			batch.draw(Assets.titleScreen, 150, 460);
			musicPButton.draw(batch);
			sfxPButton.draw(batch);
			batch.end();

			// Text
			batch.begin();
			bf.setColor(Color.WHITE);
			bf.draw(batch, "Resume", resumeButton.x + resumeButton.width / 2
					- 50, getPauseY(resumeButton));
			bf.draw(batch, "Restart", resumeButton.x + resumeButton.width / 2
					- 50, getPauseY(restartButton));
			bf.draw(batch, "Main", backMainButton.x + backMainButton.width / 2
					- 30, getPauseY(backMainButton));
			batch.end();

			Assets.drawByPixels(batch, musicPButton.bounds, new Color(
					Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b,
					GameScreen.musicVolume / 2));
			if (World.soundFX)
				Assets.drawByPixels(batch, sfxPButton.bounds, new Color(
						Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b, 0.5f));
			break;
		case STATISTICS:
			// display cumulative high score, time played (seconds), total score
			break;
		}
	}

	public void setMusicValue(float newV) {
		musicVolume.value = newV;
		GameScreen.musicVolume = musicVolume.value;
		Assets.soundTrack.setVolume(musicVolume.value);
	}

	public float getMainY(Rectangle r) {
		return r.y + (.65f * r.height);
	}

	public float getPauseY(Rectangle r) {
		return r.y + (.65f * r.height);
	}
}
