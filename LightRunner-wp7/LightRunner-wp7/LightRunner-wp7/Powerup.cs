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
    public class Powerup : Sprite2
    {
        // public Texture aura;
        public double totalTime;
        public float a;

        public double timeActive = 0;

        public enum Type
        {
            LIGHTMODIFIER, PRISMPOWERUP, ENEMYSLOW, CLEARSCREEN
        }

        public const int LM_WIDTH = 50;
        public const int P_WIDTH = 700;

        // Properties
        public float timeOfEffect;
        public Type type;
        public bool isActive = false;
        public bool isOver = false;

        public Powerup(Vector2 newPos, Type newType)
            : base(newPos, 10, 10, newType + ".png")
        {
            type = newType;
            velocity = new Vector2(-1, 0);
            //timeOfEffect = World.puhm.get(type);
        }

        public void update(float deltaTime)
        {
            if (!isOver)
            {
                position.X += velocity.X;
                position.Y += velocity.Y;

                totalTime += deltaTime;

                // Sinusoidal function for transparency
                a = (float)(0.5f + 0.5f * Math.Cos(totalTime * 10.0));

                if (isActive)
                {
                    timeActive += deltaTime;
                }
            }
        }

        public void end()
        {
            isOver = true;
            isActive = false;
            position = new Vector2(0, 0);
        }

        public void draw(SpriteBatch batch)
        {
            if (!isActive && !isOver)
            {
                batch.Begin();
                batch.Draw(texture, position, new Color(Color.White.R, Color.White.G, Color.White.B, a));
                batch.End();
            }
        }
    }

}