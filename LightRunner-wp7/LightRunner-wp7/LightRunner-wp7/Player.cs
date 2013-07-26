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
    class Player : Sprite2
    {
        float speed = 5;
        float dstY;
        public const int MAX_HEALTH = 100;
        int health;
        bool alive = true;

        public Player(float x, float y, String asset)
            : base(new Vector2(x, y), 100, 100, asset)
        {
            dstY = y;
            health = MAX_HEALTH;
        }

        public Player(Vector2 Position, String asset)
            : base(Position, 100, 100, asset)
        {
            dstY = Position.Y;
            health = MAX_HEALTH;
        }

        public void draw(SpriteBatch batch, float angle)
        {
            batch.Draw(texture, position, bounds, Color.White, angle, Vector2.Zero, 1f, SpriteEffects.None, 0f);
        }

        public void update()
        {
            if (health <= 0)
            {
                alive = false;
            }
            updateVertices();
            if (dstY > position.Y + speed)
            {
                position.Y += speed;
            }
            else if (dstY < position.Y - speed)
            {
                position.Y -= speed;
            }
        }
        public void follow(float newDstY)
        {
            this.dstY = newDstY;
        }
    }

}