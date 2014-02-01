package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DialogBox {
	public static int buttonHeight = 40, buffer = 18;

	public static enum DialogBoxType {
		YESNO, OK
	}

	public static enum DialogBoxSituation {
		MAINQUIT, GAMEQUIT, GAMERESTART, RESETDATA, DATARESET
	}

	public Rectangle bounds;
	public Rectangle[] buttons;
	public int nOfButtons;
	public String message;
	public String[] buttonText;
	/**
	 * This goes from 0 to n-1, n being the number of buttons. 
	 */
	public int defaultButton;

	public DialogBox(Rectangle newBounds, int newNOfButtons, String newMessage,
			String[] newButtonText, int newDefault) {
		bounds = newBounds;
		nOfButtons = newNOfButtons;
		buttons = new Rectangle[nOfButtons];
		message = newMessage;
		buttonText = newButtonText;
		defaultButton = newDefault;

		initializeRectangles();
	}

	public void initializeRectangles() {
		float buttonWidth = (bounds.width - ((nOfButtons + 1) * buffer))
				/ nOfButtons;
		for (int i = 0; i < nOfButtons; i++) {
			buttons[i] = new Rectangle(bounds.x + (i + 1) * (buffer) + i
					* buttonWidth, bounds.y + buffer, buttonWidth, buttonHeight);
		}
	}

	public void draw(SpriteBatch batch) {
		Assets.drawByPixels(batch, Assets.fullScreen, new Color(Color.BLACK.r,
				Color.BLACK.g, Color.BLACK.b, 0.5f));
		Assets.drawByPixels(batch, bounds, Color.GRAY);
		int i = 0;
		for (Rectangle rect : buttons) {
			Assets.drawByPixels(batch, rect, Color.WHITE);
			Assets.text(batch, buttonText[i], Assets.fontPos(rect, buttonText[i]), Color.BLACK);
			i++;
		}
		float bottom = 2 * buffer + buttonHeight;
		// float y = bounds.y + bottom + ((bounds.height - bottom)/2) -
		// Assets.fontHeight()/2;
		// float textWidth = message.length() * Assets.fontWidth();
		// float x = (bounds.width - textWidth)/2;
		// Assets.text(batch, message, Assets.getCenteredX(bounds.x,
		// bounds.width, textWidth),
		// Assets.getCenteredY(bounds.y + bottom, bounds.height - bottom,
		// Assets.fontHeight()));
		Assets.textWhite(batch, message,
				Assets.fontX(bounds.x, bounds.width, message),
				Assets.fontY(bounds.y, bounds.height + bottom/2));
	}

	public int touched() {
		if(bounds.contains(Input.touchUpPt)) {
			for (int i = 0; i < buttons.length; i++) {
				if (buttons[i].contains(Input.touchUpPt)) {
					return i;
				}
			}
			// The dialog box is touched, but not any of the buttons.
			return -1; 
		} else {
			// Outside the dialog box
			return defaultButton;
		}
	}
}