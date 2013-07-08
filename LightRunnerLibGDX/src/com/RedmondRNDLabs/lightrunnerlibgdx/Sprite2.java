package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite2 {
	Vector2 Position = new Vector2();
	Rectangle bounds = new Rectangle();
	Texture texture;
	String asset;

	public Sprite2(Vector2 Position, int newW, int newH, String newAsset) {
		this.Position = Position;
		this.bounds.width = newW;
		this.bounds.height = newH;
		this.asset = newAsset;
	}

	public Sprite2(float x, float y, int newW, int newH, String newAsset) {
		this(new Vector2(x, y), newW, newH, newAsset);
	}

	public Vector2 getCenter() {
		return new Vector2(Position.x + bounds.width / 2, Position.y
				+ bounds.height / 2);
	}

	public void setCenter(float centerX, float centerY) {
		Position.x = centerX - bounds.width / 2;
		Position.y = centerY - bounds.height / 2;
	}

	public void setCenterY(float centerY) {
		Position.y = centerY - bounds.height / 2;
	}

	public void loadContent() {
		texture = new Texture(Gdx.files.internal(asset));
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, Position.x, Position.y, bounds.width, bounds.height);
	}
}
