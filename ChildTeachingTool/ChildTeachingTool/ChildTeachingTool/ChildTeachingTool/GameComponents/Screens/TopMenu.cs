using ChildTeachingTool.GameComponents.Text;
using ChildTeachingTool.GameComponents.Texture;
using ChildTeachingTool.Services;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ChildTeachingTool.GameComponents.Screens
{
    public class TopMenu : GameScreen
    {
        protected MenuText text1;
        protected MenuText text2;
        protected DisplayText text3;
        protected Background background;

        #region Constructors
        public TopMenu(IServiceProvider services) : base(services)
        {
            text1 = new MenuText(services, "Fonts/MenuTextFont");
            text2 = new MenuText(services, "Fonts/MenuTextFont");
            text3 = new DisplayText(services, "Fonts/MenuTextFont");
            background = new Background(services, "Images/Backgrounds/TopMenuBackground");
        }
        #endregion

        public override void Initialize()
        {
//            ITextRenderer textRenderer = services;
            base.Initialize();
            LoadContent();
            initText(text1, "text1", new Vector2(100, 100), true, true, false, 300);
            initText(text2, "text2", new Vector2(100, 200), true, true, true, 300);
            initText(text3, "text3", new Vector2(100, 300), true, true, false, 300);
            background.Initialize();
            components.Add(background);
            components.Add(text1);
            components.Add(text2);
            components.Add(text3);
            //base.Initialize();
        }

        public override void LoadContent()
        {

        }

        public override void Update(GameTime gameTime)
        {
            MouseState mouseState = Mouse.GetState();
            text3.Text = "(" + mouseState.X + "," + mouseState.Y + ")";
            foreach (IMyGameComponent component in components)
                if (component is IMyGameComponent)
                    ((IMyGameComponent)component).Update(gameTime);
        }

        public override void Draw(GameTime gameTime)
        {
            foreach (IMyGameComponent component in components)
                if (component is IMyDrawableGameComponent)
                    ((IMyDrawableGameComponent)component).Draw(gameTime);
        }

        protected void initText(IText textObj, string text, Vector2 position, bool enabled, bool visible, bool drawAtCenter, int transSpeed)
        {
            textObj.Initialize();
            if (textObj is GameText)
            {
                ((GameText)textObj).Text = text;
                ((GameText)textObj).Position = position;
                ((GameText)textObj).Enabled = enabled;
                ((GameText)textObj).Visible = visible;
                ((GameText)textObj).DrawAtCenter = drawAtCenter;
            }
            if (textObj is MenuText)
            {
                ((MenuText)textObj).TransitionSpeedInMilliseconds = transSpeed;
            }
        }
    }
}
