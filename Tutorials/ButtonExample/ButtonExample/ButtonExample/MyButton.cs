using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;


namespace ButtonExample
{

    public class ButtonEventArgs : EventArgs
    {
        public enum eButtonEvent
        {
            TransitionOn,
            TransitionOff,
            MouseOn,
            MouseOff,
            Clicked
        }

        public eButtonEvent eventType { get; set; }
        public string eventMessage { get; set; }

        public ButtonEventArgs(eButtonEvent eventType, string eventMessage) {
            this.eventType = eventType;
            this.eventMessage = eventMessage;
        }
    }

    /// <summary>
    /// This is a game component that implements IUpdateable.
    /// </summary>
    public class MyButton : DrawableGameComponent
    {

        #region Event Handling

        public EventHandler<ButtonEventArgs> listener;

        protected virtual void RaiseEvent(ButtonEventArgs e)
        {
            if (listener != null)
                listener(this, e);
        }

        protected virtual void MouseOn()
        {
            RaiseEvent(new ButtonEventArgs(ButtonEventArgs.eButtonEvent.MouseOn, "MouseOn"));
        }

        protected virtual void MouseOff()
        {
            RaiseEvent(new ButtonEventArgs(ButtonEventArgs.eButtonEvent.MouseOff, "MouseOff"));
        }

        protected virtual void Clicked()
        {
            RaiseEvent(new ButtonEventArgs(ButtonEventArgs.eButtonEvent.Clicked, "Clicked"));
        }

        #endregion

        protected SpriteFont buttonFont;
        protected Vector2 position;
        protected Vector2 dimensions;
        bool transitionOn;
        bool mouseOn;
        Vector2 testPos = new Vector2(300, 300);
        MouseState mouseState;
        Vector2 testPos2 = new Vector2(400, 200);
        Vector2 testPos3 = new Vector2(300, 340);

        public Vector2 Position
        {
            get { return position; }
            set { this.position = value; }
        }

        public float Width
        {
            get { return dimensions.X; }
            set { dimensions.X = value; }
        }

        public float Height
        {
            get { return dimensions.Y; }
            set { dimensions.Y = value; }
        }

        public float MinWidth { get; set; }
        public float MaxWidth { get; set; }
        public float MinHeight { get; set; }
        public float MaxHeight { get; set; }

        float scale;
        public float MinScale { get; set; }
        public float MaxScale { get; set; }

        public MyButton(Game game)
            : base(game)
        {
            Initialize();
        }

        /// <summary>
        /// Allows the game component to perform any initialization it needs to before starting
        /// to run.  This is where it can query for any required services and load content.
        /// </summary>
        public override void Initialize()
        {
            mouseOn = false;
            transitionOn = false;
            scale = 1.0f;
            MinScale = 1.0f;
            MaxScale = 1.5f;
            LoadContent();
            base.Initialize();
        }

        public void LoadContent()
        {
            buttonFont = Game.Content.Load<SpriteFont>("ButtonSpritefont");
        }

        public void UnloadContent()
        {

        }

        /// <summary>
        /// Allows the game component to update itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        public override void Update(GameTime gameTime)
        {
            mouseState = Mouse.GetState();
            if (mouseState.X >= position.X && mouseState.X <= position.X + dimensions.X &&
                mouseState.Y >= position.Y && mouseState.Y <= position.Y + dimensions.Y)
            {
                    transitionOn = true;
                    if (scale <= MaxScale)
                        scale += .01f;
                    if (!mouseOn && dimensions.X < MaxWidth)
                        dimensions.X += (MaxWidth / 100);
                    if (!mouseOn && dimensions.Y < MaxHeight)
                        dimensions.Y += (MaxHeight / 100);
                    if (!mouseOn && dimensions.X >= MaxWidth && dimensions.Y >= MaxHeight)
                    {
                        mouseOn = true;
                        transitionOn = false;
                    }
            }
            else
            {
                    transitionOn = true;
                    if (scale >= MinScale)
                        scale -= .01f;
                    if (mouseOn && dimensions.X > MinWidth)
                        dimensions.X -= (MaxWidth / 100);
                    if (mouseOn && dimensions.Y > MinHeight)
                        dimensions.Y -= (MaxHeight / 100);
                    if (mouseOn && dimensions.X <= MinWidth && dimensions.Y <= MinHeight)
                    {
                        mouseOn = false;
                        transitionOn = false;
                    }
            }

            base.Update(gameTime);
        }

        public void Draw(GameTime gameTime)
        {
            SpriteBatch spriteBatch = new SpriteBatch(Game.GraphicsDevice);
            spriteBatch.Begin();
//            spriteBatch.DrawString(buttonFont, "BUTTON TEXT", new Rectangle(position.X,position.Y,dimensions.X,dimensions.Y), );
            spriteBatch.DrawString(buttonFont, "BUTTON TEXT", position, Color.White, 0, new Vector2(0,0), scale, SpriteEffects.None, 0);
            spriteBatch.DrawString(buttonFont, "(" + mouseState.X + "," + mouseState.Y + ") vs ("+position.X+","+position.Y+")", testPos2, Color.White);
            if (mouseOn)
                spriteBatch.DrawString(buttonFont, "MOUSE ON", testPos, Color.White);
            else if (!mouseOn)
                spriteBatch.DrawString(buttonFont, "MOUSE OFF", testPos, Color.White);
            if (transitionOn)
                spriteBatch.DrawString(buttonFont, "TRANSITION ON", testPos3, Color.White);
            else if (!transitionOn)
                spriteBatch.DrawString(buttonFont, "TRANSITION OFF", testPos3, Color.White);
            spriteBatch.End();
        }
    }
}
