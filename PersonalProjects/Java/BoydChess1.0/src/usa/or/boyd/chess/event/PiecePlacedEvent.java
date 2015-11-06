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
public class PiecePlacedEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3118276557309170547L;
	private ChessPiece placedPiece;

	/**
	 * @param source
	 */
	public PiecePlacedEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getPlacedPiece()
	{
		return placedPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setPlacedPiece(ChessPiece piece)
	{
		placedPiece = piece;
	}
}
