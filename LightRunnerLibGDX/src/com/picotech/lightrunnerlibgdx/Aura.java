package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Aura extends Sprite2 {
	public float scale = 0.5f;
	public Vector2 puPos = new Vector2();
	public boolean isFading = false;
	public float a = 1f;

	public static final float BASEDIM = 100;
	public static final float SCALE1 = 5f;
	public static final float SCALE2 = 6.8f;

	public Aura(Vector2 newPos) {
		super(newPos, 30, 30, "circle.png");
		puPos = newPos;
	}

	public void update() {
		scale += 0.25f;
		float dim = scale * BASEDIM;
		position = new Vector2(puPos.x + 25 - dim / 2, puPos.y + 25 - dim / 2);
		//if (scale > SCALE1 && scale <= SCALE2) {
		//	a = 1 - ((scale - SCALE1) / (SCALE2 - SCALE1));
		//}
	}

	@Override
	public void draw(SpriteBatch batch) {
		//batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, a);
		batch.draw(new TextureRegion(texture), position.x, position.y, 0, 0,
				bounds.width, bounds.height, scale, scale, 0);
		//batch.setColor(Color.WHITE);
	}
}
