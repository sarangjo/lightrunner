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
    class Sprite2
    {
        public Vector2 Position;
        public Texture2D texture;
        public Rectangle bounds;

        public Sprite2(Vector2 newPos)
        {
            Position = newPos;
        }
        
        private void updateVertices()
        {
            
        }

        public void loadContent(ContentManager contentManager)
        {
            texture = contentManager.Load<Texture2D>("sample");
            bounds.Height = texture.Height;
            bounds.Width = texture.Width;
            updateVertices();
        }

        public void draw(SpriteBatch batch)
        {
            batch.Draw(texture, Position, Color.White);
        }
    }
}
