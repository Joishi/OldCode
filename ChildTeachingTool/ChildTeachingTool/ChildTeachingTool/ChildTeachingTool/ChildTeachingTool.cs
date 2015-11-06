using ChildTeachingTool.GameComponents;
using ChildTeachingTool.GameComponents.Screens;
using ChildTeachingTool.Services;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ChildTeachingTool
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class ChildTeachingTool : Game
    {

        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;
        ITextRenderer textRenderer;
        ITextureRenderer textureRenderer;

        GameScreen menu;

        #region Constructors
        public ChildTeachingTool()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
            menu = new TopMenu(Services);

            IsMouseVisible = true;

            textRenderer = new TextRenderer();
            textureRenderer = new TextureRenderer();
//            Services.AddService(typeof(GraphicsDevice), GraphicsDevice);
            Services.AddService(typeof(ITextRenderer), textRenderer);
            Services.AddService(typeof(ITextureRenderer), textureRenderer);
        }
        #endregion

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            base.Initialize();
            // TODO: Add your initialization logic here
            Components.Add(menu);

            menu.Initialize();
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
            menu.LoadContent();
            menu.SpriteBatch = spriteBatch;
            textRenderer.SpriteBatch = spriteBatch;
            textureRenderer.SpriteBatch = spriteBatch;
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
            foreach (IMyGameComponent component in Components)
                if (component is IMyGameComponent)
                    ((IMyGameComponent)component).Update(gameTime);
            base.Update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);

            spriteBatch.Begin();
            foreach (IMyGameComponent component in Components)
                if (component is IMyDrawableGameComponent)
                    ((IMyDrawableGameComponent)component).Draw(gameTime);
            spriteBatch.End();

            base.Draw(gameTime);
        }
    }
}
