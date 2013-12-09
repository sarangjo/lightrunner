package com.picotech.lightrunnerlibgdx;

import android.os.Bundle;
import android.provider.Settings.Secure;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.picotech.lightrunnerlibgdx.LightRunnerLibGDX;

public class MainActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;
		initialize(new LightRunnerLibGDX(), cfg);
	}
}