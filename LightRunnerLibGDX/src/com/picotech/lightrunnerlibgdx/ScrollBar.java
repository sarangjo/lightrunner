package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ScrollBar extends Sprite2 {
	public Texture scroller;
	/**
	 * Value of the scrollbar from 0.0 to 1.0f.
	 */
	public float value;
	/**
	 * Width of the bar in pixels.
	 */
	public float barWidth;

	public ScrollBar(Vector2 newPosition, float initialValue, float newWidth) {
		super(newPosition.x, newPosition.y, "scrollBar.png");
		value = initialValue;
		barWidth = newWidth;
	}

	@Override
	public void loadContent() {
		super.loadContent();
		scroller = new Texture(Gdx.files.internal("scroller.png"));
		scale.x = barWidth / texture.getWidth();
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
		super.draw(batch);
		batch.draw(scroller, position.x + value
				* (scaledRect.width - scroller.getWidth()), position.y);
		batch.end();
	}
}
