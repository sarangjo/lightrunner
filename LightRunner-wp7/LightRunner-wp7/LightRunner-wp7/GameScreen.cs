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
    /// This is the main type for your game
    /// The main class that governs the game. Contains WorldRenderer and World.
    /// </summary>
    public class GameScreen : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;

        /// <summary>
        /// The different states of the game.
        /// </summary>
        public enum GameState
        {
            LOADING, MENU, READY, PLAYING, PAUSED, GAMEOVER
        }

        /// <summary>
        /// Where the light comes from.
        /// </summary>
        public enum LightScheme
        {
            NONE, TOP, RIGHT, BOTTOM, LEFT
        }

        public static GameState state;
        public static LightScheme scheme = LightScheme.NONE;
        public static LightScheme selectedScheme;
        //public static Movement ctrl;

        private World world;
        private WorldRenderer renderer;
        private Input input;
        private int width, height;
        // Are these from the top-left corner or the bottom-left corner?
        // public static int touchX, touchY;
        
        SpriteFont font;
        LightBeam lb;

        public GameScreen()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";

            // Frame rate is 30 fps by default for Windows Phone.
            TargetElapsedTime = TimeSpan.FromTicks(333333);

            // Extend battery life under lock.
            InactiveSleepTime = TimeSpan.FromSeconds(1);

        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here
            state = GameState.LOADING;
            this.width = graphics.PreferredBackBufferWidth;
            this.height = graphics.PreferredBackBufferHeight;
            input = new Input(Input.Movement.REGIONMOVE);

            loadAssetsContent();
            
            MediaPlayer.Play(Assets.soundTrack);

            TouchPanel.EnabledGestures = GestureType.Tap;
            lb = new LightBeam(new Vector2(10, 10), 15, LightBeam.Type.INCOMING);

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // TODO: use this.Content to load your game content here
            //font = Content.Load<SpriteFont>("font");
            loadAssetsContent();
            renderer.loadContent(Content);
        }

        /// <summary>
        /// Loads all content in {@link Assets} in the game.
        /// </summary>
        public void loadAssetsContent() {
		    Assets.soundTrack = Content.Load<Song>("soundtrack.mp3");
		    Assets.blip = Content.Load<SoundEffect>("blip.wav");
		    Assets.hit = Content.Load<SoundEffect>("hit.wav");
		    Assets.died = Content.Load<SoundEffect>("dead.wav");

		    Assets.titleScreen = Content.Load<Texture2D>("LightRunnerTitle.png");
		    Assets.loadingScreen = Content.Load<Texture2D>("LoadingScreen.png");
		    Assets.pixel = Content.Load<Texture2D>("pixel.png");
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();
            if (TouchPanel.IsGestureAvailable)
            {
                GestureSample s = TouchPanel.ReadGesture();
            }

            #region GameScreen methods.
            if (state == GameState.LOADING)
            {
                world = new World(true, Content, GraphicsDevice);
                renderer = new WorldRenderer(world, GraphicsDevice);
                world.loadContent(Content);
                state = GameState.MENU;
            }
            else if (state == GameState.READY)
            {
                world = new World(false, Content, GraphicsDevice);
                renderer = new WorldRenderer(world, GraphicsDevice);
                state = GameState.PLAYING;
            }
            // to remove
            if (renderer.terminate)
            {
                state = GameState.LOADING;
            }

            renderer.update(0.00001f);
            #endregion

            // TODO: Add your update logic here
            lb.updateIncomingBeam(new Vector2(100, 100), false, new Player(new Vector2(0, 0), "player"));

            base.Update(gameTime);
        }

        public void resize(int width, int height)
        {
            width = graphics.PreferredBackBufferWidth;
            height = graphics.PreferredBackBufferHeight;
        }

        #region Input Controller
        
        public bool touchDown(int x, int y, int pointer, int button)
        {
            input.update(world, width, height, x, y, state);
            return false;
        }
        public bool touchUp(int screenX, int screenY, int pointer, int button)
        {
            Input.touchX = screenX;
            Input.touchY = height - screenY;
            if (state == GameState.MENU)
            {
                // Draws the light in the menu only when a touch is registered.
                world.light.getOutgoingBeam().updateIncomingBeam(
                        new Vector2(0, 720), true, world.player);
                if (world.playSelected)
                    world.menuState = World.MenuState.CHOOSESIDE;
                if (world.controlsSelected)
                    state = GameState.READY;
            }
            return true;
        }
        public bool touchDragged(int screenX, int screenY, int pointer)
        {
            input.update(world, width, height, screenX, screenY, state);
            return false;
        }

        #endregion

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(new Color(.1f, .1f, .1f, 1));

            renderer.render(state);
            //update();

            spriteBatch.Begin();
            // TODO: Add your drawing code here
            lb.draw(GraphicsDevice, spriteBatch);
            spriteBatch.DrawString(font, "Lol", new Vector2(0, 0), Color.White);

            spriteBatch.End();

            base.Draw(gameTime);
        }
    }
}
