/**
 * 
 */
package usa.or.boyd.chess.menu;

import java.awt.Color;

/**
 * @author Joshua
 *
 */
public enum StyleMenuConstant implements MenuInterface
{
	WOOD("Wood","WOOD",new Color(130,92,15),new Color(244,220,168),new Color(204,0,51),new Color(228,165,31)),
	OCEAN("Ocean","OCEAN",new Color(70,130,180),new Color(64,224,208),new Color(0,255,127),new Color(46,139,87));
	
	private String displayName;
	private String actionName;
	private Color darkColor;
	private Color lightColor;
	private Color checkColor;
	private Color selectedColor;
	
	/**
	 * 
	 * @param name
	 * @param reference
	 * @param dark
	 * @param light
	 * @param check
	 * @param selected
	 */
	private StyleMenuConstant(String name, String reference, Color dark, Color light, Color check, Color selected)
	{
		displayName = name;
		actionName = reference;
		darkColor = dark;
		lightColor = light;
		checkColor = check;
		selectedColor = selected;
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
	 * @return
	 */
	public Color getDark()
	{
		return darkColor;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getLight()
	{
		return lightColor;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getCheck()
	{
		return checkColor;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getSelected()
	{
		return selectedColor;
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	public static StyleMenuConstant fromString(String command)
	{
		if (command.equals(WOOD.displayName) || command.equals(WOOD.actionName))
		{
			return WOOD;
		}
		else if (command.equals(OCEAN.displayName) || command.equals(OCEAN.actionName))
		{
			return OCEAN;
		}
		else
		{
			return null;
		}
	}
}
