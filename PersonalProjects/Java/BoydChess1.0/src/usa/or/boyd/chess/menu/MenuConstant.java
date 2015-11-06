/**
 * 
 */
package usa.or.boyd.chess.menu;

/**
 * @author Joshua
 *
 */
public enum MenuConstant implements MenuInterface
{
	NEW("New","NEW"),
	MODE("Mode","MODE"),
	STYLE("Style","STYLE");
	
	private String displayName;
	private String actionName;
	
	/**
	 * 
	 * @param name
	 * @param reference
	 */
	private MenuConstant(String name, String reference)
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
	public static MenuConstant fromString(String command)
	{
		if (command.equals(NEW.displayName) || command.equals(NEW.actionName))
		{
			return NEW;
		}
		else if (command.equals(MODE.displayName) || command.equals(MODE.actionName))
		{
			return MODE;
		}
		else if (command.equals(STYLE.displayName) || command.equals(STYLE.actionName))
		{
			return STYLE;
		}
		else
		{
			return null;
		}
	}
}
