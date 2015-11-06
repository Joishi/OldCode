/**
 * 
 */
package usa.or.boyd.chess.piece;

/**
 * @author Joshua
 *
 */
public enum ChessPieceType
{
	PAWN("P", "Pawn"),
	KNIGHT("N", "Knight"),
	BISHOP("B", "Bishop"),
	ROOK("R", "Rook"),
	QUEEN("Q", "Queen"),
	KING("K", "King");
	// TODO add faery chess pieces
	
	private String shortName;
	private String longName;
	
	/**
	 * 
	 * @param shortName
	 * @param longName
	 */
	private ChessPieceType(String shortName, String longName)
	{
		this.shortName = shortName;
		this.longName = longName;
	}
	
	public static ChessPiece getNewChessPiece(ChessPieceType type, ChessPieceColor color)
	{
		if (type == PAWN)
		{
			return new Pawn(color);
		}
		else if (type == KNIGHT)
		{
			return new Knight(color);
		}
		else if (type == BISHOP)
		{
			return new Bishop(color);
		}
		else if (type == ROOK)
		{
			return new Rook(color);
		}
		else if (type == QUEEN)
		{
			return new Queen(color);
		}
		else if (type == KING)
		{
			return new King(color);
		}
		else
		{
			// shouldn't happen
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String toShortString()
	{
		return shortName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toLongString()
	{
		return longName;
	}
	
	/**
	 * 
	 * @param test
	 * @return
	 */
	public static ChessPieceType fromString(String test)
	{
		ChessPieceType[] types = values();
		for (ChessPieceType t : types)
		{
			if (test.equals(t.toShortString()) || test.equals(t.toLongString()))
			{
				return t;
			}
		}
		return null;
//		if (test.equals(PAWN.toShortString()) || test.equals(PAWN.toLongString()))
//		{
//			return PAWN;
//		}
//		else if (test.equals(KNIGHT.toShortString()) || test.equals(KNIGHT.toLongString()))
//		{
//			return KNIGHT;
//		}
//		else if (test.equals(BISHOP.toShortString()) || test.equals(BISHOP.toLongString()))
//		{
//			return BISHOP;
//		}
//		else if (test.equals(ROOK.toShortString()) || test.equals(ROOK.toLongString()))
//		{
//			return ROOK;
//		}
//		else if (test.equals(QUEEN.toShortString()) || test.equals(QUEEN.toLongString()))
//		{
//			return QUEEN;
//		}
//		else if (test.equals(KING.toShortString()) || test.equals(KING.toLongString()))
//		{
//			return KING;
//		}
//		else 
//		{
//			return null;
//		}
	}
}
