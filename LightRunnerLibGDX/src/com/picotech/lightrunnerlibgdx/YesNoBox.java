package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.math.Rectangle;

public class YesNoBox extends DialogBox {
	public YesNoBox(Rectangle newBounds, String newMessage) {
		super(newBounds, 2, newMessage, new String[] { "Yes", "No" });
	}
}
