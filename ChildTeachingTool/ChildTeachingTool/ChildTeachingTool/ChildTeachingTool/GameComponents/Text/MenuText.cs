using ChildTeachingTool.StatesAndProperties;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Text
{
    public class MenuText : GameText
    {
        #region Fields
        protected int transitionRateMilliseconds; // THE NUMBER OF MILLISECONDS THAT IT WILL TAKE TO GROW/SHRINK
        protected Vector2 minScale; // THE MINIMUM SIZE THE TEXT WILL SHRINK TO
        protected Vector2 maxScale; // THE MAXIMUM SIZE THE TEXT WILL GROW TO
        #endregion

        #region Properties
        public int TransitionSpeedInMilliseconds
        {
            get { return transitionRateMilliseconds; }
            set { transitionRateMilliseconds = value; }
        }
        public Vector2 MinScale
        {
            get { return minScale; }
            set { minScale = value; }
        }
        public Vector2 MaxScale
        {
            get { return maxScale; }
            set { maxScale = value; }
        }
        #endregion

        #region Events
        #endregion

        public MenuText(IServiceProvider services, string spriteFontString) : base(services, spriteFontString)
        {
            minScale = new Vector2(1f, 1f);
            maxScale = new Vector2(1.2f, 1.2f);
            transitionRateMilliseconds = 1000;
        }

        public override void Update(GameTime gameTime)
        {
            base.Update(gameTime);
            if (enabled)
            {
                float milliseconds = gameTime.ElapsedGameTime.Milliseconds;
                float percentSizeChange = (milliseconds) / transitionRateMilliseconds;
                Vector2 scaleChange = new Vector2((maxScale.X - minScale.X) * percentSizeChange, (maxScale.Y - minScale.Y) * percentSizeChange);

                MouseState mouseState = Mouse.GetState();

                if (isMouseOver(mouseState.X, mouseState.Y))
                {
                    scale.X = Math.Min(scale.X + scaleChange.X, maxScale.X);
                    scale.Y = Math.Min(scale.Y + scaleChange.Y, maxScale.Y);
                }
                else
                {
                    scale.X = Math.Max(scale.X - scaleChange.X, minScale.X);
                    scale.Y = Math.Max(scale.Y - scaleChange.Y, minScale.Y);
                }
            }
        }

        public override void Draw(GameTime gameTime)
        {
            base.Draw(gameTime);
        }

        /*
        public override void Update(GameTime gameTime)
        {

            if (isMouseOn(mouseState.X, mouseState.Y))
            {
                if (state != TextState.TransitionOn && state != TextState.Active)
                    state = TextState.TransitionOn;
            }
            else
            {
                if (state != TextState.TransitionOff && state != TextState.Inactive)
                    state = TextState.TransitionOff;
            }

            if (state == TextState.TransitionOn)
            {
                dimensions.X += (maxDimensions.X - baseDimensions.X) * percentSizeChange;
                dimensions.Y += (maxDimensions.Y - baseDimensions.Y) * percentSizeChange;
                if (dimensions.X >= maxDimensions.X) dimensions.X = maxDimensions.X;
                if (dimensions.Y >= maxDimensions.Y) dimensions.Y = maxDimensions.Y;
                if (isMaxSize())
                    state = TextState.Active;
            }
            else if (state == TextState.TransitionOff)
            {
                dimensions.X -= (baseDimensions.X - minDimensions.X) * percentSizeChange;
                dimensions.Y -= (baseDimensions.Y - minDimensions.Y) * percentSizeChange;
                if (dimensions.X <= minDimensions.X) dimensions.X = minDimensions.X;
                if (dimensions.Y <= minDimensions.Y) dimensions.Y = minDimensions.Y;
                if (isMinSize())
                    state = TextState.Inactive;
            }
            else if (state == TextState.Active)
            {

            }
            else if (state == TextState.Inactive)
            {

            }
        }
        */
    }
}
