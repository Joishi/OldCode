using ChildTeachingTool.Services;
using ChildTeachingTool.StatesAndProperties;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Text
{
    public abstract class GameText : IText
    {
        #region Fields
        protected IServiceProvider services;
        protected string spriteFontString;
        protected ContentManager content;
        protected bool initialized;
        protected TextState textState;
        protected bool drawAtCenter;
        protected List<TextProperty> textProperties;

        protected ITextRenderer textRenderer; // property TextRenderer
        protected SpriteFont font; // property Font
        protected string text; // property Text
        protected Vector2 position; // property Position
        protected Color color; // property Color
        protected float rotation; // property Rotation
        protected Vector2 origin; // property Origin
        protected Vector2 scale; // property Scale
        protected SpriteEffects effects; // property Effects
        protected float layerDepth; // property LayerDepth
        protected bool enabled; // property Enabled
        protected bool visible; // property Visible
        #endregion

        #region Properties
        public ITextRenderer TextRenderer { set { textRenderer = value; } }
        public SpriteFont Font { get { return font; } }
        public string Text { get { return text; } set { text = value; } }
        public Vector2 Position
        {
            get
            {
                if (drawAtCenter)
                {
                    Vector2 textDims = font.MeasureString(text);
                    return new Vector2(position.X - ((textDims.X / 2) * scale.X), position.Y - ((textDims.Y / 2) * scale.Y));
                }
                else
                {
                    return position;
                }
            }
            set { position = value; }
        }
        public Color Color { get { return color; } }
        public float Rotation { get { return rotation; } }
        public Vector2 Origin { get { return origin; } }
        public Vector2 Scale { get { return scale; } }
        public SpriteEffects Effects { get { return effects; } }
        public float LayerDepth { get { return layerDepth; } }
        public bool Enabled { get { return enabled; } set { enabled = value; } }
        public int UpdateOrder { get { throw new NotImplementedException(); } }
        public int DrawOrder { get { throw new NotImplementedException(); } }
        public bool Visible { get { return visible; } set { visible = value; } }
        public bool DrawAtCenter { get { return drawAtCenter; } set { drawAtCenter = value; } }
        #endregion

        #region Events
        public event EventHandler<EventArgs> EnabledChanged;
        public event EventHandler<EventArgs> UpdateOrderChanged;
        public event EventHandler<EventArgs> DrawOrderChanged;
        public event EventHandler<EventArgs> VisibleChanged;
        #endregion

        public GameText(IServiceProvider services, string spriteFontString)
        {
            this.services = services;
            this.spriteFontString = spriteFontString;
            content = new ContentManager(services, "Content");
            initialized = false;
            textState = TextState.Inactive;
            drawAtCenter = false;
            textProperties = new List<TextProperty>();
            text = "Default Text";
            position = new Vector2(0, 0);
            color = Color.White;
            rotation = 0f;
            origin = new Vector2(0, 0);
            scale = new Vector2(1, 1);
            effects = SpriteEffects.None;
            layerDepth = 0f;
            enabled = false;
            visible = false;
        }

        public virtual void Initialize()
        {
            if (!initialized)
            {
                this.textRenderer = (ITextRenderer)services.GetService(typeof(ITextRenderer));
                LoadContent();
                initialized = !initialized;
            }
        }

        public virtual void LoadContent()
        {
            font = content.Load<SpriteFont>(spriteFontString);
        }

        public virtual void UnloadContent()
        {
            content.Unload();
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
                if (textRenderer != null)
                {
                    textRenderer.Render(this);
                }
            }
        }

        protected bool isMouseOver(float mouseX, float mouseY)
        {
            Rectangle textRect;
            Vector2 textDims = font.MeasureString(text);
            if (drawAtCenter)
            {
                textRect = new Rectangle((int)(position.X - ((textDims.X / 2) * scale.X)), (int)(position.Y - ((textDims.Y / 2) * scale.Y)), (int)textDims.X, (int)textDims.Y);
            }
            else
            {
                textRect = new Rectangle((int)(position.X), (int)(position.Y), (int)textDims.X, (int)textDims.Y);
            }
            return textRect.Contains((int)mouseX, (int)mouseY);
        }
    }
}
