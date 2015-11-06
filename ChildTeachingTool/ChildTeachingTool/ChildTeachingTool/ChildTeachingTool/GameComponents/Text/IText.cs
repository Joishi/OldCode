using ChildTeachingTool.Services;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Text
{
    public interface IText : IMyDrawableGameComponent
    {
        ITextRenderer TextRenderer { set; }

        SpriteFont Font { get; }
        string Text { get; }
        Vector2 Position { get; }
        Color Color { get; }
        float Rotation { get; }
        Vector2 Origin { get; }
        Vector2 Scale { get; }
        SpriteEffects Effects { get; }
        float LayerDepth { get; }
    }
}
