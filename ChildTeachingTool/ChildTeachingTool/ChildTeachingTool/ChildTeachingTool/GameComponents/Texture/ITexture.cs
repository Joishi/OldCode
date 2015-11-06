using ChildTeachingTool.Services;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Texture
{
    public interface ITexture : IMyDrawableGameComponent
    {
        ITextureRenderer TextureRenderer { set; }

        Texture2D Texture { get; }
        Rectangle DestinationRectangle { get; }
        Color Color { get; }
    }
}
