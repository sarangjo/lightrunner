package com.RedmondRNDLabs.framework;

import com.RedmondRNDLabs.framework.Graphics.ImageFormat;

public interface Image {
	public int getWidth();
	public int getHeight();
	public ImageFormat getFormat();
	public void dispose();
}
