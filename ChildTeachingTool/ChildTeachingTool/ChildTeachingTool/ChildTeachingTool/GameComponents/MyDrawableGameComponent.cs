using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents
{
    public abstract class MyDrawableGameComponent : MyGameComponent, IMyDrawableGameComponent
    {
        #region Fields
        #endregion

        #region Properties
        #endregion

        #region Events
        #endregion

        public abstract void Draw(GameTime gameTime);
    }
}
