using System;

namespace ChildTeachingTool
{
#if WINDOWS || XBOX
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static void Main(string[] args)
        {
            using (ChildTeachingTool game = new ChildTeachingTool())
            {
                game.Run();
            }
        }
    }
#endif
}

