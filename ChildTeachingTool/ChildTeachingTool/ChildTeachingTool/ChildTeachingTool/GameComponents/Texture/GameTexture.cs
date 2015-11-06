using ChildTeachingTool.Services;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Texture
{
    public abstract class GameTexture : ITexture
    {
        #region Fields
        protected IServiceProvider services;
        protected ContentManager content;
        protected bool initialized;
        protected string textureString;
        protected ITextureRenderer textureRenderer; // property TextureRenderer
        protected Texture2D texture; // property Texture
        protected Rectangle destinationRectangle; // property DestinationRectangle;
        protected Color color; // property Color
        protected bool enabled; // property Enabled
        protected bool visible; // property Visible
        #endregion

        #region Properties
        public ITextureRenderer TextureRenderer { set { textureRenderer = value; } }
        public Texture2D Texture { get { return texture; } }
        public Rectangle DestinationRectangle { get { return destinationRectangle; } }
        public Color Color { get { return color; } }
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

        public GameTexture(IServiceProvider services, string textureString)
        {
            this.services = services;
            this.content = new ContentManager(services, "Content");
            initialized = false;
            this.textureString = textureString;
            destinationRectangle = new Rectangle(0, 0, 0, 0);
            color = Color.White;
            enabled = false;
            visible = false;
        }

        public virtual void Initialize()
        {
            if (!initialized)
            {
                this.textureRenderer = (ITextureRenderer)services.GetService(typeof(ITextureRenderer));
                LoadContent();
                initialized = !initialized;
            }
        }

        public virtual void LoadContent()
        {
            texture = content.Load<Texture2D>(textureString);
        }

        public virtual void UnloadContent()
        {

        }

        public virtual void Update(GameTime gameTime)
        {
            if (enabled)
            {

            }
        }

        public virtual void Draw(GameTime gameTime)
        {
            if (visible)
            {
                if (textureRenderer != null)
                {
                    textureRenderer.Render(this);
                }
            }
        }

    }
}
