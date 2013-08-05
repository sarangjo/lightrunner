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
    /// The World class holds all of the players, enemies and environment objects.
    /// It handles collisions and drawing methods, as well as loading content.
    /// </summary>

    public class World
    {
        public enum MenuState
        {
            PLAY, CHOOSESIDE
        }

        public MenuState menuState = MenuState.PLAY;
        public Player player;
        public Mirror mirror;
        public Light light;
        public Magnet magnet;
        public DebugOverlay debug;

        SpriteFont bf;

        float deltaTime, totalTime;
        float loadedContentPercent;

        int enemiesKilled;
        int score;
        int level;
        int powerupf = 2000;

        Vector2 ENEMY_VEL;
        Vector2 LightSource;

        public Rectangle playButton;
        public Rectangle topButton, rightButton, bottomButton;

        public bool menuScreen;
        public bool playSelected;
        public bool controlsSelected;
        public bool isClearScreen = false;
        public bool slowActivated = false;
        public bool isIncoming = false;
        public bool playedSound = false;
        public bool debugMode = true;
        public List<Enemy> enemies;
        public List<Enemy> enemiesAlive;

        List<Powerup> powerups;
        public static Dictionary<Powerup.Type, int> puhm = new Dictionary<Powerup.Type, int>();

        Color healthBar;

        Random r = new Random();

        #region WP7 variables
        ContentManager manager;
        GraphicsDevice graphicsDevice;
        #endregion

        /// <summary>
        /// There are two types of worlds, the menu world and the in-game world. The
        /// behavior of the light depends on whether the game is in the menu or
        /// playing state.
        /// </summary>
        public World(bool isMenu, ContentManager newCM, GraphicsDevice newGD)
        {
            level = 1;
            totalTime = 0;

            playButton = new Rectangle(390, 100, 500, 100);

            topButton = new Rectangle(190, 100, 300, 100);
            rightButton = new Rectangle(490, 100, 300, 100);
            bottomButton = new Rectangle(790, 100, 300, 100);

            enemies = new List<Enemy>();
            enemiesAlive = new List<Enemy>();
            powerups = new List<Powerup>();

            menuScreen = isMenu;
            player = new Player(new Vector2(0, 300), "characterDirection0.png");
            mirror = new Mirror(new Vector2(100, 300), "mirror.png");
            magnet = new Magnet(new Vector2(1280, 400), 48, 48, "magnet.png", .05f);

            debug = new DebugOverlay();

            if (menuScreen)
            {
                player = new Player(new Vector2(-100, -100),
                        "characterDirection0.png");
                magnet = new Magnet(new Vector2(-1000, 400), 48, 48, "magnet.png",
                        0);
                light = new Light(true);
                level = 40;
            }
            else
            {
                setLight();
                healthBar = new Color();
            }

            // Spawning enemies
            for (int i = 0; i < level; i++)
            {
                enemies.Add(new Enemy(new Vector2(r.Next(950) + 300, r
                        .Next(700)), 50, 50, level));
                enemies[enemies.Count - 1].loadContent(manager);
            }

            // Power-ups
            if (!menuScreen)
            {
                foreach (Powerup pu in powerups)
                    pu.loadContent(manager);
                powerupf = r.Next(500) + 1500;
            }
            // HashMap values
            puhm.Add(Powerup.Type.CLEARSCREEN, 5);
            puhm.Add(Powerup.Type.ENEMYSLOW, 12);
            puhm.Add(Powerup.Type.LIGHTMODIFIER, 15);
            puhm.Add(Powerup.Type.PRISMPOWERUP, 18);
            puhm.Add(Powerup.Type.INCOMINGACTIVE, 10);

            #region WP7 specific
            manager = newCM;
            graphicsDevice = newGD;
            #endregion
        }

        private void setLight()
        {
            Random r = new Random();
            if (GameScreen.scheme == GameScreen.LightScheme.TOP)
            {
                LightSource = new Vector2(r.Next(640) + 420, 720);
            }
            else if (GameScreen.scheme == GameScreen.LightScheme.RIGHT)
            {
                LightSource = new Vector2(1280, r.Next(700 + 10));
            }
            else if (GameScreen.scheme == GameScreen.LightScheme.BOTTOM)
            {
                LightSource = new Vector2(r.Next(640) + 420, 0);
            }

            light = new Light(LightSource, mirror.getCenter());
        }

        /// <summary>
        /// Loads all the content of the World.
        /// </summary>
        public void loadContent(ContentManager newManager)
        {
            manager = newManager;

            player.loadContent(manager);
            mirror.loadContent(manager);
            magnet.loadContent(manager);

            foreach (Powerup pu in powerups)
            {
                pu.loadContent(manager);
            }

            bf = manager.Load<SpriteFont>("bf");
            //bf.scale(1);
            //bf.setColor(Color.WHITE);

            if (debugMode)
                debug.loadContent(manager);
        }

        /// <summary>
        /// Updates the entire World. Includes light, enemy movement, and enemy
        /// destruction. Also updates the time functions for frame rate-independent
        /// functions deltaTime and totalTime (which are all in seconds).
        /// </summary>
        public void update(float newDeltaTime)
        {
            // Miscellaneous time updating functions.
            deltaTime = newDeltaTime;
            totalTime += deltaTime;

            if ((debug.nothingSelected && debugMode) || !debugMode)
            {
                player.update();
                mirror.rotateAroundPlayer(player.getCenter(),
                        (player.bounds.Width / 2) + 2
                                + (light.getOutgoingBeam().isPrism ? 40 : 0));
            }
            // Updating light, player, and the mirror.
            light.update(mirror, player);
            magnet.update();

            // Updates all enemies in "enemies".
            foreach (Enemy e in enemies)
            {
                e.update();
                for (int beam = (isIncoming) ? 0 : 1; beam < light.beams.Count; beam++)
                {
                    /*
                    if (Intersector.overlapConvexPolygons(
                            light.beams[beam].beamPolygon, e.p))
                    {
                        if (e.alive)
                        {
                            e.health--;
                            e.losingHealth = true;
                            Assets.hit.Play(.1f, 0f, 0f);
                        }
                        else
                        {
                            enemiesKilled++;
                        }
                    }
                    if (Intersector.overlapConvexPolygons(player.p, e.p))
                    {
                        if (player.alive)
                            player.health--;
                    }
                     */
                }
                // adds the number of enemies still alive to a new List
                if (e.alive)
                {
                    enemiesAlive.Add(e);
                    e.isSlow = slowActivated;
                }

                // magnets
                if (Vector2.Distance(e.getCenter(), magnet.getCenter()) < 500)
                {
                    e.velocity = magnet.getPull(e.getCenter());
                }
            }

            // Depending on the MenuState, it will either show the Play
            // button or the Top-Right-Bottom buttons.
            float dstX = light.getOutgoingBeam().dst.X;
            if (menuState == MenuState.CHOOSESIDE)
            {
                // Style 1: Manual light-source choosing.
                /*
                 * if (dstX > 17 && dstX < 433) { GameScreen.scheme =
                 * GameScreen.selectedScheme = GameScreen.LightScheme.TOP;
                 * //GameScreen.selectedScheme = GameScreen.LightScheme.TOP;
                 * controlsSelected = true; playBlip(); } else if (dstX > 465 &&
                 * dstX < 815) { GameScreen.scheme = GameScreen.selectedScheme =
                 * GameScreen.LightScheme.RIGHT; //GameScreen.selectedScheme =
                 * GameScreen.LightScheme.RIGHT; controlsSelected = true;
                 * playBlip(); } else if (dstX > 847 && dstX < 1200) {
                 * GameScreen.scheme = GameScreen.selectedScheme =
                 * GameScreen.LightScheme.BOTTOM; //GameScreen.selectedScheme =
                 * GameScreen.LightScheme.BOTTOM; controlsSelected = true;
                 * playBlip(); } else { controlsSelected = false; playedSound =
                 * false; }
                 */
                // Style 2: Randomized light-source choosing.

                int schemeN = r.Next(3) + 1;
                GameScreen.scheme = GameScreen.selectedScheme = (GameScreen.LightScheme)(schemeN);
                //GameScreen.scheme = GameScreen.selectedScheme = GameScreen.LightScheme
                //		.values()[schemeN];
                controlsSelected = true;
                playedSound = true;
                GameScreen.state = GameScreen.GameState.READY;
            }
            if (menuState == MenuState.PLAY)
            {
                if (dstX > playButton.X - 100
                        && dstX < playButton.X + playButton.Width + 100)
                {
                    playSelected = true;
                    playBlip();
                }
                else
                {
                    playSelected = false;
                    playedSound = false;
                }
            }

            // removes the "dead" enemies from the main List
            enemies = (List<Enemy>)enemies.Union<Enemy>(enemiesAlive);
            enemiesAlive.Clear();

            // temporarily spawns new enemies, which get progressively faster
            if (enemies.Count < level)
            {
                enemies.Add(new Enemy(new Vector2(1280, r.Next(700)), 50, 50,
                        level));
                enemies[enemies.Count - 1].isSlow = slowActivated;
                enemies[enemies.Count - 1].loadContent(manager);
            }

            // Time-wise level changing
            if (totalTime > 5 * level)
                level++;

            setScore();

            // Powerups.
            updatePowerups();

            // Debugging overlay.
            if (debugMode)
            {
                debug.update();
                if (debug.selectedButtons[0])
                {
                    Console.WriteLine("Changed mirror.");
                    if (mirror.type == Mirror.Type.CONVEX)
                        mirror.type = Mirror.Type.FLAT;
                    else if (mirror.type == Mirror.Type.FLAT)
                        mirror.type = Mirror.Type.FOCUS;
                    else if (mirror.type == Mirror.Type.FOCUS)
                        mirror.type = Mirror.Type.CONVEX;
                }
                else if (debug.selectedButtons[1])
                {
                    Console.WriteLine("Reset magnet.");
                    magnet.setCenter(new Vector2(1280, r.Next(720)));
                }
                else if (debug.selectedButtons[2])
                {
                    Console.WriteLine("Added powerup.");
                    addPowerup();
                }
                debug.resetButtons();
            }

        }

        public void setScore()
        {
            // Score algorithm
            score = (int)(totalTime * 10 + enemiesKilled * 5);
        }

        private void playBlip()
        {
            if (!playedSound)
            {
                Assets.blip.Play(.5f, 0, 0);
                playedSound = true;
            }
        }

        /// <summary>
        /// Handles all the power-up logic.
        /// </summary>
        private void updatePowerups()
        {
            // Randomizing spawns
            if ((int)(totalTime * 100) % powerupf == 0)
            {
                addPowerup();
            }

            for (int i = 0; i < powerups.Count; i++)
            {
                Powerup pu = powerups[0];
                pu.update(deltaTime);

                // Collision with player
                if (pu.position.X < player.position.X + player.bounds.Width
                        && pu.position.Y + pu.bounds.Height > player.position.Y
                        && pu.position.Y < player.position.Y + player.bounds.Height)
                {

                    switch (pu.type)
                    {
                        case Powerup.Type.LIGHTMODIFIER:
                            light.getOutgoingBeam().setWidth(Powerup.LM_WIDTH);
                            break;
                        case Powerup.Type.PRISMPOWERUP:
                            GameScreen.scheme = GameScreen.LightScheme.LEFT;
                            light.getOutgoingBeam().setWidth(Powerup.P_WIDTH);
                            mirror.setType(Mirror.Type.PRISM, "prism.png", manager);
                            break;
                        case Powerup.Type.ENEMYSLOW:
                            slowActivated = true;
                            foreach (Enemy e in enemies)
                                e.isSlow = true;
                            break;
                        case Powerup.Type.CLEARSCREEN:
                            isClearScreen = true;
                            for (int j = 0; j < enemies.Count; j++)
                            {
                                if (enemies[j].alive)
                                    enemiesKilled++;
                            }
                            setScore();
                            break;
                        case Powerup.Type.INCOMINGACTIVE:
                            isIncoming = true;
                            break;
                    }
                    pu.isActive = true;
                    pu.isAura = true;
                    pu.position = new Vector2(10000, 10000);
                }

                // Ending power-ups
                if (pu.timeActive > pu.timeOfEffect)
                {
                    pu.end();

                    switch (pu.type)
                    {
                        case Powerup.Type.LIGHTMODIFIER:
                            light.getOutgoingBeam().setWidth(Light.L_WIDTH);
                            break;
                        case Powerup.Type.PRISMPOWERUP:
                            GameScreen.scheme = GameScreen.selectedScheme;
                            setLight();
                            light.getOutgoingBeam().setWidth(Light.L_WIDTH);
                            light.getOutgoingBeam().isPrism = false;
                            mirror.setType(Mirror.Type.FLAT, "mirror.png", manager);
                            break;
                        case Powerup.Type.ENEMYSLOW:
                            slowActivated = false;
                            foreach (Enemy e in enemies)
                            {
                                e.isSlow = false;
                            }
                            break;
                        case Powerup.Type.CLEARSCREEN:
                            isClearScreen = false;
                            break;
                        case Powerup.Type.INCOMINGACTIVE:
                            isIncoming = false;
                            break;
                    }

                    powerups.RemoveAt(i);
                }
            }
            if (isClearScreen)
            {
                enemiesAlive.Clear();
                enemies.Clear();
            }
        }

        public void addPowerup()
        {
            /*
             * int x = r.nextInt(Powerup.Type.values().length); powerups.add(new
             * Powerup(new Vector2(1300, r.nextInt(600) + 50),
             * Powerup.Type.values()[x]));
             */
            powerups.Add(new Powerup(new Vector2(1300, r.Next(600) + 50),
                    Powerup.Type.INCOMINGACTIVE));
            powerups[powerups.Count - 1].loadContent(manager);
            powerupf = r.Next(500) + 2500;
        }

        /// <summary>
        /// Draws the entire world.
        /// </summary>
        /// <param name="batch">the SpriteBatch from WorldRenderer</param>
        /// <param name="sr">the ShapeRenderer to render light and enemies</param>
        public void draw(SpriteBatch batch, ShapeRenderer sr)
        {

            foreach (Enemy e in enemies)
                e.draw(batch);
            // e.draw(sr);

            if (menuScreen)
            { // this draws all the graphics for the menu
                if (menuState == MenuState.PLAY)
                {
                    //sr.begin(ShapeType.FilledRectangle);
                    if (playSelected)
                        sr.setColor(Color.White);
                    else
                        sr.setColor(Color.LightGray);
                    sr.filledRect(playButton.X, playButton.Y, playButton.Width,
                            playButton.Height, batch);
                    //sr.end();
                    batch.Begin();
                    //bf.setColor(Color.BLACK);
                    //bf.draw(batch, , 610, 160);
                    batch.DrawString(bf, "Play", new Vector2(610, 160), Color.Black);
                    batch.End();
                }
                else if (menuState == MenuState.CHOOSESIDE)
                {
                    //sr.begin(ShapeType.FilledRectangle);
                    sr.setColor(Color.LightGray);
                    sr.filledRect(topButton.X, topButton.Y, topButton.Width,
                            topButton.Height, batch);
                    sr.filledRect(rightButton.X, rightButton.Y, rightButton.Width,
                            rightButton.Height, batch);
                    sr.filledRect(bottomButton.X, bottomButton.Y,
                            bottomButton.Width, bottomButton.Height, batch);
                    if (GameScreen.scheme != GameScreen.LightScheme.NONE)
                    {
                        sr.setColor(Color.White);
                        if (GameScreen.scheme == GameScreen.LightScheme.TOP)
                        {
                            sr.filledRect(topButton.X, topButton.Y,
                                    topButton.Width, topButton.Height, batch);
                        }
                        else if (GameScreen.scheme == GameScreen.LightScheme.RIGHT)
                        {
                            sr.filledRect(rightButton.X, rightButton.Y,
                                    rightButton.Width, rightButton.Height, batch);
                        }
                        else if (GameScreen.scheme == GameScreen.LightScheme.BOTTOM)
                        {
                            sr.filledRect(bottomButton.X, bottomButton.Y,
                                    bottomButton.Width, bottomButton.Height, batch);
                        }
                    }
                    //sr.end();

                    batch.Begin();
                    //bf.setColor(Color.BLACK);
                    //bf.draw(batch, "Top", 290, 160);
                    //bf.draw(batch, "Right", 590, 160);
                    //bf.draw(batch, "Bottom", 890, 160);


                    batch.End();
                }
            }
            else
            { // this draws everything needed in game
                if (debugMode)
                    debug.draw(batch, sr);

                batch.Begin();
                player.draw(batch, mirror.angle - 90);
                mirror.draw(batch);
                magnet.draw(batch);

                // Text drawing
                //bf.setColor(Color.White);
                //bf.draw(batch, "Score: " + score, 0, 720);
                //bf.draw(batch, "Enemies Killed: " + enemiesKilled, 225, 720);
                //bf.draw(batch, "Level: " + level, 1000, 720);
                batch.DrawString(bf, "Score: " + score, new Vector2(0, 720), Color.White);
                batch.DrawString(bf, "Enemies Killed: " + enemiesKilled, new Vector2(225, 720), Color.White);
                batch.DrawString(bf, "Level: " + level, new Vector2(1000, 720), Color.White);

                // testing
                //bf.draw(batch, "pu: "
                //        + (powerups.size() > 0 ? powerups.get(0).timeActive
                //                : "No powerups."), 550, 720);
                batch.DrawString(bf, "pu: " + (powerups.Count > 0 ? powerups[0].timeActive + ""
                                : "No powerups."), new Vector2(550, 720), Color.White);
                batch.End();

                healthBar = new Color(1 - player.health / 100, player.health / 100, 0, 1);

                // drawing health bar
                //sr.begin(ShapeType.FilledRectangle);
                sr.setColor(healthBar);
                sr.filledRect(100, 20, player.health * 10, 10, batch);
                //sr.end();
            }

            light.draw(graphicsDevice, batch);

            // testing powerups
            if (!menuScreen)
                for (int i = 0; i < powerups.Count; i++)
                    powerups[i].draw(batch);
        }
    }

}