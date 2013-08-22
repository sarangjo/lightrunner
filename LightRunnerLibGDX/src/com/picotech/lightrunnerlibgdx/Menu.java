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
	public Rectangle playButton, instructionsButton, statisticsButton, quitButton, optionsButton;
	// Pause
	public Rectangle ResumeButton, RestartButton, BackMainButton;
	// Options
	public Rectangle Music;
	// Instructions
	public Rectangle BackButton, NextButton;
	
	public Rectangle blackScreen; 
	
	public Menu() {
		super(new Vector2(0,0), 1280, 720);
		// Initializes the rectangular buttons to be a particular x, y, width, height
		playButton = new Rectangle(390, 400, 500, 60);
		instructionsButton = new Rectangle(390, 310, 500, 60);
		statisticsButton = new Rectangle(390, 220, 500, 60);
		quitButton = new Rectangle(390, 130, 500, 60);
		optionsButton = new Rectangle(390, 40, 500, 60);

		// Pause
		ResumeButton = new Rectangle(700, 460, 400, 100);
		RestartButton = new Rectangle(700, 310, 400, 100);
		BackMainButton = new Rectangle(700, 160, 400, 100);
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
				break;
			case INSTRUCTIONS:
				break;
			case MAIN:
				Assets.drawByPixels(batch, playButton, Color.WHITE);
				Assets.drawByPixels(batch, instructionsButton, Color.WHITE);
				Assets.drawByPixels(batch, statisticsButton, Color.WHITE);
				Assets.drawByPixels(batch, quitButton, Color.WHITE);
				Assets.drawByPixels(batch, optionsButton, Color.WHITE);
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 500);
				// 	Text
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Play", 610, playButton.y + bounds.height - 5);
				//	bf.
				batch.end();			
				break;
			case OPTIONS:
				break;
			case PAUSE:
				Assets.drawByPixels(batch, blackScreen, new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.5f));
				
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 500);
				batch.end();
				
				Assets.drawByPixels(batch, ResumeButton, Color.WHITE);
				Assets.drawByPixels(batch, RestartButton, Color.WHITE);
				Assets.drawByPixels(batch, BackMainButton, Color.WHITE);
				break;
			case STATISTICS:
				break;
			default:
				break;
		}
	}
}
