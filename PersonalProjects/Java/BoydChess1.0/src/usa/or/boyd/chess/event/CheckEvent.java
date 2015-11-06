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
public class CheckEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9192988830165608599L;
	private ChessPiece checkingPiece;

	/**
	 * @param source
	 */
	public CheckEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getCheckingPiece()
	{
		return checkingPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setCheckingPiece(ChessPiece piece)
	{
		checkingPiece = piece;
	}
}
