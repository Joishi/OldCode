/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class InvalidCaptureException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -588887758431070470L;

	/**
	 * 
	 */
	public InvalidCaptureException()
	{
	}

	/**
	 * @param message
	 */
	public InvalidCaptureException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidCaptureException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidCaptureException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
