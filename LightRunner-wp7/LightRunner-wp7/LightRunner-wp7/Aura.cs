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
    public class Aura : Sprite2
    {
        public float scale = 0.5f;
        public Vector2 powerupPos = new Vector2();
        public bool isFading = false;
        public float a = 1f;

        public const float BASEDIM = 100;
        public const float SCALE1 = 5f;
        public const float SCALE2 = 6.8f;

        public Aura(Vector2 newPos)
            : base(newPos, 30, 30, "circle.png")
        {
            powerupPos = newPos;
        }

        public void update()
        {
            scale += 0.25f;
            float dim = scale * BASEDIM;
            position = new Vector2(powerupPos.X + 25 - dim / 2, powerupPos.Y + 25 - dim / 2);
            //if (scale > SCALE1 && scale <= SCALE2) {
            //	a = 1 - ((scale - SCALE1) / (SCALE2 - SCALE1));
            //}
        }

        public void draw(SpriteBatch batch)
        {
            //batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, a);
            batch.Draw(texture, position, bounds, Color.White, 0f, Vector2.Zero, scale, SpriteEffects.None, 0f);
            //batch.setColor(Color.WHITE);
        }
    }

}