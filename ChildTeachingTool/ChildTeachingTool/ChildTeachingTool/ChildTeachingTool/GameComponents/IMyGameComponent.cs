using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents
{
    public interface IMyGameComponent : IGameComponent, IUpdateable
    {
        void LoadContent();
        void UnloadContent();
    }
}
