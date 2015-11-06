

import java.awt.HeadlessException;

import javax.swing.JApplet;
import javax.swing.JFrame;

import usa.or.boyd.chess.ChessBoard;
import usa.or.boyd.chess.ChessBoardGUI;
import usa.or.boyd.chess.ChessBoardManager;

/**
 * @author Joshua
 *
 */
public class ChessBoardGame extends JApplet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1231333008195599510L;

	/**
	 * @throws HeadlessException
	 */
	public ChessBoardGame() throws HeadlessException
	{
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// for application running
		ChessBoard model = new ChessBoard();
		ChessBoardManager controller = new ChessBoardManager();
		ChessBoardGUI view = new ChessBoardGUI(model, controller);
		controller.setChessBoard(model);
//		controller.setChessBoardGUI(view);
		model.addChessEventListener(view);
		JFrame app = new JFrame();
		app.add(view);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setTitle("Testing!");
		app.pack();
		app.setVisible(true);
		app.setResizable(false);
	}
	
	public void init()
	{
		// for applet running
		ChessBoard model = new ChessBoard();
		ChessBoardManager controller = new ChessBoardManager();
		ChessBoardGUI view = new ChessBoardGUI(model, controller);
		controller.setChessBoard(model);
//		controller.setChessBoardGUI(view);
		model.addChessEventListener(view);
		add(view);
	}
}
