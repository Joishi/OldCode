using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ChildTeachingTool.GameComponents.Texture
{
    public class Background : GameTexture
    {
        //protected bool isFullScreen;

        public Background(IServiceProvider services, string textureString) : base(services, textureString)
        {
            //isFullScreen = true;
            //destinationRectangle = ((GraphicsDevice)services.GetService(typeof(GraphicsDevice))).Viewport.Bounds;
            //destinationRectangle = ((IGraphicsDeviceManager)services.GetService(typeof(IGraphicsDeviceManager))).
            //destinationRectangle = ((IGraphicsDeviceService)services.GetService(typeof(IGraphicsDeviceService))).GraphicsDevice.Viewport.Bounds;
            enabled = true;
            visible = true;
        }

        public override void Initialize()
        {
            base.Initialize();
            destinationRectangle = ((IGraphicsDeviceService)services.GetService(typeof(IGraphicsDeviceService))).GraphicsDevice.Viewport.Bounds;
        }
    }
}
