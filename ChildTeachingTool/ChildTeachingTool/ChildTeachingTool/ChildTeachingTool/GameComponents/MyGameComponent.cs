using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents
{
    public abstract class MyGameComponent : IMyGameComponent
    {
        #region Fields
        protected bool enabled; // property Enabled
        protected bool visible; // property Visible
        #endregion

        #region Properties
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
        
        public abstract void Initialize();
        public abstract void LoadContent();
        public abstract void UnloadContent();
        public abstract void Update(GameTime gameTime);
    }
}
