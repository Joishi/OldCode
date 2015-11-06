/**
 * 
 */
package usa.or.boyd.chess.event;

import java.util.EventObject;
import usa.or.boyd.chess.piece.ChessPiece;

/**
 * @author Joshua
 *
 */
public class PieceRemovedEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -11069839017179971L;
	private ChessPiece removedPiece;

	/**
	 * @param source
	 */
	public PieceRemovedEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getRemovedPiece()
	{
		return removedPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setRemovedPiece(ChessPiece piece)
	{
		removedPiece = piece;
	}
}
