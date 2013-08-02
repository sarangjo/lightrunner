using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace LightRunner_wp7
{
    class Line
    {
        float width; Color color; Vector2 pt1; Vector2 pt2;
        Texture2D point;

        public Line(GraphicsDevice GraphicsDev, float w, Color c, Vector2 p1, Vector2 p2)
        {
            point = new Texture2D(GraphicsDev, 1, 1, false, SurfaceFormat.Color);
            point.SetData(new[] { Color.White });

            width = w;
            color = c;
            pt1 = p1;
            pt2 = p2;
        }

        /// <summary>
        /// Awesome program to draw a line.
        /// </summary>
        /// <param name="spriteBatch">The SpriteBatch.</param>
        /// <param name="width">The width of the line.</param>
        /// <param name="color">The color of the line.</param>
        /// <param name="pt1">Point one of the line.</param>
        /// <param name="pt2">Point two of the line.</param>
        public void DrawLine(SpriteBatch spriteBatch)
        {
            float angle = (float)Math.Atan2(pt2.Y - pt1.Y, pt2.X - pt1.X);
            float length = Vector2.Distance(pt1, pt2);

            spriteBatch.Draw(point, pt1, null, color,
                        angle, Vector2.Zero, new Vector2(length, width),
                        SpriteEffects.None, 0);
        }
    }
}