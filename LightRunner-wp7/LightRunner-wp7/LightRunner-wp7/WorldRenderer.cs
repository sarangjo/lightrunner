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
    public class WorldRenderer
    {
        //public OrthographicCamera camera;
        private SpriteBatch batch;
        private World world;
        private ShapeRenderer sr;
        //private int width, height;
        public bool terminate = false;
        //private Texture titleScreen;

        public WorldRenderer(World world, GraphicsDevice graphicsDevice)
        {
            this.world = world;
            //width = Gdx.graphics.getWidth();
            //height = Gdx.graphics.getHeight();
            //camera = new OrthographicCamera(1, height / width);
            batch = new SpriteBatch(graphicsDevice);
            sr = new ShapeRenderer(Color.White, 1, graphicsDevice);
        }

        public void loadContent(ContentManager newCM)
        {
            //titleScreen = new Texture(Gdx.files.internal("LightRunnerTitle.png"));
            world.loadContent(newCM);
        }

        public void update(float newDeltaTime)
        {
            world.update(newDeltaTime);
        }

        public void render(GameScreen.GameState state)
        {
            world.draw(batch, sr);
            if (world.player.alive == false)
            {
                state = GameScreen.GameState.GAMEOVER;
                // to remove later
                terminate = true;
            }
            if (state == GameScreen.GameState.MENU)
            {
                batch.Begin();
                batch.Draw(Assets.titleScreen, new Vector2(150, 100), Color.White);
                batch.End();
            }
            else if (state == GameScreen.GameState.LOADING)
            {
                batch.Begin();
                batch.Draw(Assets.loadingScreen, new Vector2(0, 0), Color.White);
                batch.End();
            }
            else if (state == GameScreen.GameState.GAMEOVER)
            {

            }
        }
    }


}
