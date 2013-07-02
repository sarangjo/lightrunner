package com.RedmondRNDLabs.lightRunner;

import com.RedmondRNDLabs.framework.Screen;
import com.RedmondRNDLabs.framework.implementation.AndroidGame;

public class TrickPong extends AndroidGame{

	@Override
	public Screen getInitScreen() {
		return new LoadingScreen(this);
	}

}
