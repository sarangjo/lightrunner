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
    public class Mirror : Sprite2
    {
        public float distance;

        public enum Type
        {
            FLAT, FOCUS, CONVEX, PRISM
        }

        Type type = Type.FLAT;
        float angle = 0f;

        public Mirror(float x, float y, int newW, int newH, String asset)
            : base(x, y, newW, newH, asset)
        {
        }

        public Mirror(Vector2 Position, String asset) :
            base(Position, 100, 100, asset)
        {
        }

        public void setMirrorAngle(Vector2 src, Vector2 dst)
        {
            angle = (float)(Math.Atan((dst.Y - src.Y) / (dst.X - src.X)) * 180 / Math.PI);
        }
        public void setMirrorDistance(float newD)
        {
            distance = newD;
        }

        public void setType(Type type, String asset, ContentManager contentManager)
        {
            this.type = type;
            this.asset = asset;
            loadContent(contentManager);
        }

        public void rotateAroundPlayer(Vector2 playerVector, float newD)
        {
            setMirrorDistance(newD);
            setCenterX(playerVector.X + (float)(newD * Math.Cos(MathHelper.ToRadians(angle))));
            setCenterY(playerVector.Y + (float)(newD * Math.Sin(MathHelper.ToRadians(angle))));
        }

        public void draw(SpriteBatch batch)
        {
            batch.Draw(texture, position, bounds, Color.White, angle, Vector2.Zero, 1f, SpriteEffects.None, 0f);
        }

    }

}