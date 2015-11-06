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
public class CaptureEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4939575684511049413L;
	private ChessPiece capturedPiece;
	
	/**
	 * @param arg0
	 */
	public CaptureEvent(Object arg0)
	{
		super(arg0);
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPiece getCapturedPiece()
	{
		return capturedPiece;
	}
	
	/**
	 * 
	 * @param piece
	 */
	public void setCapturedPiece(ChessPiece piece)
	{
		capturedPiece = piece;
	}
}
