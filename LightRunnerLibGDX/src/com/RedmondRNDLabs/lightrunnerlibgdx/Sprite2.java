package com.RedmondRNDLabs.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Sprite2 {
	Vector2 Position = new Vector2();
	Rectangle bounds = new Rectangle();
	Polygon p;
	float[] vertices = new float[8];
	Texture texture;
	String asset;

	public Sprite2(Vector2 Position, int newW, int newH, String newAsset) {
		this.Position = Position;
		this.bounds.width = newW;
		this.bounds.height = newH;
		this.asset = newAsset;
		p = new Polygon(vertices);
	}

	public Sprite2(float x, float y, int newW, int newH, String newAsset) {
		this(new Vector2(x, y), newW, newH, newAsset);
		p = new Polygon(vertices);
	}

	public Vector2 getCenter() {
		return new Vector2(Position.x + bounds.width / 2, Position.y
				+ bounds.height / 2);
	}

	public void setCenter(float centerX, float centerY) {
		Position.x = centerX - bounds.width / 2;
		Position.y = centerY - bounds.height / 2;
	}
	
	public void updateVertices(){
		vertices[0] = Position.x;
		vertices[1] = Position.y;
		vertices[2] = Position.x + bounds.width;
		vertices[3] = Position.y;
		vertices[4] = Position.x + bounds.width;
		vertices[5] = Position.y + bounds.height;
		vertices[6] = Position.x;
		vertices[7] = Position.x + bounds.height;
		
		p = new Polygon(vertices);
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
