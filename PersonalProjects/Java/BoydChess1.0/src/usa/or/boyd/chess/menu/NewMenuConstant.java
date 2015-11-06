/**
 * 
 */
package usa.or.boyd.chess.menu;

/**
 * @author Joshua
 *
 */
public enum NewMenuConstant implements MenuInterface
{
	EMPTY("Empty","EMPTY"),
	STANDARD("Standard","STANDARD"),
	ZMQUAD("ZM Quad","ZMQUAD");
	
	private String displayName;
	private String actionName;
	
	/**
	 * 
	 * @param name
	 * @param reference
	 */
	private NewMenuConstant(String name, String reference)
	{
		displayName = name;
		actionName = reference;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDisplayName()
	{
		return displayName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getActionCommand()
	{
		return actionName;
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	public static NewMenuConstant fromString(String command)
	{
		if (command.equals(EMPTY.displayName) || command.equals(EMPTY.actionName))
		{
			return EMPTY;
		}
		else if (command.equals(STANDARD.displayName) || command.equals(STANDARD.actionName))
		{
			return STANDARD;
		}
		else if (command.equals(ZMQUAD.displayName) || command.equals(ZMQUAD.actionName))
		{
			return ZMQUAD;
		}
		else
		{
			return null;
		}
	}
}
