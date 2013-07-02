package com.RedmondRNDLabs.lightRunner;

import com.RedmondRNDLabs.framework.Graphics;
import com.RedmondRNDLabs.framework.Image;

// Basic Sprite class that will be extended.
public class Sprite {
	int width, height;
	double x, y;
	double velocity;

	public Sprite(double newx, double newy, int neww, int newh) {
		x = newx;
		y = newy;
		width = neww;
		height = newh;
	}

	public void paintRect(Graphics g, int color) {
		g.drawRect((int)x, (int)y, width, height, color);
	}

	public void paintImage(Graphics g, Image image) {
		g.drawImage(image, (int)x, (int)y);
	}

	// Gets
	public int getLeftBound() {
		return (int)x;
	}

	public int getRightBound() {
		return (int)x + width;
	}

	public int getCenterY() {
		return (int)y + height / 2;
	}

	// Sets
	public void setCenterY(int newCenter) {
		y = newCenter - height / 2;
	}

	public void setHeight(int newh) {
		height = newh;
	}
}
