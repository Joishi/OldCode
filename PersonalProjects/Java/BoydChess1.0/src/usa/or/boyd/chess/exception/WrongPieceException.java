/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class WrongPieceException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8205526833738883632L;

	/**
	 * 
	 */
	public WrongPieceException()
	{
	}

	/**
	 * @param message
	 */
	public WrongPieceException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public WrongPieceException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WrongPieceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
