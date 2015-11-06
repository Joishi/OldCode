/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class NoPieceException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8479122731255632173L;

	/**
	 * 
	 */
	public NoPieceException()
	{
	}

	/**
	 * @param message
	 */
	public NoPieceException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoPieceException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoPieceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
