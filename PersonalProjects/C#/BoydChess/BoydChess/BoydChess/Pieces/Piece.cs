using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BoydChess.Pieces
{
    public abstract class Piece : DrawableGameComponent
    {
        #region Fields
        protected Game game;
        private Int32 xPosition;
        private Int32 yPosition;
        protected List<Movement> validMovements;
        protected List<Movement> validCaptures;
        #endregion

        #region Properties
        public Point Position { get { return new Point(xPosition, yPosition); } }
        protected Int32 X { get { return xPosition; } set { xPosition = value; } }
        protected Int32 Y { get { return yPosition; } set { yPosition = value; } }
        #endregion

        protected Piece(Game game, Point Position) : base(game)
        {
            this.X = Position.X;
            this.Y = Position.Y;
            validMovements = new List<Movement>();
            validCaptures = new List<Movement>();
        }

        protected virtual Boolean AddMovement(Movement move) {
            validMovements.Add(move);
            return true;
        }

        protected virtual Boolean AddCapture(Movement move)
        {
            validCaptures.Add(move);
            return true;
        }

        public virtual Boolean Move()
        {
            return false;
        }
    }
}
