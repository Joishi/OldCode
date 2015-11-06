using System;

namespace BoydChess
{
#if WINDOWS || XBOX
    static class BoydChess
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static void Main(string[] args)
        {
            using (BoydChessGame game = new BoydChessGame())
            {
                game.Run();
            }
        }
    }
#endif
}

