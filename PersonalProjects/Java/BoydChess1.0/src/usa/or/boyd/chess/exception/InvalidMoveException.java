/**
 * 
 */
package usa.or.boyd.chess.exception;

/**
 * @author Joshua
 *
 */
public class InvalidMoveException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8222118420223568885L;

	/**
	 * 
	 */
	public InvalidMoveException()
	{
	}

	/**
	 * @param arg0
	 */
	public InvalidMoveException(String arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public InvalidMoveException(Throwable arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidMoveException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
