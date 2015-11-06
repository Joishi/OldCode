/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Point;

/**
 * @author Joshua
 *
 */
public abstract class ChessPieceGeneric implements ChessPiece
{
	protected Point previousLocation;
	protected Point currentLocation;
	protected Point proposedLocation;
	private ChessPieceType typeOfPiece;
	private ChessPieceColor colorOfPiece;
	private boolean hasMoved;
	
	
	public ChessPieceGeneric(ChessPieceColor color)
	{
		colorOfPiece = color;
		hasMoved = false;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#capture()
	 */
	public void capture()
	{
		if (proposedLocation.equals(currentLocation))
		{
			// do nothing
		}
		else
		{
			previousLocation = currentLocation;
			currentLocation = proposedLocation;
			hasMoved = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#getColor()
	 */
	public ChessPieceColor getColor()
	{
		return colorOfPiece;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#getDirection()
	 */
	public ChessPieceDirection getDirection()
	{
		return colorOfPiece.getDirection();
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#getLocation()
	 */
	public Point getLocation()
	{
		return currentLocation;
	}
	
	public Point getPreviousLocation()
	{
		return previousLocation;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#getType()
	 */
	public ChessPieceType getType()
	{
		return typeOfPiece;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasMoved()
	{
		return hasMoved;
	}
	

	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#move()
	 */
	public void move()
	{
		if (proposedLocation.equals(currentLocation))
		{
			// do nothing
		}
		else
		{
			previousLocation = currentLocation;
			currentLocation = proposedLocation;
			hasMoved = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.piece.ChessPiece#placeOn(java.awt.Point)
	 */
	public ChessPiece placeOn(Point target)
	{
		if (currentLocation == null)
		{
			currentLocation = target;
		}
		previousLocation = currentLocation;
		currentLocation = target;
		return this;
	}
	
	/**
	 * 
	 * @param type
	 */
	protected void setType(ChessPieceType type)
	{
		if (typeOfPiece == null)
		{
			typeOfPiece = type;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ChessPiece clone()
	{
		return null;
	}
}
