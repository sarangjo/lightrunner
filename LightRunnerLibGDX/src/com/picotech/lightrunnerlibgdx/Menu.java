package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Menu extends Sprite2 {
	public enum MenuState
	{
		MAINMENU, PAUSE, OPTIONS, INSTRUCTIONS, CREDITS, STATISTICS
	}
	MenuState menuState = MenuState.MAINMENU;
	
	// MainMenu
	public Sprite2 PlayButton, InstructionsButton, StatisticsButton, QuitButton, OptionsButton;
	// Pause
	public Sprite2 ResumeButton, RestartButton, BackMainButton;
	// Options
	public Sprite2 Music;
	// Instructions
	public Sprite2 BackButton, NextButton;
	
	public Menu() {
		super(new Vector2(0,0), 1280, 720);
		
		PlayButton = new Sprite2(390, 400, 500, 60);
		InstructionsButton = new Sprite2(390, 310, 500, 60);
		StatisticsButton = new Sprite2(390, 220, 500, 60);
		QuitButton = new Sprite2(390, 130, 500, 60);
		OptionsButton = new Sprite2(390, 40, 500, 60);

		// Pause
		ResumeButton = new Sprite2(); RestartButton = new Sprite2(); BackMainButton = new Sprite2();
		// Options
		//Music = new Sprite2();
		// Instructions
		BackButton = new Sprite2(); NextButton = new Sprite2();
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
		case MAINMENU:
			PlayButton.drawByPixels(batch);
			InstructionsButton.drawByPixels(batch);
			StatisticsButton.drawByPixels(batch);
			QuitButton.drawByPixels(batch);
			OptionsButton.drawByPixels(batch);
			break;
		case OPTIONS:
			break;
		case PAUSE:
			break;
		case STATISTICS:
			break;
		default:
			break;
		}
	}
}
