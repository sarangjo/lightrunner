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
	public Rectangle PlayButton, InstructionsButton, StatisticsButton, QuitButton, OptionsButton;
	// Pause
	public Rectangle ResumeButton, RestartButton, BackMainButton;
	// Options
	public Rectangle Music;
	// Instructions
	public Rectangle BackButton, NextButton;
	
	public Menu() {
		super(new Vector2(0,0), 1280, 720);
		// Initializes the rectangular buttons to be a particular x, y, width, height
		PlayButton = new Rectangle(390, 400, 500, 60);
		InstructionsButton = new Rectangle(390, 310, 500, 60);
		StatisticsButton = new Rectangle(390, 220, 500, 60);
		QuitButton = new Rectangle(390, 130, 500, 60);
		OptionsButton = new Rectangle(390, 40, 500, 60);

		// Pause
		ResumeButton = new Rectangle(740, 520, 350, 100);
		RestartButton = new Rectangle(740, 420, 350, 100);
		BackMainButton = new Rectangle(740, 320, 350, 100);
		// Options
		//Music = new Sprite2();
		// Instructions
		BackButton = new Rectangle(); NextButton = new Rectangle();
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
				Assets.drawByPixels(batch, PlayButton);
				Assets.drawByPixels(batch, InstructionsButton);
				Assets.drawByPixels(batch, StatisticsButton);
				Assets.drawByPixels(batch, QuitButton);
				Assets.drawByPixels(batch, OptionsButton);
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 500);
				// 	Text
				bf.setColor(Color.BLACK);
				bf.draw(batch, "Play", 610, PlayButton.y + bounds.height - 5);
				//	bf.
				batch.end();			
				break;
			case OPTIONS:
				break;
			case PAUSE:
				batch.begin();
				batch.draw(Assets.titleScreen, 150, 560);
				batch.end();
				Assets.drawByPixels(batch, ResumeButton);
				Assets.drawByPixels(batch, RestartButton);
				Assets.drawByPixels(batch, QuitButton);
				break;
			case STATISTICS:
				break;
			default:
				break;
		}
	}
}
