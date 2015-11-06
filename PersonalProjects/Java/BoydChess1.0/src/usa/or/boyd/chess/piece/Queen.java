/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Point;

/**
 * @author Joshua
 *
 */
public class Queen extends ChessPieceGeneric
{
	/**
	 * @param color
	 */
	public Queen(ChessPieceColor color) 
	{
		super(color);
		setType(ChessPieceType.QUEEN);
	}

	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#canMoveTo(java.awt.Point)
	 */
	public boolean canMoveTo(Point target) 
	{
		if (target.equals(currentLocation))
		{
			return false;
		}
		else if (Math.abs(target.x - currentLocation.x) == Math.abs(target.y - currentLocation.y))
		{
			// diagonal ok
			proposedLocation = target;
			return true;
		}
		else if (target.x == currentLocation.x || target.y == currentLocation.y)
		{
			// vert/horiz ok
			proposedLocation = target;
			return true;
		}
		else
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#canTakePiece(usa.or.boyd.chess.piece.ChessPiece)
	 */
	public boolean canTakePiece(ChessPiece target) 
	{
		if (target == null)
		{
			return false;
		}
		else
		{
			return target.getColor() != this.getColor() && canMoveTo(target.getLocation());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ChessPiece clone()
	{
		return new Queen(super.getColor()).placeOn(super.getLocation());
	}
}
