/**
 * 
 */
package usa.or.boyd.chess;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import usa.or.boyd.chess.piece.*;

/**
 * @author Joshua
 *
 */
public class ChessBoardManager implements ActionListener
{
	private ChessBoard board;
//	private ChessBoardGUI gui;
	private ChessPieceType type;
	private ChessPieceColor color;
	private Point square1;
	
	public ChessBoardManager()
	{
	}
	
	public void setChessBoard(ChessBoard board)
	{
		this.board = board;
	}
	
//	public void setChessBoardGUI(ChessBoardGUI gui)
//	{
//		this.gui = gui;
//	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		// listens to the board and the sandbox
		String ac = e.getActionCommand();
		if (e.getSource() instanceof JButton)
		{
			int comma = ac.indexOf(",");
			int x = Integer.parseInt(ac.substring(0,comma));
			int y = Integer.parseInt(ac.substring(comma + 1, ac.length()));
			if (square1 == null)
			{
				square1 = new Point(x,y);
			}
			else if (type != null && color != null)
			{
				Point square2 = new Point(x,y);
				ChessPiece piece = ChessPieceType.getNewChessPiece(type, color);
				board.processMove(square1, square2, piece);
				square1 = null;
			}
			else
			{
				Point square2 = new Point(x,y);
				board.processMove(square1, square2);
				square1 = null;
			}
		}
		else if (e.getSource() instanceof JRadioButton)
		{
			if (ChessPieceType.fromString(ac) != null)
			{
				type = ChessPieceType.fromString(ac);
			}
			else if (ChessPieceColor.fromString(ac) != null)
			{
				color = ChessPieceColor.fromString(ac);
			}
			else
			{
				type = null;
				color = null;
			}
		}
		else
		{
			// shouldn't happen
			System.exit(0);
		}
	}
}
