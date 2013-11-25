package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	public float scaledScrollerWidth;

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
		scaledScrollerWidth = scroller.getWidth() * GameScreen.defS.x;
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.begin();
		//super.draw(batch);
		batch.draw(new TextureRegion(texture), position.x, position.y, 0, 0,
				texture.getWidth(), texture.getHeight(), scale.x * GameScreen.defS.x,
				scale.y * GameScreen.defS.y, 0f);
		batch.draw(new TextureRegion(scroller), position.x + value
				* (scaledRect.width - scaledScrollerWidth), position.y, 0, 0,
				scroller.getWidth(), scroller.getHeight(), GameScreen.defS.x,
				GameScreen.defS.y, 0f);
		/*
		 * batch.draw(scroller, position.x + value (scaledRect.width -
		 * scroller.getWidth()), position.y);
		 */
		batch.end();
	}

	public float touched(float x, float y) {
		if ((x >= scaledRect.x + scaledScrollerWidth / 2)
				&& (x <= scaledRect.x + scaledRect.width - scaledScrollerWidth
						/ 2)) {
			// diffX is the difference in x-values of the touch and the
			// leftmost point of the scroll bar.
			float diffX = Input.touchX - (position.x + scaledScrollerWidth / 2);
			return (diffX / (scaledRect.width - scroller.getWidth()));
		} else {
			// At this point it is certain that the down touch was in
			// the region, and the dragged touch is now being
			// registered. So, the music can be set even if the x value
			// of the touch is outside the scaledRect.
			if (Input.touchX < scaledRect.x) {
				return 0;
			} else if (Input.touchX > (scaledRect.x + scaledRect.width)
					* GameScreen.defS.x) {
				return 1;
			}
		}
		return -1;
	}
}
