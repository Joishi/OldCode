/**
 * 
 */
package usa.or.boyd.chess.event;

import java.util.EventObject;
import usa.or.boyd.chess.piece.Pawn;

/**
 * @author Joshua
 *
 */
public class PromotePawnEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 597545300946795359L;
	private Pawn promotedPiece;

	/**
	 * @param source
	 */
	public PromotePawnEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public Pawn getPromotedPiece()
	{
		return promotedPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setPromotedPiece(Pawn piece)
	{
		promotedPiece = piece;
	}
}
