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
    public class ShapeRenderer
    {
        public Color color;
        public float width = 1f;
        public GraphicsDevice GraphicsDev;
        List<Line> lines = new List<Line>();

        public ShapeRenderer(Color newC, float newW, GraphicsDevice graphicsDevice)
        {
            color = newC;
            width = newW;
            GraphicsDev = graphicsDevice;
        }

        public void setColor(Color newC)
        {
            color = newC;
        }
        public void setTriangle(float ax, float ay, float bx, float by, float cx, float cy)
        {
            lines.Add(new Line(GraphicsDev, width, color, new Vector2(ax, ay), new Vector2(bx, by)));
            lines.Add(new Line(GraphicsDev, width, color, new Vector2(bx, by), new Vector2(cx, cy)));
            lines.Add(new Line(GraphicsDev, width, color, new Vector2(cx, cy), new Vector2(ax, ay)));
        }

        /// <summary>
        /// Cannot be called without calling setTriangle().
        /// </summary>
        /// <param name="spriteBatch"></param>
        public void drawTriangle(SpriteBatch spriteBatch)
        {
            spriteBatch.Begin();
            foreach(Line l in lines)
            {
                l.DrawLine(spriteBatch);
            }
            spriteBatch.End();
        }

        public void filledRect(int x, int y, int width, int height, SpriteBatch spriteBatch)
        {
            spriteBatch.Begin();
            spriteBatch.Draw(Assets.pixel, new Vector2(x, y), null, color, 0, Vector2.Zero, new Vector2(width, height), SpriteEffects.None, 0);
            spriteBatch.End();
        }
    }
}
