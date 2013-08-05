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
    public class DebugOverlay
    {

        SpriteFont bf;

        List<Rectangle> debugOptions;
        public bool[] selectedButtons;
        public bool nothingSelected = true;
        Rectangle switchMirror = new Rectangle(1100, 600, 100, 25);
        Rectangle spawnMagnet = new Rectangle(1100, 550, 100, 25);
        Rectangle spawnPowerup = new Rectangle(1100, 500, 100, 25);

        public DebugOverlay()
        {
            debugOptions = new List<Rectangle>();
            debugOptions.Add(switchMirror);
            debugOptions.Add(spawnMagnet);
            debugOptions.Add(spawnPowerup);
            selectedButtons = new bool[debugOptions.Count];

        }

        public void loadContent(ContentManager manager)
        {
            bf = manager.Load<SpriteFont>("bf");
            //bf.setColor(Color.White);
        }

        public void update() {
		for(int button = 0; button < debugOptions.Count; button++){
			if(debugOptions[button].Contains(Input.touchX, Input.touchY)){
				selectedButtons[button] = true;
			}
		}
		nothingSelected = true;
		foreach (bool b in selectedButtons){
			if(b)
				nothingSelected = false;
		}
	}

        public void resetButtons()
        {
            for (int button = 0; button < selectedButtons.Length; button++)
            {
                if (!debugOptions[button].Contains(Input.touchX, Input.touchY))
                    selectedButtons[button] = false;
                Input.touchX = -1;
                Input.touchY = -1;
            }
        }

        public void draw(SpriteBatch batch, ShapeRenderer sr)
        {

            for (int rect = 0; rect < debugOptions.Count; rect++)
            {
                if (selectedButtons[rect])
                {
                    sr.setColor(Color.White);
                }
                else
                {
                    sr.setColor(Color.Gray);
                }
                sr.filledRect(debugOptions[rect].X, debugOptions[rect].Y, debugOptions[rect].Width, debugOptions[rect].Height, batch);
            }
            
            // Text drawing
            batch.Begin();
            
            //bf.draw(batch, "Switch mirrors!", switchMirror.x, switchMirror.y + 20);
            //bf.draw(batch, "Spawn magnet!", spawnMagnet.x, spawnMagnet.y + 20);
            //bf.draw(batch, "Spawn Powerup!", spawnPowerup.x, spawnPowerup.y + 20);
            batch.DrawString(bf, "Switch mirrors!", new Vector2(switchMirror.X, switchMirror.Y + 20), Color.White);
            batch.DrawString(bf, "Spawn magnet!", new Vector2(spawnMagnet.X, spawnMagnet.Y + 20), Color.White);
            batch.DrawString(bf, "Spawn Powerup!", new Vector2(spawnPowerup.X, spawnPowerup.Y + 20), Color.White);
            
            batch.End();
        }
    }

}
