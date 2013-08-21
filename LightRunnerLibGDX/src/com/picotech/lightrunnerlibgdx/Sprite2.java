package com.picotech.lightrunnerlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Any sprite that is displayed on the screen.
 * @author Sarang Joshi
 */
public class Sprite2 {
	public Vector2 position = new Vector2();
	Vector2 velocity;
	Rectangle bounds = new Rectangle();
	Rectangle boundingRect = new Rectangle();
	Polygon p;
	/**
	 * Rectangular vertices.
	 */
	float[] vertices = new float[8];
	Texture texture;
	public String asset;

	public Sprite2()
	{
	}
	public Sprite2(int x, int y, int newW, int newH)
	{
		this(new Vector2(x, y), newW, newH);
	}
	public Sprite2(Vector2 Position, int newW, int newH) {
		this.position = Position;
		this.bounds.width = newW;
		this.bounds.height = newH;
		
		vertices[0] = Position.x;
		vertices[1] = Position.y;
		vertices[2] = Position.x + bounds.width;
		vertices[3] = Position.y;
		vertices[4] = Position.x + bounds.width;
		vertices[5] = Position.y + bounds.height;
		vertices[6] = Position.x;
		vertices[7] = Position.y + bounds.height;
		
		p = new Polygon(vertices);
	}
	
	public Sprite2(Vector2 Position, int newW, int newH, String newAsset) {
		this(Position, newW, newH);
		this.asset = newAsset;
	}

	public Sprite2(float x, float y, int newW, int newH, String newAsset) {
		this(new Vector2(x, y), newW, newH, newAsset);
		
		p = new Polygon(vertices);
	}

	public Vector2 getCenter() {
		return new Vector2(position.x + bounds.width / 2, position.y
				+ bounds.height / 2);
	}

	public void setCenter(float centerX, float centerY) {
		position.x = centerX - bounds.width / 2;
		position.y = centerY - bounds.height / 2;
	}

	public void setCenter(Vector2 newCenter) {
		setCenter(newCenter.x, newCenter.y);
	}

	public void setCenterY(float centerY) {
		position.y = centerY - bounds.height / 2;
	}

	public void setCenterX(float centerX) {
		position.x = centerX - bounds.width / 2;
	}

	/**
	 * Updates and sets the vertices of the polygon. Updates both vertices and
	 * p.
	 */
	public void updateVertices() {
		vertices[0] = position.x;
		vertices[1] = position.y;
		vertices[2] = position.x + bounds.width;
		vertices[3] = position.y;
		vertices[4] = position.x + bounds.width;
		vertices[5] = position.y + bounds.height;
		vertices[6] = position.x;
		vertices[7] = position.y + bounds.height;
		
		p = new Polygon(vertices);
		boundingRect = p.getBoundingRectangle();
	}

	/**
	 * Loads the texture of the sprite. Also sets the width and height of the
	 * bounds rectangle.
	 */
	public void loadContent() {
		texture = new Texture(Gdx.files.internal(asset));
		bounds.width = texture.getWidth();
		bounds.height = texture.getHeight();
		updateVertices();
	}

	public void draw(SpriteBatch batch) {
		if (asset == null)
			drawByPixels(batch);
		else
			batch.draw(texture, position.x, position.y, bounds.width, bounds.height);
	}
	public void drawByPixels(SpriteBatch batch)
	{
		batch.begin();
		batch.draw(new TextureRegion(Assets.pixel), position.x, position.y, 0, 0, 1, 1, bounds.width, bounds.height, 0);  //Draw(Assets.pixel, new Vector2(x, y), null, color, 0, Vector2.Zero, new Vector2(width, height), SpriteEffects.None, 0);
		batch.end();
	}
}
