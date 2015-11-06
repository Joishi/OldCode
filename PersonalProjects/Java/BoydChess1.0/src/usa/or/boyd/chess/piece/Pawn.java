/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Point;

/**
 * @author Joshua
 *
 */
public class Pawn extends ChessPieceGeneric
{
	/**
	 * @param color
	 */
	public Pawn(ChessPieceColor color)
	{
		super(color);
		setType(ChessPieceType.PAWN);
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
		else if (this.getDirection() == ChessPieceDirection.NORTH)
		{
			if (target.y - currentLocation.y == 2 && this.hasMoved() == false)
			{
				proposedLocation = target;
				return true;
			}
			else if (target.y - currentLocation.y == 1 && currentLocation.x == target.x)
			{
				proposedLocation = target;
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (this.getDirection() == ChessPieceDirection.SOUTH)
		{
			if (currentLocation.y - target.y == 2 && this.hasMoved() == false && currentLocation.x == target.x)
			{
				proposedLocation = target;
				return true;
			}
			else if (currentLocation.y - target.y == 1 && currentLocation.x == target.x)
			{
				proposedLocation = target;
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (this.getDirection() == ChessPieceDirection.EAST)
		{
			if (target.x - currentLocation.x == 2 && this.hasMoved() == false && currentLocation.y == target.y)
			{
				proposedLocation = target;
				return true;
			}
			else if (target.x - currentLocation.x == 1 && currentLocation.y == target.y)
			{
				proposedLocation = target;
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (this.getDirection() == ChessPieceDirection.WEST)
		{
			if (currentLocation.x - target.x == 2 && this.hasMoved() == false && currentLocation.y == target.y)
			{
				proposedLocation = target;
				return true;
			}
			else if (currentLocation.x - target.x == 1 && currentLocation.y == target.y)
			{
				proposedLocation = target;
				return true;
			}
			else
			{
				return false;
			}
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
		else if (target.getColor() == this.getColor())
		{
			return false;
		}
		else
		{
			Point targetP = target.getLocation();
			int targetX = targetP.x;
			int targetY = targetP.y;
			
			if (this.getDirection() == ChessPieceDirection.NORTH)
			{
				if (Math.abs(targetX - currentLocation.x) == 1 && targetY - currentLocation.y == 1)
				{
					proposedLocation = targetP;
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (this.getDirection() == ChessPieceDirection.SOUTH)
			{
				if (Math.abs(targetX - currentLocation.x) == 1 && currentLocation.y - targetY == 1)
				{
					proposedLocation = targetP;
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (this.getDirection() == ChessPieceDirection.EAST)
			{
				if (Math.abs(targetY - currentLocation.y) == 1 && targetX - currentLocation.x == 1)
				{
					proposedLocation = targetP;
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (this.getDirection() == ChessPieceDirection.WEST)
			{
				if (Math.abs(targetY - currentLocation.y) == 1 && currentLocation.x - targetX == 1)
				{
					proposedLocation = targetP;
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ChessPiece clone()
	{
		return new Pawn(super.getColor()).placeOn(super.getLocation());
	}
}
