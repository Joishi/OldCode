/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Point;

/**
 * @author Joshua
 *
 */
public class Bishop extends ChessPieceGeneric
{
	/**
	 * @param color
	 */
	public Bishop(ChessPieceColor color)
	{
		super(color);
		setType(ChessPieceType.BISHOP);
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
		return new Bishop(super.getColor()).placeOn(super.getLocation());
	}
}
