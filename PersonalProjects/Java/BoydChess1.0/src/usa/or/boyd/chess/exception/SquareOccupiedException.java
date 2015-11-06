/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class SquareOccupiedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7085038589875364055L;

	/**
	 * 
	 */
	public SquareOccupiedException()
	{
	}

	/**
	 * @param arg0
	 */
	public SquareOccupiedException(String arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public SquareOccupiedException(Throwable arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SquareOccupiedException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
