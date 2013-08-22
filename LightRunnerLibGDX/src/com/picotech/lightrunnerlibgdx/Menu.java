package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu extends Sprite2 {
	public enum MenuState
	{
		MAIN, PAUSE, OPTIONS, INSTRUCTIONS, CREDITS, STATISTICS
	}
	MenuState menuState = MenuState.MAIN;
	
	public BitmapFont bf;
	
	// MainMenu
	public Rectangle playButton, instructionsButton, statisticsButton, optionsButton, creditsButton, quitButton;
	// Pause
	public Rectangle resumeButton, restartButton, backMainButton;
	// Options
	public Rectangle Music;
	// Instructions
	public Rectangle BackButton, NextButton;
	
	public Rectangle blackScreen; 
	
	public Menu() {
		super(new Vector2(0,0), 1280, 720);
		// Initializes the rectangular buttons to be a particular x, y, width, height
		playButton = new Rectangle(440, 160, 400, 400); 
		instructionsButton = new Rectangle(1050, 400, 200, 80);
		statisticsButton = new Rectangle(1050, 310, 200, 80);
		optionsButton = new Rectangle(1050, 220, 200, 80);
		creditsButton = new Rectangle(1050, 130, 200, 80);
		quitButton = new Rectangle(1050, 40, 200, 80);

		// Pause
		resumeButton = new Rectangle(800, 460, 400, 100);
		restartButton = new Rectangle(800, 310, 400, 100);
		backMainButton = new Rectangle(800, 160, 400, 100);
		// Options
		//Music = new Sprite2();
		// Instructions
		BackButton = new Rectangle(); NextButton = new Rectangle();
		
		blackScreen = new Rectangle(0, 0, 1280, 720);
	}
	
	@Override
	public void loadContent()
	{
		bf = new BitmapFont();
		bf.scale(1);
		bf.setColor(Color.BLACK);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		switch (menuState)
		{
			case CREDITS:
				//also needs a back button
				//this is completely spitball
				//white font on black background to make our names pop
				
				//LightRunner logo here
				batch.begin();
				Assets.drawByPixels(batch, blackScreen, Color.BLACK);
				bf.setColor(Color.WHITE);
				bf.draw(batch, "Cameron Akker", 540, 120);
				bf.draw(batch, "Daniel Fang", 540, 200);
				bf.draw(batch, "Sarang Joshi", 540, 280);
				bf.draw(batch, "Adarsh Karnati", 540, 360);
				bf.draw(batch,  "Atticus Liu", 540, 480);
				bf.draw(batch, "Special thanks to StudentRND", 480, 580);
				batch.end();
				//break;
			case INSTRUCTIONS:
				//two-fold: three plot .png's come here
				//then put the instruction .png's 
				//will take place as a sequence
				break;
			case MAIN:
				Assets.drawByPixels(batch, playButton, Color.WHITE);
				Assets.drawByPixels(batch, instructionsButton, Color.WHITE);
				Assets.drawByPixels(batch, statisticsButton, Color.WHITE);
				Assets.drawByPixels(batch, optionsButton, Color.WHITE);
				Assets.drawByPixels(batch, creditsButton, Color.WHITE);
				Assets.drawByPixels(batch, quitButton, Color.WHITE);
				
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 500);
				batch.end();
				
				// 	Text
				batch.begin();
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Play", 500, getMainY(playButton)); // playButton.y + playButton.height - 15);
				bf.draw(batch, "Instructions", 1080, getMainY(instructionsButton));
				bf.draw(batch, "Statistics", 1090, getMainY(statisticsButton));
				bf.draw(batch, "Options", 1095, getMainY(optionsButton));
				bf.draw(batch, "Credits", 1100, getMainY(creditsButton));
				bf.draw(batch, "Quit", 1120, getMainY(quitButton));

				batch.end();			
				break;
			case OPTIONS:
				break;
			case PAUSE:
				Assets.drawByPixels(batch, blackScreen, new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.3f));
				Assets.drawByPixels(batch, resumeButton, Color.WHITE);
				Assets.drawByPixels(batch, restartButton, Color.WHITE);
				Assets.drawByPixels(batch, backMainButton, Color.WHITE);
				
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 460);
				batch.end();
				
				// Text
				batch.begin();
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Resume", resumeButton.x + resumeButton.width/2 - 50, getPauseY(resumeButton));
				bf.draw(batch, "Restart", resumeButton.x + resumeButton.width/2 - 50, getPauseY(restartButton));
				bf.draw(batch, "Quit", backMainButton.x + backMainButton.width/2 - 30, getPauseY(backMainButton));
				batch.end();
				break;
			case STATISTICS:
				break;
			default:
				break;
		}
	}
	
	public float getMainY(Rectangle r)
	{
		return r.y + (.65f*r.height);	
	}
	public float getPauseY(Rectangle r)
	{
		return r.y + (.65f*r.height);
	}
}
