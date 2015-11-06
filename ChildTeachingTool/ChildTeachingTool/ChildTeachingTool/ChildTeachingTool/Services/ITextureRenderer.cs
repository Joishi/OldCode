using ChildTeachingTool.GameComponents.Texture;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.Services
{
    public interface ITextureRenderer
    {
        SpriteBatch SpriteBatch { set; }

        void Render(ITexture screen);
    }
}
