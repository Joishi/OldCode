/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class PathwayBlockedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676100328294317376L;

	/**
	 * 
	 */
	public PathwayBlockedException()
	{
	}

	/**
	 * @param message
	 */
	public PathwayBlockedException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public PathwayBlockedException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PathwayBlockedException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
