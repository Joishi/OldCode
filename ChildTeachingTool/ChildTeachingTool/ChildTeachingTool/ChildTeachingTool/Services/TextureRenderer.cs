using ChildTeachingTool.GameComponents.Texture;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.Services
{
    public class TextureRenderer : ITextureRenderer
    {
        protected SpriteBatch spriteBatch;
        public SpriteBatch SpriteBatch { set { this.spriteBatch = value; } }

        public void Render(ITexture texture)
        {
            spriteBatch.Draw(texture.Texture, texture.DestinationRectangle, texture.Color);
        }
    }
}
