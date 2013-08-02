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

    /// <summary>
    /// Represents the collection of all LightBeams on the screen.
    /// </summary>
    public class Light
    {
        bool isMenu;
        
        /// <summary>
        /// An ArrayList representing the incoming and outgoing beams of light. <br>
        /// [0] is the incoming beam and [1] is the outgoing beam.
        /// </summary>
        List<LightBeam> beams = new List<LightBeam>();
        public const int L_WIDTH = 20;

        /// <summary>
        /// Initializes two LightBeams.
        /// <ul>
        /// <li>The first is from the source to the mirror's source.</li>
        /// <li>The second is from the mirror's source to (temporarily) x=0, y=0.</li>
        /// </summary>
        /// <param name="sourceOrigin">the source of the LightBeam at the edge of the screen.</param>
        /// <param name="mirrorCenter">the Vector2 representing the center of the mirror.</param>
        public Light(Vector2 sourceOrigin, Vector2 mirrorCenter)
        {
            // beam from source to player mirror
            beams.Add(new LightBeam(sourceOrigin, mirrorCenter, L_WIDTH, LightBeam.Type.INCOMING));
            // reflected beam; in this case, the origin is the destination vector of
            // the first beam
            beams.Add(new LightBeam(mirrorCenter, L_WIDTH, LightBeam.Type.OUTGOING));
        }

        /// <summary>
        /// The constructor used when it is currently the menu.
        /// </summary>
        /// <param name="isMenu">whether or not it is the menu.</param>
        public Light(bool isMenu)
        {
            this.isMenu = isMenu;
            beams.Add(new LightBeam(new Vector2(0, 0), new Vector2(0, 0), L_WIDTH, LightBeam.Type.INCOMING));
            beams.Add(new LightBeam(new Vector2(640, 720), new Vector2(640, 0), L_WIDTH, LightBeam.Type.OUTGOING));
        }

        /// <summary>
        /// Returns the incoming beam.
        /// </summary>
        /// <returns>the incoming beam</returns>
        public LightBeam getIncomingBeam()
        {
            return beams[0];
        }

        /// <summary>
        /// Returns the outgoing beam.
        /// </summary>
        /// <returns>the outgoing beam</returns>
        public LightBeam getOutgoingBeam()
        {
            return beams[1];
        }

        /// <summary>
        /// Calls the corresponding update() methods for LightBeam.
        /// </summary>
        /// <param name="mirror">The mirror.</param>
        /// <param name="player">The player.</param>
        public void update(Mirror mirror, Player player)
        {
            if (!isMenu)
            {
                if (mirror.type == Mirror.Type.CONVEX)
                {
                    if (beams.Count < 4)
                        beams.Add(new LightBeam(new Vector2(640, 720), new Vector2(640, 0), L_WIDTH, LightBeam.Type.OUTGOING));
                }
                beams[0].updateIncomingBeam(mirror.getCenter(), false, player);
                for (int beam = 1; beam < beams.Count; beam++)
                {
                    beams[beam].updateOutgoingBeam(beams[0], mirror.angle, mirror.type, beam);
                }
            }
        }

        /// <summary>
        /// Draws the light beams.
        /// </summary>
        /// <param name="gd">The GraphicsDevice.</param>
        /// <param name="spriteBatch">The spritebatch.</param>
        public void draw(GraphicsDevice gd, SpriteBatch spriteBatch)
        {
            foreach (LightBeam lb in beams)
            {
                lb.draw(gd, spriteBatch);
            }
        }

    }
}