package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DialogBox {
	public static final int buttonHeight = 40, buffer = 18;

	public static enum DialogBoxType {
		YESNO
	}
	public static enum DialogBoxSituation {
		MAINQUIT, GAMEQUIT, GAMERESTART
	}
	
	
	public Rectangle bounds;
	public Rectangle[] buttons;
	public int nOfButtons;
	public String message;
	public String[] buttonText;

	public DialogBox(Rectangle newBounds, int newNOfButtons,
			String newMessage, String[] newButtonText) {
		bounds = newBounds;
		nOfButtons = newNOfButtons;
		buttons = new Rectangle[nOfButtons];
		message = newMessage;
		buttonText = newButtonText;

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
		Assets.drawByPixels(batch, Assets.fullScreen, new Color(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 0.5f));
		Assets.drawByPixels(batch, bounds, Color.GRAY);
		int i = 0;
		for (Rectangle rect : buttons) {
			Assets.drawByPixels(batch, rect, Color.WHITE);
			Assets.text(batch, buttonText[i], rect.x, rect.y, Color.BLACK);
			i++;
		}
		float bottom = 2 * buffer + buttonHeight;
		float y = bounds.y + bottom + ((bounds.height - bottom)/2) - Assets.fontHeight()/2;
		Assets.text(batch, message, bounds.x, y);
	}

	public int touched() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(Input.touchUpPt)) {
				return i;
			}
		}
		return -1;
	}
}
