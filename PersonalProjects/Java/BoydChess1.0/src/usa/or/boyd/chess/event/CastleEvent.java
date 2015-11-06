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
public class CastleEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8308085627143692802L;
	private ChessPiece firstPiece;
	private ChessPiece secondPiece;

	/**
	 * @param source
	 */
	public CastleEvent(Object source)
	{
		super(source);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getFirstPiece()
	{
		return firstPiece;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getSecondPiece()
	{
		return secondPiece;
	}
	
	/**
	 * 
	 * @param first
	 */
	public void setFirstPiece(ChessPiece piece)
	{
		firstPiece = piece;
	}
	
	/**
	 * 
	 * @param second
	 */
	public void setSecondPiece(ChessPiece piece)
	{
		secondPiece = piece;
	}
}
