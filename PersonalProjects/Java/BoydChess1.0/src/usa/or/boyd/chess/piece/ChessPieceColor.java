/**
 * 
 */
package usa.or.boyd.chess.piece;

import java.awt.Color;

/**
 * @author Joshua
 *
 */
public enum ChessPieceColor
{
	WHITE(255,255,255,"Wht","White",ChessPieceDirection.NORTH),
	BLACK(0,0,0,"Bla","Black",ChessPieceDirection.SOUTH),
	RED(255,0,0,"Red","Red",ChessPieceDirection.EAST),
	GREEN(0,255,0,"Gre","Green",ChessPieceDirection.WEST),
	YELLOW(255,255,0,"Yel","Yellow",ChessPieceDirection.NORTH),
	BLUE(0,0,255,"Blu","Blue",ChessPieceDirection.SOUTH),
	CYAN(0,255,255,"Cyn","Cyan",ChessPieceDirection.EAST),
	MAGENTA(255,0,255,"Mag","Magenta",ChessPieceDirection.WEST);
	
	private Color color;
	private String shortName;
	private String longName;
	private ChessPieceDirection advancementDirection;
	
	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param symbol
	 * @param name
	 * @param direction
	 */
	private ChessPieceColor(int r, int g, int b, String symbol, String name, ChessPieceDirection direction)
	{
		color = new Color(r,g,b);
		shortName = symbol;
		longName = name;
		advancementDirection = direction;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSymbol()
	{
		return shortName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName()
	{
		return longName;
	}
	
	/**
	 * 
	 * @return
	 */
	public ChessPieceDirection getDirection()
	{
		return advancementDirection;
	}
	
	/**
	 * 
	 * @param color
	 * @return
	 */
	public static ChessPieceColor fromColor(Color color)
	{
		if (WHITE.color.equals(color))
		{
			return WHITE;
		}
		else if (BLACK.color.equals(color))
		{
			return BLACK;
		}
		else if (RED.color.equals(color))
		{
			return RED;
		}
		else if (GREEN.color.equals(color))
		{
			return GREEN;
		}
		else if (YELLOW.color.equals(color))
		{
			return YELLOW;
		}
		else if (BLUE.color.equals(color))
		{
			return BLUE;
		}
		else if (CYAN.color.equals(color))
		{
			return CYAN;
		}
		else if (MAGENTA.color.equals(color))
		{
			return MAGENTA;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @param test
	 * @return
	 */
	public static ChessPieceColor fromString(String test)
	{
		if (test.equals(WHITE.shortName) || test.equals(WHITE.longName))
		{
			return WHITE;
		}
		else if (test.equals(BLACK.shortName) || test.equals(BLACK.longName))
		{
			return BLACK;
		}
		else if (test.equals(RED.shortName) || test.equals(RED.longName))
		{
			return RED;
		}
		else if (test.equals(GREEN.shortName) || test.equals(GREEN.longName))
		{
			return GREEN;
		}
		else if (test.equals(YELLOW.shortName) || test.equals(YELLOW.longName))
		{
			return YELLOW;
		}
		else if (test.equals(BLUE.shortName) || test.equals(BLUE.longName))
		{
			return BLUE;
		}
		else if (test.equals(CYAN.shortName) || test.equals(CYAN.longName))
		{
			return CYAN;
		}
		else if (test.equals(MAGENTA.shortName) || test.equals(MAGENTA.longName))
		{
			return MAGENTA;
		}
		else
		{
			return null;
		}
	}
}
