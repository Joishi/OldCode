using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ChildTeachingTool.GameComponents.Screens
{
    public abstract class GameScreen : IMyDrawableGameComponent
    {
        #region Fields
        protected IServiceProvider services;
        protected bool initialized;
        protected bool enabled; // property Active
        protected bool visible; // property Visible
        #endregion

        #region Properties
        protected SpriteBatch spriteBatch;
        //protected GameComponentCollection components;
        protected List<IMyGameComponent> components;
        public SpriteBatch SpriteBatch { get { return spriteBatch; } set { spriteBatch = value; } }

        public bool Enabled { get { return enabled; } set { enabled = value; } }
        public int UpdateOrder { get { throw new NotImplementedException(); } }
        public int DrawOrder { get { throw new NotImplementedException(); } }
        public bool Visible { get { return visible; } set { visible = value; } }
        #endregion

        #region Events
        public event EventHandler<EventArgs> EnabledChanged;
        public event EventHandler<EventArgs> UpdateOrderChanged;
        public event EventHandler<EventArgs> DrawOrderChanged;
        public event EventHandler<EventArgs> VisibleChanged;
        #endregion

        public GameScreen(IServiceProvider services)
        {
            this.services = services;
        }

        public virtual void Initialize()
        {
            components = new List<IMyGameComponent>();
        }

        public virtual void LoadContent()
        {

        }

        public virtual void UnloadContent()
        {

        }

        public virtual void Update(GameTime gameTime)
        {

        }

        public virtual void Draw(GameTime gameTime) {

        }
    }
}
