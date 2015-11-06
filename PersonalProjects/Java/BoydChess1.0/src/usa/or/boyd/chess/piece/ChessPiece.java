/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Point;

/**
 * @author Joshua
 *
 */
public interface ChessPiece
{
	/**
	 * 
	 * @param target
	 * @return
	 */
	boolean canMoveTo(Point target);
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	boolean canTakePiece(ChessPiece target);
	
	/**
	 * 
	 *
	 */
	void capture();
	
	/**
	 * 
	 * @return
	 */
	ChessPiece clone();
	
	/**
	 * 
	 * @return
	 */
	ChessPieceColor getColor();
	
	/**
	 * 
	 * @return
	 */
	ChessPieceDirection getDirection();
	
	/**
	 * 
	 * @return
	 */
	Point getLocation();
	
	/**
	 * 
	 * @return
	 */
	Point getPreviousLocation();
	
	/**
	 * 
	 * @return
	 */
	ChessPieceType getType();
	
	/**
	 * 
	 * @return
	 */
	boolean hasMoved();
	
	/**
	 * 
	 *
	 */
	void move();
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	ChessPiece placeOn(Point target);
}
