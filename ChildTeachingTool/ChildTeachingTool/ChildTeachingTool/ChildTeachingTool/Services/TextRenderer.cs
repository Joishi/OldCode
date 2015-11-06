using ChildTeachingTool.GameComponents.Text;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.Services
{
    public class TextRenderer : ITextRenderer
    {
        protected IServiceProvider services;
        protected SpriteBatch spriteBatch;
        public SpriteBatch SpriteBatch { set { this.spriteBatch = value; } }

//        public TextRenderer(IServiceProvider services)
//        {
//            this.services = services;
//            spriteBatch = (SpriteBatch)services.GetService(typeof(SpriteBatch));
//        }

        public void Render(IText text)
        {
            spriteBatch.DrawString(text.Font, text.Text, text.Position, text.Color, text.Rotation, text.Origin, text.Scale, text.Effects, text.LayerDepth);
        }
    }
}
