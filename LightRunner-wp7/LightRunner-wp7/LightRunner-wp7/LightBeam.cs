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
    ///<summary>
    /// A light beam on the screen. It can be either incoming or outgoing.
    /// author: Daniel Fang
    ///</summary>
    public class LightBeam
    {
        public enum Type
        {
            INCOMING, OUTGOING
        }

        Type type;
        int strength;
        int convexBeamSpread = 10;
        Vector2 origin;
        Vector2 dst;
        float angle;
        float beamLength = 1300;
        int width = 20;

        Color lightColor = Color.Yellow;
        ///<summary>
        /// An array of 6 elements that represents the 3 vertices of the LightBeam. <br>
        /// For outgoing beams, values 2&3 are the bottom point, values 4&5 are the top point.
        ///</summary>
        float[] beamVertices = new float[6];
        //Polygon beamPolygon = new Polygon(beamVertices);
        /// <summary>
        /// An ArrayList of Vector2's that represent the vertices of the LightBeam.
        /// </summary>
        List<Vector2> vectorPolygon = new List<Vector2>();

        bool polygonInstantiated = false;
        bool isPrism = false;

        ///<summary>
        /// Represents the 6 points that (with the top and bottom points) create the prism.
        ///</summary>
        float[] prismVertices = new float[12];

        ///<summary>
        /// Default constructor with the destination being (0,0).
        ///</summary>
        ///<param name="newOrigin">the origin of the beam</param>
        ///<param name="newW">the width of the beam</param>
        ///<param name="newT">the type of the beam</param>
        public LightBeam(Vector2 newOrigin, int newW, Type newT)
            : this(newOrigin, new Vector2(0, 0), newW, newT)
        {
            // origin = newOrigin;
            // dst = new Vector2(0, 0);
            // for (int i = 0; i < 3; i++) {
            // vectorPolygon.add(new Vector2(0, 0));
            // }
            // setWidth(newW);
        }

        ///<summary>
        /// Constructor that sets both origin and destination vectors.
        ///</summary>
        ///<param name="newOrigin">the new origin vector2</param>
        ///<param name="newDst">the new destination vector2</param>
        ///<param name="newW">the width of the beam</param>
        ///<param name="newT">the type of the beam</param>
        public LightBeam(Vector2 newOrigin, Vector2 newDst, int newW, Type newT)
        {
            origin = newOrigin;
            dst = newDst;
            for (int i = 0; i < 3; i++)
            {
                vectorPolygon.Add(new Vector2(0, 0));
            }
            setWidth(newW);
            type = newT;
        }

        ///<summary>
        /// Calculates the orientation, position, and size of the beam if it is an 
        /// incoming one. The polygon points are predetermined to set the faux
        /// 'origin' of the beam to be "width" pixels wide.
        ///</summary>
        ///<param name="newDst">the destination of the incoming beam</param>
        ///<param name="isMenu">whether it is currently Menu screen or not.</param>
        ///<param name="player">the current player object</param>
        public void updateIncomingBeam(Vector2 newDst, bool isMenu, Player player)
        {
            dst = newDst;
            calculateAngle();

            // This is the algorithm for incoming beams coming from the top.
            // The x value of the first point of the polygon is offset left by
            // 10 at the top edge.
            beamVertices[0] = origin.X - width / 2;
            beamVertices[1] = origin.Y;

            // The x value of the second point is offset right by 10 to create a
            // triangle.
            beamVertices[2] = origin.X + width / 2;
            beamVertices[3] = origin.Y;

            if (!isMenu)
            {
                setVertices(player);
            }
            beamVertices[4] = dst.X;
            beamVertices[5] = dst.Y;

            //beamPolygon = new Polygon(beamVertices);

        }

        public void setVertices(Player player)
        {
            if (GameScreen.scheme == GameScreen.LightScheme.TOP)
            {
                beamVertices[0] = origin.X - width / 2;
                beamVertices[1] = origin.Y;

                beamVertices[2] = origin.X + width / 2;
                beamVertices[3] = origin.Y;
            }
            else if (GameScreen.scheme == GameScreen.LightScheme.LEFT)
            {
                origin = new Vector2(player.position.X, player.position.Y
                        + player.bounds.Height / 2);
            }
            if (GameScreen.scheme == GameScreen.LightScheme.RIGHT
                    || GameScreen.scheme == GameScreen.LightScheme.LEFT)
            {
                beamVertices[0] = origin.X;
                beamVertices[1] = origin.Y + width / 2;

                beamVertices[2] = origin.X;
                beamVertices[3] = origin.Y - width / 2;
            }
            else if (GameScreen.scheme == GameScreen.LightScheme.BOTTOM)
            {
                beamVertices[0] = origin.X - width / 2;
                beamVertices[1] = origin.Y;

                // The x value of the second point is offset right by 10 to
                // create a triangle.
                beamVertices[2] = origin.X + width / 2;
                beamVertices[3] = origin.Y;
            }
        }

        ///<summary>
        /// Calculates the orientation, location, and size of the beam if it is an
        /// outgoing beam.
        ///</summary>
        ///<param name="sourceBeam">the source beam</param>
	    ///<param name="mirrorAngle">the angle of the mirror</param>
        ///<param name="type">the type of the mirror</param>
        ///<param name="lightBehavior">the type of the mirror, to determine the behavior of the beam</param>        
        public void updateOutgoingBeam(LightBeam sourceBeam, float mirrorAngle,
                Mirror.Type type, int beamNumber)
        {
            origin = sourceBeam.dst;

            // calculates the angle of all reflecting beams depending on mirror type
            if (type == Mirror.Type.FLAT)
            {
                angle = MathHelper.ToRadians((2 * mirrorAngle - sourceBeam.angle));
            }
            else if (type == Mirror.Type.FOCUS)
            {
                angle = MathHelper.ToRadians(mirrorAngle);
            }
            else if (type == Mirror.Type.CONVEX)
            {
                angle = MathHelper.ToRadians(mirrorAngle + (convexBeamSpread * (beamNumber - 2)));
                //width = 300;
                //angle = mirrorAngle * MathUtils.degreesToRadians;
            }

            // trigonometry to calculate where the outgoing beam ends, which varies
            // with the beamLength
            dst.X = origin.X + (float)(Math.Cos(angle) * beamLength);
            dst.Y = origin.Y + (float)(Math.Sin(angle) * beamLength);

            beamVertices[0] = origin.X;
            beamVertices[1] = origin.Y;

            beamVertices[2] = dst.X;
            beamVertices[3] = dst.Y - width / 2;

            beamVertices[4] = dst.X;
            beamVertices[5] = dst.Y + width / 2;

            if (isPrism)
            {
                setPrismVertices();
            }

            vectorPolygon[0] = new Vector2(beamVertices[0], beamVertices[1]);
            vectorPolygon[1] = new Vector2(beamVertices[2], beamVertices[3]);
            vectorPolygon[2] = new Vector2(beamVertices[4], beamVertices[5]);

            //beamPolygon = new Polygon(beamVertices);
            // boundingRect = beamPolygon.getBoundingRectangle();
        }

        ///<summary>
        /// Sets the prismVertices array to the appropriate values for the 
        /// vertices.
        ///</summary>
        private void setPrismVertices()
        {
            for (int i = 0; i < 6; i++)
            {
                // x-values are all the same as the destination Vector.
                prismVertices[2 * i] = dst.X;
                // y-values are 1/7th the width up from the base.
                prismVertices[2 * i + 1] = dst.Y - width / 2 + (i + 1) * width / 7;
            }
        }

        public void setWidth(int newWidth)
        {
            width = newWidth;
        }

        /// <summary>
        /// Draws the light beam.
        /// </summary>
        /// <param name="gd">The graphics device.</param>
        /// <param name="spriteBatch">The SpriteBatch</param>
        public void draw(GraphicsDevice gd, SpriteBatch spriteBatch)
        {
            ShapeRenderer sr = new ShapeRenderer(Color.White, 1f, gd);
            if (isPrism && type == Type.OUTGOING)
            {
                // First triangle is red. Points 2&3 from beamVertices, 0&1 from
                // prismVertices.
                sr.color = Color.Red;
                sr.setTriangle(beamVertices[0], beamVertices[1], 
                    beamVertices[2], beamVertices[3],
                    prismVertices[0], prismVertices[1]);
                sr.drawTriangle(spriteBatch);
                // Orange.
                // prismVertices: Points 0&1 and 2&3
                sr.setColor(Color.Orange);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[0], prismVertices[1], prismVertices[2],
                        prismVertices[3]);
                // Yellow.
                // prismVertices: Points 2&3 and 4&5
                sr.setColor(Color.Yellow);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[2], prismVertices[3], prismVertices[4],
                        prismVertices[5]);
                // Green.
                // prismVertices: Points 4&5 and 6&7
                sr.setColor(Color.Green);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[4], prismVertices[5], prismVertices[6],
                        prismVertices[7]);
                // Cyan.
                // prismVertices: Points 6&7 and 8&9
                sr.setColor(Color.Cyan);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[6], prismVertices[7], prismVertices[8],
                        prismVertices[9]);
                // Blue.
                // prismVertices: Points 8&9 and 10&11
                sr.setColor(Color.Blue);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[8], prismVertices[9], prismVertices[10],
                        prismVertices[11]);
                // Violet.
                // prismVertices: Points 10&11
                // beamVertices: Points 4&5
                sr.setColor(new Color(143, 0, 255, 1));
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        prismVertices[10], prismVertices[11], beamVertices[4],
                        beamVertices[5]);

            }
            else
            {
                // Regular light.
                lightColor.A = (byte)(.1);
                sr.setColor(lightColor);
                sr.setTriangle(beamVertices[0], beamVertices[1],
                        beamVertices[2], beamVertices[3], beamVertices[4],
                        beamVertices[5]);
            }

        }

        /// <summary>
        /// Calculates the angle of the incoming beam, independent of mirror angle.
        /// </summary>
        public void calculateAngle()
        {
            angle = (float)(Math.atan((dst.y - origin.y) / (dst.x - origin.x)) * 180 / Math.PI);
        }
    }

}