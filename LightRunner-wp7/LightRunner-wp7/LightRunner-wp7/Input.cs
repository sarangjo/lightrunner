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
    public class Input
    {
        /// <summary>
        /// Movement schemes.
        /// </summary>
        public enum Movement
        {
            DUALMOVE, MIRRORMOVE, PLAYERMOVE, REGIONMOVE
        }

        Movement ctrl;
        // The x and y values of the touch.
        public static int touchX, touchY;

        public Input()
        {
        }

        public Input(Movement newCtrl)
        {
            ctrl = newCtrl;
        }

        /// <summary>
        /// Handles single/dragged touches.
        /// </summary>
        /// <param name="world">the current world</param>
        /// <param name="width">width of the screen</param>
        /// <param name="height">height of the screen</param>
        /// <param name="touchX">x-value of the touch</param>
        /// <param name="touchY">y-value of the touch</param>
        /// <param name="state">the current state of the game</param>
        public void update(World world, int width, int height, int touchX, int touchY,
                GameScreen.GameState state)
        {
            //if (Gdx.input.isTouched())
            //{
            if (state == GameScreen.GameState.PLAYING)
            {
                switch (ctrl)
                {
                    case Movement.DUALMOVE:
                        // STYLE 1: Mobile controls
                        if (touchX < width / 6)
                        {
                            world.player.setCenterY(height - touchY);
                        }
                        else
                        {
                            // calculates and sets the mirror angle -- from the
                            // touch point to the mirror position
                            world.mirror.setMirrorAngle(world.mirror.getCenter(),
                                    new Vector2(touchX, height - touchY));
                        }
                        break;
                    case Movement.MIRRORMOVE:
                        // STYLE 2: Stationary controls
                        world.mirror.setMirrorAngle(world.mirror.getCenter(),
                                new Vector2(touchX, height - touchY));
                        world.mirror
                                .rotateAroundPlayer(
                                        world.player.getCenter(),
                                        (world.player.bounds.Width / 2)
                                                + 10
                                                + (world.light.getOutgoingBeam().isPrism ? 40
                                                        : 0));

                        break;
                    case Movement.PLAYERMOVE:
                        // STYLE 3: Stationary mirror, movable player
                        world.player.setCenterY(height - touchY);

                        break;
                    case Movement.REGIONMOVE:
                        // STYLE 4: Region around player governs movement, else
                        // mirror movement
                        if (height - touchY > world.player.getCenter().Y - 200
                                && height - touchY < world.player.getCenter().Y + 200
                                && touchX < width / 6)
                        {
                            world.player.follow(height - touchY
                                    - world.player.bounds.Height / 2);
                        }
                        else
                        {
                            world.mirror.setMirrorAngle(world.mirror.getCenter(),
                                    new Vector2(touchX, height - touchY));
                        }
                        world.mirror
                                .rotateAroundPlayer(
                                        world.player.getCenter(),
                                        (world.player.bounds.Width / 2)
                                                + 2
                                                + (world.light.getOutgoingBeam().isPrism ? 40
                                                        : 0));

                        break;
                }
            }
            else if (state == GameScreen.GameState.MENU)
            {
                // Sets the beam to pass through the touch.
                float X = (720 * (640 - touchX) / (/*height - */touchY));
                world.light.beams[1].updateIncomingBeam(
                        new Vector2(640 - X, 0), true, world.player);
            }

            //}
        }
    }

}
