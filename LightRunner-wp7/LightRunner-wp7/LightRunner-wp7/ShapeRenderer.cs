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
    class ShapeRenderer
    {
        public Color color;
        public float width = 1f;
        public GraphicsDevice GraphicsDev;
        public List<Line> lines = new List<Line>();

        public ShapeRenderer(Color newC, float newW, GraphicsDevice gd)
        {
            color = newC;
            width = newW;
            GraphicsDev = gd;
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
    }
}
