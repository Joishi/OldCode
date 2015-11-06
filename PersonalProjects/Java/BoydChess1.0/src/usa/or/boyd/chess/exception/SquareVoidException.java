/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class SquareVoidException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -101552866919820604L;

	/**
	 * 
	 */
	public SquareVoidException()
	{
	}

	/**
	 * @param message
	 */
	public SquareVoidException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public SquareVoidException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SquareVoidException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
