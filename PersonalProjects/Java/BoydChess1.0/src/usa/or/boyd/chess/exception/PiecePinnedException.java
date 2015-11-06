/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class PiecePinnedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 190812119453511634L;

	/**
	 * 
	 */
	public PiecePinnedException()
	{
	}

	/**
	 * @param message
	 */
	public PiecePinnedException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public PiecePinnedException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PiecePinnedException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
