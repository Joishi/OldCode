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
public class MoveEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3995130922495058801L;
	private ChessPiece movedPiece;

	/**
	 * @param source
	 */
	public MoveEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getMovedPiece()
	{
		return movedPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setMovedPiece(ChessPiece piece)
	{
		movedPiece = piece;
	}
}
