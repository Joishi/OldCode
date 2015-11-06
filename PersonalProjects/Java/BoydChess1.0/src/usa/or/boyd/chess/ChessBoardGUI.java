/**
 * 
 */
package usa.or.boyd.chess;

import usa.or.boyd.chess.event.*;
import usa.or.boyd.chess.menu.*;
import usa.or.boyd.chess.piece.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * @author Joshua
 *
 */
public class ChessBoardGUI extends JPanel implements ChessEventListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5612636389901659618L;
	private ChessBoard board;
	private ChessBoardManager manager;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel lowerRightPanel;
	private JPanel boardPanel;
	private JPanel pawnPromotionPanel;
	private JPanel menuPanel;
	private JPanel sandboxPanel;
	private JPanel sandboxColorPanel;
	private JPanel sandboxPiecePanel;
	private JPanel historyPanel;
	private JButton[][] boardButtons;
	private JRadioButton[] pawnButtons;
	private JRadioButton[] colorButtons;
	private JRadioButton[] pieceButtons;
	private JRadioButton nullColor;
	private JRadioButton nullPiece;
	private ArrayList<JMenuItem> menuItems;
	private ButtonGroup pawnButtonGroup;
	private ButtonGroup colorButtonGroup;
	private ButtonGroup pieceButtonGroup;
	private JScrollPane historyScrollPane;
	private JTextPane historyTextPane;
	private ModeMenuConstant mode;
	private StyleMenuConstant style;
	private Point checkSquare1;
	private Point checkSquare2;
	private ArrayList<ChessPiece> capturedPieces;
	private ChessPieceColor pawnColor;
	private Point selectedSquare;
	private int moveNumber;
	private int turnNumber;
	private int numPlayers;
	
	public ChessBoardGUI(ChessBoard board, ChessBoardManager manager)
	{
		this.board = board;
		this.manager = manager;
		style = StyleMenuConstant.WOOD;
		mode = ModeMenuConstant.SANDBOX;
		capturedPieces = new ArrayList<ChessPiece>(0);
		numPlayers = 0;
		turnNumber = 0;
		moveNumber = 0;
		
		// board panel setup
		setupBoard();
		enableBoard(true);
		
		// pawn promotion panel setup
		setupPawn();
		enablePawn(false);
		
		// menu panel setup
		setupMenu();
		
		// sandbox panel setup
		setupSandbox();
		enableSandbox(true);
		
		// history panel setup
		setupHistory();
		
		// overall view setup
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(boardPanel, BorderLayout.CENTER);
		leftPanel.add(pawnPromotionPanel, BorderLayout.SOUTH);
		lowerRightPanel = new JPanel(new BorderLayout());
		lowerRightPanel.add(sandboxPanel, BorderLayout.NORTH);
		lowerRightPanel.add(historyPanel, BorderLayout.CENTER);
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(lowerRightPanel, BorderLayout.CENTER);
		rightPanel.add(menuPanel, BorderLayout.NORTH);
		setLayout(new BorderLayout());
		add(leftPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	private void setupBoard()
	{
		Dimension boardSize = board.getBoardSize();
		boardPanel = new JPanel(new GridLayout(boardSize.height,boardSize.width));
		boardButtons = new JButton[boardSize.width][boardSize.height];
		for (int y = boardSize.height - 1; y >= 0; y--)
		{
			for (int x = 0; x < boardSize.width; x++)
			{
				JButton square = new JButton();
				square.setActionCommand(x + "," + y);
				if ((x + y) % 2 == 0)
				{
					square.setBackground(style.getDark());
				}
				else
				{
					square.setBackground(style.getLight());
				}
				square.addActionListener(manager);
				square.addActionListener(this);
				square.setMargin(new Insets(0,0,0,0));
				square.setPreferredSize(new Dimension(50,50));
				boardButtons[x][y] = square;
				boardPanel.add(square);
			}
		}
	}
	
	private void setupPawn()
	{
		ChessPieceType[] types = ChessPieceType.values();
		pawnPromotionPanel = new JPanel(new FlowLayout());
		pawnButtons = new JRadioButton[types.length - 2];
		pawnButtonGroup = new ButtonGroup();
		for (int i = 0, j = 0; i < types.length; i++)
		{
			if (types[i] == ChessPieceType.PAWN || types[i] == ChessPieceType.KING)
			{
				// do nothing
			}
			else
			{
				pawnButtons[j] = new JRadioButton(types[i].toLongString());
				pawnButtons[j].setActionCommand(types[i].toShortString());
				pawnButtons[j].addActionListener(this);
				pawnButtonGroup.add(pawnButtons[j]);
				pawnPromotionPanel.add(pawnButtons[j]);
				j++;
			}
		}
	}
	
	private void setupMenu()
	{
		menuItems = new ArrayList<JMenuItem>(0);
		MenuConstant[] topMenus = MenuConstant.values();
		MenuInterface[][] menuConstants = new MenuInterface[topMenus.length][];
		menuConstants[0] = NewMenuConstant.values();
		menuConstants[1] = ModeMenuConstant.values();
		menuConstants[2] = StyleMenuConstant.values();
		menuPanel = new JPanel(new BorderLayout());
		JMenuBar menuBar = new JMenuBar();
		try
		{
			for (int i = 0; i < topMenus.length; i++)
			{
				JMenu menu = new JMenu(topMenus[i].getDisplayName());
				try
				{
					for (int j = 0; j < menuConstants[i].length; j++)
					{
						JMenuItem menuItem = new JMenuItem(menuConstants[i][j].getDisplayName());
						menuItem.setActionCommand(topMenus[i].getActionCommand() + "," + menuConstants[i][j].getActionCommand());
						menuItem.addActionListener(this);
						menuItems.add(menuItem);
						menu.add(menuItem);
					}
				}
				catch (NullPointerException e)
				{
					// do nothing
				}
				menuBar.add(menu);
			}
		}
		catch (NullPointerException e)
		{
			// do nothing
		}
		menuPanel.add(menuBar);
	}
	
	private void setupSandbox()
	{
		ChessPieceColor[] colors = ChessPieceColor.values();
		ChessPieceType[] types = ChessPieceType.values();
		sandboxPanel = new JPanel(new BorderLayout());
		sandboxColorPanel = new JPanel(new GridLayout(4,colors.length / 4));
		sandboxPiecePanel = new JPanel(new GridLayout(4,types.length / 4));
		colorButtons = new JRadioButton[colors.length];
		colorButtonGroup = new ButtonGroup();
		pieceButtons = new JRadioButton[types.length];
		pieceButtonGroup = new ButtonGroup();
		for (int i = 0; i < colors.length; i++)
		{
			colorButtons[i] = new JRadioButton(colors[i].getName());
			colorButtons[i].setActionCommand(colors[i].getSymbol());
			colorButtons[i].addActionListener(manager);
			colorButtonGroup.add(colorButtons[i]);
			sandboxColorPanel.add(colorButtons[i]);
		}
		for (int i = 0; i < types.length; i++)
		{
			pieceButtons[i] = new JRadioButton(types[i].toLongString());
			pieceButtons[i].setActionCommand(types[i].toShortString());
			pieceButtons[i].addActionListener(manager);
			pieceButtonGroup.add(pieceButtons[i]);
			sandboxPiecePanel.add(pieceButtons[i]);
		}
		nullColor = new JRadioButton("No Color");
		nullColor.setActionCommand("NOCOLOR");
		colorButtonGroup.add(nullColor);
		nullColor.addActionListener(manager);
		nullPiece = new JRadioButton("No Piece");
		nullPiece.setActionCommand("NOPIECE");
		pieceButtonGroup.add(nullPiece);
		nullPiece.addActionListener(manager);
		sandboxPanel.add(sandboxColorPanel, BorderLayout.NORTH);
		sandboxPanel.add(sandboxPiecePanel, BorderLayout.SOUTH);
	}
	
	private void setupHistory()
	{
		historyPanel = new JPanel(new BorderLayout());
		historyTextPane = new JTextPane();
		historyTextPane.setEditable(false);
		historyTextPane.setPreferredSize(new Dimension(100,120));
		historyTextPane.setFont(new Font("Monospaced",Font.PLAIN,12));
		historyTextPane.setText("      move\r\n     ------\r\n");
		historyScrollPane = new JScrollPane(historyTextPane);
		historyPanel.add(historyScrollPane);
	}
	
	private void enableBoard(boolean onOrOff)
	{
		for (int x = 0; x < boardButtons.length; x++)
		{
			for (int y = 0; y < boardButtons[x].length; y++)
			{
				boardButtons[x][y].setEnabled(onOrOff);
			}
		}
	}
	
	private void enablePawn(boolean onOrOff)
	{
		for (int i = 0; i < pawnButtons.length; i++)
		{
			pawnButtons[i].setEnabled(onOrOff);
		}
	}
	
	private void enableSandbox(boolean onOrOff)
	{
		if (!onOrOff)
		{
			nullColor.doClick();
			nullPiece.doClick();
		}
		for (int i = 0; i < colorButtons.length; i++)
		{
			colorButtons[i].setEnabled(onOrOff);
		}
		for (int i = 0; i < pieceButtons.length; i++)
		{
			pieceButtons[i].setEnabled(onOrOff);
		}
	}
	
	private void enableMenu(boolean onOrOff)
	{
		for (JMenuItem i : menuItems)
		{
			i.setEnabled(onOrOff);
		}
	}
	
	private void resetChecks()
	{
		if (checkSquare2 != null)
		{
			if ((checkSquare1.x + checkSquare1.y) % 2 == 0)
			{
				boardButtons[checkSquare1.x][checkSquare1.y].setBackground(style.getDark());
			}
			else
			{
				boardButtons[checkSquare1.x][checkSquare1.y].setBackground(style.getLight());
			}
			checkSquare2 = null;
		}
		if (checkSquare1 != null)
		{
			if ((checkSquare1.x + checkSquare1.y) % 2 == 0)
			{
				boardButtons[checkSquare1.x][checkSquare1.y].setBackground(style.getDark());
			}
			else
			{
				boardButtons[checkSquare1.x][checkSquare1.y].setBackground(style.getLight());
			}
			checkSquare1 = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchCaptureEvent(usa.or.boyd.chess.event.CaptureEvent)
	 */
	public void catchCaptureEvent(CaptureEvent event)
	{
		ChessPiece piece = event.getCapturedPiece();
		Point loc = piece.getLocation();
		boardButtons[loc.x][loc.y].setText(null);
		boardButtons[loc.x][loc.y].setIcon(null);
		capturedPieces.add(piece);
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchCastleEvent(usa.or.boyd.chess.event.CastleEvent)
	 */
	public void catchCastleEvent(CastleEvent event)
	{
		ChessPiece one = event.getFirstPiece();
		ChessPiece two = event.getSecondPiece();
		Point p11 = one.getPreviousLocation();
		Point p12 = one.getLocation();
		Point p21 = two.getPreviousLocation();
		Point p22 = two.getLocation();
		String t1 = boardButtons[p11.x][p11.y].getText();
		Icon i1 = boardButtons[p11.x][p11.y].getIcon();
		String t2 = boardButtons[p21.x][p21.y].getText();
		Icon i2 = boardButtons[p21.x][p21.y].getIcon();
		Color fg1 = boardButtons[p11.x][p11.y].getForeground();
		Color fg2 = boardButtons[p21.x][p21.y].getForeground();
		boardButtons[p11.x][p11.y].setText(null);
		boardButtons[p11.x][p11.y].setIcon(null);
		boardButtons[p21.x][p21.y].setText(null);
		boardButtons[p21.x][p21.y].setIcon(null);
		boardButtons[p12.x][p12.y].setText(t1);
		boardButtons[p12.x][p12.y].setIcon(i1);
		boardButtons[p22.x][p22.y].setText(t2);
		boardButtons[p22.x][p22.y].setIcon(i2);
		boardButtons[p12.x][p12.y].setForeground(fg1);
		boardButtons[p22.x][p22.y].setForeground(fg2);
		resetChecks();
		// do history reporting stuff
		historyTextPane.setText(historyTextPane.getText() + (moveNumber+1)+"-"+(turnNumber+1)+"   "+(p11.x+1)+""+(p11.y+1)+"-"+(p12.x + 1)+""+(p12.y+1)+"\r\n");
		turnNumber++;
		moveNumber = moveNumber + turnNumber/numPlayers;
		turnNumber = turnNumber % numPlayers;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchCheckEvent(usa.or.boyd.chess.event.CheckEvent)
	 */
	public void catchCheckEvent(CheckEvent event)
	{
		ChessPiece piece = event.getCheckingPiece();
		if (checkSquare1 == null)
		{
			checkSquare1 = piece.getLocation();
			boardButtons[checkSquare1.x][checkSquare1.y].setBackground(style.getCheck());
		}
		else
		{
			checkSquare2 = piece.getLocation();
			boardButtons[checkSquare2.x][checkSquare2.y].setBackground(style.getCheck());
		}
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchMoveEvent(usa.or.boyd.chess.event.MoveEvent)
	 */
	public void catchMoveEvent(MoveEvent event)
	{
		ChessPiece piece = event.getMovedPiece();
		Point oldLoc = piece.getPreviousLocation();
		Point newLoc = piece.getLocation();
		boardButtons[newLoc.x][newLoc.y].setForeground(boardButtons[oldLoc.x][oldLoc.y].getForeground());
		boardButtons[newLoc.x][newLoc.y].setText(boardButtons[oldLoc.x][oldLoc.y].getText());
		boardButtons[newLoc.x][newLoc.y].setIcon(boardButtons[oldLoc.x][oldLoc.y].getIcon());
		boardButtons[oldLoc.x][oldLoc.y].setText(null);
		boardButtons[oldLoc.x][oldLoc.y].setIcon(null);
		resetChecks();
		// do history reporting stuff
		historyTextPane.setText(historyTextPane.getText() + (moveNumber+1)+"-"+(turnNumber+1)+"   "+(oldLoc.x+1)+""+(oldLoc.y+1)+"-"+(newLoc.x + 1)+""+(newLoc.y+1)+"\r\n");
		turnNumber++;
		moveNumber = moveNumber + turnNumber/numPlayers;
		turnNumber = turnNumber % numPlayers;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchNewPlayerEvent(usa.or.boyd.chess.event.NewPlayerEvent)
	 */
	public void catchNewPlayerEvent(NewPlayerEvent event)
	{
		numPlayers++;
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchPiecePlacedEvent(usa.or.boyd.chess.event.PiecePlacedEvent)
	 */
	public void catchPiecePlacedEvent(PiecePlacedEvent event)
	{
		ChessPiece piece = event.getPlacedPiece();
		Point p = piece.getLocation();
//		URL url = getClass().getResource("" + piece.getColor().getName() + "_" + piece.getType().toLongString() + ".gif");
//		URL url = getClass().getResource("images/" + piece.getColor().getName() + "/" + piece.getColor().getName() + "_" + piece.getType().toLongString() + ".gif");
		URL url = getClass().getResource("/usa/or/boyd/chess/images/" + piece.getColor().getName() + "/" + piece.getColor().getName() + "_" + piece.getType().toLongString() + ".gif");
//		System.out.println("Url is " + url);
		if (url != null)
		{
			ImageIcon icon = new ImageIcon(url);
			boardButtons[p.x][p.y].setText(null);
			boardButtons[p.x][p.y].setIcon(icon);
		}
		else
		{
			boardButtons[p.x][p.y].setIcon(null);
			boardButtons[p.x][p.y].setForeground(piece.getColor().getColor());
			boardButtons[p.x][p.y].setText(piece.getType().toShortString());
		}
	}

	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchPieceRemovedEvent(usa.or.boyd.chess.event.PieceRemovedEvent)
	 */
	public void catchPieceRemovedEvent(PieceRemovedEvent event)
	{
		ChessPiece piece = event.getRemovedPiece();
		Point p = piece.getLocation();
		boardButtons[p.x][p.y].setIcon(null);
		boardButtons[p.x][p.y].setText(null);
	}
	
	/* (non-Javadoc)
	 * @see usa.or.boyd.chess.event.ChessEventListener#catchPromotePawnEvent(usa.or.boyd.chess.event.PromotePawnEvent)
	 */
	public void catchPromotePawnEvent(PromotePawnEvent event)
	{
		if (mode == ModeMenuConstant.SANDBOX)
		{
		}
		else if (mode == ModeMenuConstant.STANDARD)
		{
			enableMenu(false);
			enableBoard(false);
			enablePawn(true);
			pawnColor = event.getPromotedPiece().getColor();
		}
		else if (mode == ModeMenuConstant.ZMQUAD)
		{
			enableMenu(false);
			enableBoard(false);
			enablePawn(true);
			for (JRadioButton b : pawnButtons)
			{
				if (ChessPieceType.fromString(b.getActionCommand()) == ChessPieceType.QUEEN)
				{
					b.setEnabled(false);
				}
			}
			pawnColor = event.getPromotedPiece().getColor();
		}
		else
		{
			// shouldn't happen
			System.exit(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		String ac = e.getActionCommand();
		if (e.getSource() instanceof JMenuItem)
		{
			int comma = ac.indexOf(",");
			MenuConstant menu = MenuConstant.fromString(ac.substring(0,comma));
			if (menu == MenuConstant.NEW)
			{
				NewMenuConstant newGame = NewMenuConstant.fromString(ac.substring(comma + 1, ac.length()));
				capturedPieces = new ArrayList<ChessPiece>(0);
				numPlayers = 0;
				turnNumber = 0;
				moveNumber = 0;
				historyTextPane.setText("      move\r\n     ------\r\n");
				if (newGame == NewMenuConstant.EMPTY)
				{
					board.newBoard(8, 8);
					enableSandbox(true);
					mode = ModeMenuConstant.SANDBOX;
				}
				else if (newGame == NewMenuConstant.STANDARD)
				{
					board.newStandardGame();
					enableSandbox(false);
					mode = ModeMenuConstant.STANDARD;
				}
				else if (newGame == NewMenuConstant.ZMQUAD)
				{
					board.newZMQuadGame();
					enableSandbox(false);
					mode = ModeMenuConstant.ZMQUAD;
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (menu == MenuConstant.MODE)
			{
				mode = ModeMenuConstant.fromString(ac.substring(comma + 1, ac.length())); 
				board.newMode(mode);
				if (mode == ModeMenuConstant.SANDBOX)
				{
					enableSandbox(true);
				}
				else if (mode == ModeMenuConstant.STANDARD)
				{
					enableSandbox(false);
				}
				else if (mode == ModeMenuConstant.ZMQUAD)
				{
					enableSandbox(false);
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (menu == MenuConstant.STYLE)
			{
				StyleMenuConstant newStyle = StyleMenuConstant.fromString(ac.substring(comma + 1, ac.length()));
				for (int x = 0; x < boardButtons.length; x++)
				{
					for (int y = 0; y < boardButtons[x].length; y++)
					{
						Color bg = boardButtons[x][y].getBackground();
						if (bg == style.getDark())
						{
							boardButtons[x][y].setBackground(newStyle.getDark());
						}
						else if (bg == style.getLight())
						{
							boardButtons[x][y].setBackground(newStyle.getLight());
						}
						else if (bg == style.getCheck())
						{
							boardButtons[x][y].setBackground(newStyle.getCheck());
						}
						else if (bg == style.getSelected())
						{
							boardButtons[x][y].setBackground(newStyle.getSelected());
						}
						else
						{
							// shouldn't happen
							System.exit(0);
						}
					}
				}
				style = newStyle;
			}
			else
			{
				// shouldn't happen
				System.exit(0);
			}
		}
		else if (e.getSource() instanceof JRadioButton)
		{
			ChessPiece piece = ChessPieceType.getNewChessPiece(ChessPieceType.fromString(ac), pawnColor);
			board.promotePawn(piece);
			enablePawn(false);
			enableBoard(true);
			enableMenu(true);
		}
		else if (e.getSource() instanceof JButton)
		{
			int comma = ac.indexOf(",");
			int x = Integer.parseInt(ac.substring(0, comma));
			int y = Integer.parseInt(ac.substring(comma + 1, ac.length()));
			if (selectedSquare == null)
			{
				selectedSquare = new Point(x,y);
				boardButtons[x][y].setBackground(style.getSelected());
			}
			else
			{
				if ((selectedSquare.x + selectedSquare.y) % 2 == 0)
				{
					boardButtons[selectedSquare.x][selectedSquare.y].setBackground(style.getDark());
				}
				else
				{
					boardButtons[selectedSquare.x][selectedSquare.y].setBackground(style.getLight());
				}
				selectedSquare = null;
			}
		}
		else
		{
			// shouldn't happen
			System.exit(0);
		}
	}
}
