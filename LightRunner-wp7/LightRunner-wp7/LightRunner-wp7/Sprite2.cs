using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Input.Touch;
using Microsoft.Xna.Framework.Media;
using Microsoft.Phone.Controls;

namespace LightRunner_wp7
{
    public class Sprite2
    {
        public Vector2 position = new Vector2();
        public Vector2 velocity;
        public Rectangle bounds = new Rectangle();
        Rectangle boundingRect = new Rectangle();
        //Polygon p;
        /// <summary>
        /// Rectangular vertices.
        /// </summary>
        float[] vertices = new float[8];
        public Texture2D texture;
        public String asset;

        public Sprite2(Vector2 Position, int newW, int newH)
        {
            this.position = Position;
            this.bounds.Width = newW;
            this.bounds.Height = newH;

            vertices[0] = Position.X;
            vertices[1] = Position.Y;
            vertices[2] = Position.X + bounds.Width;
            vertices[3] = Position.Y;
            vertices[4] = Position.X + bounds.Width;
            vertices[5] = Position.Y + bounds.Height;
            vertices[6] = Position.X;
            vertices[7] = Position.Y + bounds.Height;

            //p = new Polygon(vertices);
        }
        public Sprite2(Vector2 Position, int newW, int newH, String newAsset)
            : this(Position, newW, newH)
        {
            asset = newAsset;
        }
        public Sprite2(float x, float y, int newW, int newH, String newAsset)
            : this(new Vector2(x, y), newW, newH, newAsset)
        {
        }

        public Vector2 getCenter()
        {
            return new Vector2(position.X + bounds.Width / 2, position.Y
                    + bounds.Height / 2);
        }

        public void setCenter(float centerX, float centerY)
        {
            position.X = centerX - bounds.Width / 2;
            position.Y = centerY - bounds.Height / 2;
        }

        public void setCenter(Vector2 newCenter)
        {
            setCenter(newCenter.X, newCenter.Y);
        }

        public void setCenterY(float centerY)
        {
            position.Y = centerY - bounds.Height / 2;
        }

        public void setCenterX(float centerX)
        {
            position.X = centerX - bounds.Width / 2;
        }

        public void updateVertices()
        { }

        public void loadContent(ContentManager contentManager)
        {
            texture = contentManager.Load<Texture2D>("sample");
            bounds.Height = texture.Height;
            bounds.Width = texture.Width;
            updateVertices();
        }

        public void draw(SpriteBatch batch)
        {
            batch.Draw(texture, position, Color.White);
        }
    }
}
