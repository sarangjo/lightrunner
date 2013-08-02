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

namespace LightRunner_wp7
{

    public class Enemy : Sprite2
    {
        /// <summary>
        /// Represents the different types of Enemies.
        /// </summary>
        static enum Type
        {
            NORMAL, FAST, RANDOM
        }

        Type type = Type.NORMAL;
        int health;
        int maxHealth;
        bool alive;
        bool losingHealth;
        bool normalizedVelocity;
        bool isSlow = false;
        Random r = new Random();

        /// <summary>
        /// Initializes a new enemy at a particular level.
        /// </summary>
        /// <param name="Position">the new position</param>
        /// <param name="newW">the width of the enemy</param>
        /// <param name="newH">the height of the enemy</param>
        /// <param name="level">the level of the enemy</param>
        public Enemy(Vector2 Position, int newW, int newH, int level)
            : base(Position, newW, newH)
        {
            float enemySpawning = (float)(r.Next(30 - (11 - level / 3)) + (11 - level / 3)); //  MathUtils.random(11 - level / 3, 30);
            if (enemySpawning < 10 && enemySpawning > 3)
            {
                type = Type.FAST;
                maxHealth = 25;
                velocity = new Vector2(-7.5f, (float)(r.NextDouble() * 2) - 1);// MathUtils.random(-.1f, .1f));
            }
            else if (enemySpawning <= 3)
            {
                type = Type.RANDOM;
                maxHealth = 10;
                velocity = new Vector2(-5.0f, 0);
            }
            else
            {
                type = Type.NORMAL;
                maxHealth = 50;
                velocity = new Vector2(-1.0f, (float)(r.NextDouble() * 4) - 2f); //MathUtils.random(-.2f, .2f));
            }
            asset = "enemy.png";
            alive = true;
            health = maxHealth;
        }

        /// <summary>
        /// Updates the enemy, moving it and checking if it is still alive.
        /// </summary>
        public void update()
        {
            losingHealth = false;

            // played around with some sinusoidal functions for the random blocks
            if (type == Type.RANDOM)
            {
                velocity.X += (float)Math.Sin(position.X / 100);
                velocity.Y += (float)Math.Sin(position.Y / 25);
            }

            if (isSlow)
            {
                if (type == Type.NORMAL)
                {
                    velocity.X = -0.2f;
                }
                else if (type == Type.FAST)
                {
                    velocity.X = -1.5f;
                }
                else if (type == Type.RANDOM)
                {
                    velocity.X /= 5f;
                }
                normalizedVelocity = false;
            }
            else if (!normalizedVelocity)
            {
                if (type == Type.NORMAL)
                {
                    velocity = new Vector2(-1.0f, (float)(r.NextDouble() * 4) - 2);//MathUtils.random(-.2f, .2f));
                }
                else if (type == Type.FAST)
                {
                    velocity = new Vector2(-7.5f, (float)(r.NextDouble() * 2) - 1);//MathUtils.random(-.1f, .1f));
                }
                normalizedVelocity = true;
            }

            position.X += velocity.X;
            position.Y += velocity.Y;
            if (health <= 5 || position.X + bounds.Width < 0
                    || position.Y + bounds.Height < 0
                    || position.Y - bounds.Height > 720)
            {
                alive = false;
                Assets.died.Play();
            }
            updateVertices();
        }

        /// <summary>
        /// Draws the Enemy based on the texture enemy.png.
        /// </summary>
        public void draw(SpriteBatch batch)
        {
            if (alive)
            {
                batch.Begin();
                if (type == Type.NORMAL)
                {
                    batch.Draw(texture, position, new Color((health - 5) / 5f, 0, 0));
                }
                else if (type == Type.FAST)
                {
                    batch.Draw(texture, position, new Color(0, (health - 5) / 5f, 0));
                }
                else if (type == Type.RANDOM)
                {
                    batch.Draw(texture, position, new Color(0, 0, (health - 5) / 5f));
                }
                if (losingHealth || health < maxHealth)
                {
                    Vector2 barPosition = new Vector2(position.X, position.Y + bounds.Height + 2);
                    batch.Draw(Assets.pixel, barPosition, new Rectangle((int)barPosition.X, (int)barPosition.Y, 1, 1), Color.White,
                        0f, Vector2.Zero, new Vector2(((health / (float)maxHealth) * bounds.Width), 5), SpriteEffects.None, 0f);
                }
                batch.End();
            }
        }
    }

}