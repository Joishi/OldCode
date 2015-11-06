/**
 * 
 */
package usa.or.boyd.chess;

import usa.or.boyd.chess.event.*;
import usa.or.boyd.chess.exception.*;
import usa.or.boyd.chess.menu.*;
import usa.or.boyd.chess.piece.*;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

/**
 * @author Joshua
 *
 */
public class ChessBoard
{
	private EventListenerList listenerList = new EventListenerList();
	private CaptureEvent captureEvent = new CaptureEvent(this);
	private CastleEvent castleEvent = new CastleEvent(this);
	private CheckEvent checkEvent = new CheckEvent(this);
	private MoveEvent moveEvent = new MoveEvent(this);
	private NewPlayerEvent newPlayerEvent = new NewPlayerEvent(this);
	private PiecePlacedEvent piecePlacedEvent = new PiecePlacedEvent(this);
	private PieceRemovedEvent pieceRemovedEvent = new PieceRemovedEvent(this);
	private PromotePawnEvent promotePawnEvent = new PromotePawnEvent(this);
	private ChessPiece[][] pieces;
	private ChessPiece[][] copy;
	private ArrayList<ChessPieceColor> players;
	private int turn;
	private ArrayList<Boolean> canCastle;
	private ModeMenuConstant mode;
	private Pawn enPassantPawn;
	private Point enPassantSquare;
	private ChessPiece sandboxPiece;
	
	public ChessBoard()
	{
		newBoard(8,8);
	}
	
	public Dimension getBoardSize()
	{
		if (pieces == null)
		{
			return null;
		}
		else
		{
			return new Dimension(pieces.length, pieces[0].length);
		}
	}
	
	public void newMode(ModeMenuConstant newMode)
	{
		mode = newMode;
	}
	
	public void processMove(Point start, Point end)
	{
		try
		{
			if (players.size() > 0 && isOutOfPieces(players.get(turn)))
			{
				removePlayer(players.get(turn));
			}
			if (mode == ModeMenuConstant.SANDBOX)
			{
				sandboxRules(start, end);
			}
			else if (mode == ModeMenuConstant.STANDARD)
			{
				standardRules(start, end);
				nextPlayer();
				if (isCheckmated(players.get(turn)))
				{
					System.out.println(players.get(turn).getName() + " has been checkmated!");
				}
			}
			else if (mode == ModeMenuConstant.ZMQUAD)
			{
				zmQuadRules(start, end);
				nextPlayer();
				int size = players.size();
				int t = turn;
				for (int i = t; i > t - size; i--)
				{
					if (boardIsInCheck(pieces, players.get((i + size) % size)))
					{
						turn = (i + size) % size;
					}
				}
				if (isCheckmated(players.get(turn)))
				{
					System.out.println(players.get(turn).getName() + " has been checkmated!");
					ChessPiece king = findKingOnBoard(pieces, players.get(turn));
					try
					{
						removePiece(king.getLocation());
					}
					catch (SquareVoidException e)
					{
						// shouldn't happen
					}
				}
			}
			else
			{
				// shouldn't happen
				System.exit(0);
			}
			reportChecks(players.get(turn));
		}
		catch (InvalidCaptureException e)
		{
			// piece couldn't capture target
		}
		catch (InvalidMoveException e)
		{
			// piece couldn't move to square
		}
		catch (NoPieceException e)
		{
			// no piece selected
		}
		catch (PathwayBlockedException e)
		{
			// path is not clear
		}
		catch (PiecePinnedException e)
		{
			// piece is pinned
		}
		catch (WrongPieceException e)
		{
			// opponents piece selected
		}
	}
	
	public void processMove(Point start, Point end, ChessPiece piece)
	{
		sandboxPiece = piece;
		processMove(start, end);
	}
	
	public void promotePawn(ChessPiece piece)
	{
		if (promotePawnEvent.getPromotedPiece() == null)
		{
			// no pawn to promote
		}
		else
		{
			Pawn pawn = promotePawnEvent.getPromotedPiece();
			Point loc = pawn.getLocation();
			try
			{
				removePiece(loc);
				placePiece(piece.placeOn(loc));
				promotePawnEvent.setPromotedPiece(null);
				reportChecks(players.get(turn));
			}
			catch (SquareVoidException e)
			{
				// shouldn't happen here
			}
			catch (SquareOccupiedException e)
			{
				// shouldn't happen here
			}
		}
	}
	
	private void sandboxRules(Point start, Point end) // throws
	{
		if (start.equals(end))
		{
			// either remove or place a piece
			if (pieces[end.x][end.y] != null)
			{
				// remove piece
				try
				{
					removePiece(end);
				}
				catch (SquareVoidException e)
				{
					// shouldn't happen
				}
			}
			else if (sandboxPiece != null)
			{
				// place piece
				try
				{
					placePiece(sandboxPiece.placeOn(end));
				}
				catch (SquareOccupiedException e)
				{
					// shouldn't happen
				}
			}
			else
			{
				// do nothing
			}
		}
		else
		{
			// move a piece
			try
			{
				removePiece(end);
			}
			catch (SquareVoidException e)
			{
				// no piece on end
			}
			try
			{
				ChessPiece piece = pieces[start.x][start.y];
				removePiece(start);
				placePiece(piece.placeOn(end));
			}
			catch (SquareOccupiedException e)
			{
				// shouldn't happen
			}
			catch (SquareVoidException e)
			{
				// shouldn't happen
			}
		}
	}
	
	private void standardRules(Point start, Point end) throws InvalidCaptureException, InvalidMoveException, NoPieceException, PathwayBlockedException, PiecePinnedException, WrongPieceException
	{
		if (pieces[start.x][start.y] == null)
		{
			throw new NoPieceException();
		}
		else if (pieces[start.x][start.y].getColor() != players.get(turn))
		{
			throw new WrongPieceException();
		}
		else
		{
			if (!boardIsClear(pieces, start, end))
			{
				throw new PathwayBlockedException();
			}
			if (isPinned(pieces[start.x][start.y], end))
			{
				throw new PiecePinnedException();
			}
			else if (pieces[start.x][start.y] instanceof King)
			{
				// king special rules in here
				King king = (King) pieces[start.x][start.y];
				if (canCastle.get(turn).booleanValue() && Math.abs(end.x - start.x) == 2 && end.y == start.y && !boardIsInCheck(pieces, players.get(turn)))
				{
					if (end.x - start.x == 2)
					{
						// king-side castle
						if (!king.hasMoved() && !pieces[7][end.y].hasMoved() && pieces[7][end.y] instanceof Rook && pieces[7][end.y].getColor() == king.getColor())
						{
							Rook rook = (Rook) pieces[7][end.y];
							if (boardIsClear(pieces, start, new Point(7,end.y)))
							{
								if (!isPinned(king, new Point(5,end.y)))
								{
									pieces[start.x][start.y] = null;
									pieces[7][end.y] = null;
									pieces[end.x][end.y] = king;
									pieces[5][end.y] = rook;
									fireCastleEvent(king.placeOn(end), rook.placeOn(new Point(5,end.y)));
								}
								else
								{
									throw new InvalidMoveException();
								}
							}
							else
							{
								throw new InvalidMoveException();
							}
						}
						else
						{
							throw new InvalidMoveException();
						}
					}
					else
					{
						// queen-side castle
						if (!king.hasMoved() && !pieces[0][end.y].hasMoved() && pieces[0][end.y] instanceof Rook && pieces[0][end.y].getColor() == king.getColor())
						{
							Rook rook = (Rook) pieces[0][end.y];
							if (boardIsClear(pieces, start, new Point(0,end.y)))
							{
								if (!isPinned(king, new Point(3,end.y)))
								{
									pieces[start.x][start.y] = null;
									pieces[0][end.y] = null;
									pieces[end.x][end.y] = king;
									pieces[3][end.y] = rook;
									fireCastleEvent(king.placeOn(end), rook.placeOn(new Point(3,end.y)));
								}
								else
								{
									throw new InvalidMoveException();
								}
							}
							else
							{
								throw new InvalidMoveException();
							}
						}
						else
						{
							throw new InvalidMoveException();
						}
					}
				}
				else if (pieces[end.x][end.y] == null)
				{
					if (!king.canMoveTo(end))
					{
						throw new InvalidMoveException();
					}
					else
					{
						// regular king move
						king.move();
						pieces[end.x][end.y] = king;
						pieces[start.x][start.y] = null;
						fireMoveEvent(king);
						canCastle.set(turn, new Boolean(false));
					}
				}
				else if (pieces[end.x][end.y] != null)
				{
					if (!king.canTakePiece(pieces[end.x][end.y]))
					{
						throw new InvalidCaptureException();
					}
					else
					{
						// regular king capture
						king.capture();
						fireCaptureEvent(pieces[end.x][end.y]);
						pieces[end.x][end.y] = king;
						pieces[start.x][start.y] = null;
						fireMoveEvent(king);
						canCastle.set(turn, new Boolean(false));
					}
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (pieces[start.x][start.y] instanceof Pawn)
			{
				// pawn special rules in here
				Pawn pawn = (Pawn) pieces[start.x][start.y];
				if (pieces[end.x][end.y] == null)
				{
					if (!pawn.canMoveTo(end))
					{
						// need to test for enPassant
						if (enPassantPawn == null)
						{
							throw new InvalidMoveException();
						}
						else
						{
							if (end.equals(enPassantSquare) && pawn.canTakePiece(enPassantPawn.clone().placeOn(enPassantSquare)))
							{
								pawn.placeOn(enPassantSquare);
								fireCaptureEvent(enPassantPawn);
								pieces[end.x][end.y] = pawn;
								pieces[start.x][start.y] = null;
								Point p = enPassantPawn.getLocation();
								pieces[p.x][p.y] = null;
								fireMoveEvent(pawn);
								testPawnPromotion(pawn);
							}
							else
							{
								throw new InvalidMoveException();
							}
						}
					}
					else
					{
						// regular pawn move
						pawn.move();
						pieces[end.x][end.y] = pawn;
						pieces[start.x][start.y] = null;
						fireMoveEvent(pawn);
						if (Math.abs(end.y - start.y) == 2)
						{
							enPassantSquare = new Point(start.x, (start.y + end.y) / 2);
							enPassantPawn = pawn;
						}
						testPawnPromotion(pawn);
					}
				}
				else if (pieces[end.x][end.y] != null)
				{
					if (!pawn.canTakePiece(pieces[end.x][end.y]))
					{
						throw new InvalidCaptureException();
					}
					else
					{
						// regular pawn capture
						pawn.capture();
						fireCaptureEvent(pieces[end.x][end.y]);
						pieces[end.x][end.y] = pawn;
						pieces[start.x][start.y] = null;
						fireMoveEvent(pawn);
						testPawnPromotion(pawn);
					}
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (pieces[end.x][end.y] == null)
			{
				if (!pieces[start.x][start.y].canMoveTo(end))
				{
					throw new InvalidMoveException();
				}
				else
				{
					// other pieces move accordingly
					pieces[start.x][start.y].move();
					pieces[end.x][end.y] = pieces[start.x][start.y];
					pieces[start.x][start.y] = null;
					fireMoveEvent(pieces[end.x][end.y]);
				}
			}
			else if (pieces[end.x][end.y] != null)
			{
				if (!pieces[start.x][start.y].canTakePiece(pieces[end.x][end.y]))
				{
					throw new InvalidCaptureException();
				}
				else
				{
					// other pieces capture accordingly
					pieces[start.x][start.y].capture();
					fireCaptureEvent(pieces[end.x][end.y]);
					pieces[end.x][end.y] = pieces[start.x][start.y];
					pieces[start.x][start.y] = null;
					fireMoveEvent(pieces[end.x][end.y]);
				}
			}
		}
	}
	
	private void zmQuadRules(Point start, Point end) throws InvalidCaptureException, InvalidMoveException, NoPieceException, PathwayBlockedException, PiecePinnedException, WrongPieceException
	{
		if (pieces[start.x][start.y] == null)
		{
			throw new NoPieceException();
		}
		else if (pieces[start.x][start.y].getColor() != players.get(turn))
		{
			throw new WrongPieceException();
		}
		else
		{
			if (!boardIsClear(pieces, start, end))
			{
				throw new PathwayBlockedException();
			}
			if (isPinned(pieces[start.x][start.y], end))
			{
				throw new PiecePinnedException();
			}
			else if (pieces[start.x][start.y] instanceof King)
			{
				// king special rules in here
				King king = (King) pieces[start.x][start.y];
				if (canCastle.get(turn).booleanValue() && pieces[end.x][end.y] instanceof Rook && pieces[end.x][end.y].getColor() == king.getColor() && !pieces[end.x][end.y].hasMoved() && !king.hasMoved())
				{
					// castling
					Rook rook = (Rook) pieces[end.x][end.y];
					pieces[start.x][start.y] = rook.placeOn(start);
					pieces[end.x][end.y] = king.placeOn(end);
					fireCastleEvent(king, rook);
				}
				else if (pieces[end.x][end.y] == null)
				{
					if (!king.canMoveTo(end))
					{
						throw new InvalidMoveException();
					}
					else
					{
						// regular king move
						king.move();
						pieces[end.x][end.y] = king;
						pieces[start.x][start.y] = null;
						fireMoveEvent(king);
						canCastle.set(turn, new Boolean(false));
						// test for queen
						try
						{
							if (king.getDirection() == ChessPieceDirection.NORTH)
							{
								if (start.equals(new Point(pieces.length,pieces[pieces.length - 1].length)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.SOUTH)
							{
								if (start.equals(new Point(0,0)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.EAST)
							{
								if (start.equals(new Point(pieces.length,0)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.WEST)
							{
								if (start.equals(new Point(0, pieces[0].length)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else
							{
								// shouldn't happen
							}
						}
						catch (SquareOccupiedException e)
						{
							// shouldn't happen
						}
					}
				}
				else if (pieces[end.x][end.y] != null)
				{
					if (!king.canTakePiece(pieces[end.x][end.y]))
					{
						throw new InvalidCaptureException();
					}
					else
					{
						// regular king capture
						king.capture();
						fireCaptureEvent(pieces[end.x][end.y]);
						pieces[end.x][end.y] = king;
						pieces[start.x][start.y] = null;
						fireMoveEvent(king);
						canCastle.set(turn, new Boolean(false));
						// test for queen
						try
						{
							if (king.getDirection() == ChessPieceDirection.NORTH)
							{
								if (start.equals(new Point(pieces.length,pieces[pieces.length - 1].length)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.SOUTH)
							{
								if (start.equals(new Point(0,0)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.EAST)
							{
								if (start.equals(new Point(pieces.length,0)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else if (king.getDirection() == ChessPieceDirection.WEST)
							{
								if (start.equals(new Point(0, pieces[0].length)))
								{
									placePiece(new Queen(king.getColor()).placeOn(start));
								}
							}
							else
							{
								// shouldn't happen
							}
						}
						catch (SquareOccupiedException e)
						{
							// shouldn't happen
						}
					}
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (pieces[start.x][start.y] instanceof Pawn)
			{
				// pawn special rules in here
				Pawn pawn = (Pawn) pieces[start.x][start.y];
				if (pieces[end.x][end.y] == null)
				{
					if (!pawn.canMoveTo(end))
					{
						// need to test for enPassant
						if (enPassantPawn == null)
						{
							throw new InvalidMoveException();
						}
						else
						{
							if (end.equals(enPassantSquare) && pawn.canTakePiece(enPassantPawn.clone().placeOn(enPassantSquare)))
							{
								pawn.placeOn(enPassantSquare);
								fireCaptureEvent(enPassantPawn);
								pieces[end.x][end.y] = pawn;
								pieces[start.x][start.y] = null;
								Point p = enPassantPawn.getLocation();
								pieces[p.x][p.y] = null;
								fireMoveEvent(pawn);
								testPawnPromotion(pawn);
							}
							else
							{
								throw new InvalidMoveException();
							}
						}
					}
					else
					{
						// regular pawn move
						pawn.move();
						pieces[end.x][end.y] = pawn;
						pieces[start.x][start.y] = null;
						fireMoveEvent(pawn);
						if (Math.abs(end.y - start.y) == 2)
						{
							enPassantSquare = new Point(start.x, (start.y + end.y) / 2);
							enPassantPawn = pawn;
						}
						else if (Math.abs(end.x - start.x) == 2)
						{
							enPassantSquare = new Point((start.x + end.x) / 2, start.y);
							enPassantPawn = pawn;
						}
						testPawnPromotion(pawn);
					}
				}
				else if (pieces[end.x][end.y] != null)
				{
					if (!pawn.canTakePiece(pieces[end.x][end.y]))
					{
						throw new InvalidCaptureException();
					}
					else
					{
						// regular pawn capture
						pawn.capture();
						fireCaptureEvent(pieces[end.x][end.y]);
						pieces[end.x][end.y] = pawn;
						pieces[start.x][start.y] = null;
						fireMoveEvent(pawn);
						testPawnPromotion(pawn);
					}
				}
				else
				{
					// shouldn't happen
					System.exit(0);
				}
			}
			else if (pieces[end.x][end.y] == null)
			{
				if (!pieces[start.x][start.y].canMoveTo(end))
				{
					throw new InvalidMoveException();
				}
				else
				{
					// other pieces move accordingly
					pieces[start.x][start.y].move();
					pieces[end.x][end.y] = pieces[start.x][start.y];
					pieces[start.x][start.y] = null;
					fireMoveEvent(pieces[end.x][end.y]);
				}
			}
			else if (pieces[end.x][end.y] != null)
			{
				if (!pieces[start.x][start.y].canTakePiece(pieces[end.x][end.y]))
				{
					throw new InvalidCaptureException();
				}
				else
				{
					// other pieces capture accordingly
					pieces[start.x][start.y].capture();
					fireCaptureEvent(pieces[end.x][end.y]);
					pieces[end.x][end.y] = pieces[start.x][start.y];
					pieces[start.x][start.y] = null;
					fireMoveEvent(pieces[end.x][end.y]);
				}
			}
		}
	}
	
	private void placePiece(ChessPiece piece) throws SquareOccupiedException
	{
		Point p = piece.getLocation();
		if (pieces[p.x][p.y] == null)
		{
			if (newPlayer(piece.getColor()))
			{
				addNewPlayer(piece.getColor());
			}
			pieces[p.x][p.y] = piece;
			firePiecePlacedEvent(piece);
		}
		else
		{
			throw new SquareOccupiedException("Square " + p.x + "," + p.y + " is occupied.");
		}
	}
	
	private void removePiece(Point location) throws SquareVoidException
	{
		if (pieces[location.x][location.y] != null)
		{
			ChessPiece pieceRemoved = pieces[location.x][location.y];
			pieces[location.x][location.y] = null;
			firePieceRemovedEvent(pieceRemoved);
		}
		else
		{
			throw new SquareVoidException("Square " + location.x + "," + location.y + " is empty.");
		}
	}
	
	private void addNewPlayer(ChessPieceColor color)
	{
		players.add(color);
		canCastle.add(new Boolean(true));
	}
	
	private boolean boardIsClear(ChessPiece[][] board, Point p1, Point p2)
	{
		int dist;
		if (isDiagonal(p1,p2))
		{
			dist = Math.abs(p2.x - p1.x);
			if (p2.x > p1.x)
			{
				if (p2.y > p1.y)
				{
					// northeast
					for (int i = 1; i < dist; i++)
					{
						if (board[p1.x+i][p1.y+i] != null)
						{
							return false;
						}
					}
				}
				else
				{
					// southeast
					for (int i = 1; i < dist; i++)
					{
						if (board[p1.x+i][p1.y-i] != null)
						{
							return false;
						}
					}
				}
			}
			else
			{
				if (p2.y > p1.y)
				{
					// northwest
					for (int i = 1; i < dist; i++)
					{
						if (board[p1.x-i][p1.y+i] != null)
						{
							return false;
						}
					}
				}
				else
				{
					// southwest
					for (int i = 1; i < dist; i++)
					{
						if (board[p1.x-i][p1.y-i] != null)
						{
							return false;
						}
					}
				}
			}
		}
		else if (isHorizontal(p1,p2))
		{
			dist = Math.abs(p2.x - p1.x);
			if (p2.x > p1.x)
			{
				// east
				for (int i = 1; i < dist; i++)
				{
					if (board[p1.x+i][p1.y] != null)
					{
						return false;
					}
				}
			}
			else
			{
				// west
				for (int i = 1; i < dist; i++)
				{
					if (board[p1.x-i][p1.y] != null)
					{
						return false;
					}
				}
			}
		}
		else if (isVertical(p1,p2))
		{
			dist = Math.abs(p2.y - p1.y);
			if (p2.y > p1.y)
			{
				// north
				for (int i = 1; i < dist; i++)
				{
					if (board[p1.x][p1.y+i] != null)
					{
						return false;
					}
				}
			}
			else
			{
				// south
				for (int i = 1; i < dist; i++)
				{
					if (board[p1.x][p1.y-i] != null)
					{
						return false;
					}
				}
			}
		}
		else
		{
			// jump move
		}
		return true;
	}
	
	private boolean boardIsInCheck(ChessPiece[][] board, ChessPieceColor color)
	{
		ChessPiece king = findKingOnBoard(board, color);
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[x].length; y++)
			{
				try
				{
					if (board[x][y].canTakePiece(king) && boardIsClear(board, new Point(x,y), king.getLocation()))
					{
						return true;
					}
					else
					{
						// do nothing
					}
				}
				catch (NullPointerException e)
				{
					// do nothing
				}
			}
		}
		return false;
	}
	
	private boolean canBeMovedOnto(ChessPieceColor color, Point target)
	{
		for (int x = 0; x < pieces.length; x++)
		{
			for (int y = 0; y < pieces[x].length; y++)
			{
				if (pieces[x][y] == null)
				{
					// do nothing
				}
				else if (pieces[x][y].getColor() == color && pieces[x][y].canMoveTo(target) && !isPinned(pieces[x][y], target))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private void copyBoard()
	{
		if (pieces == null)
		{
			copy = null;
		}
		else
		{
			copy = new ChessPiece[pieces.length][pieces[0].length];
			for (int x = 0; x < pieces.length; x++)
			{
				for (int y = 0; y < pieces[x].length; y++)
				{
					try
					{
						copy[x][y] = pieces[x][y].clone();
					}
					catch (NullPointerException c)
					{
						// do nothing
					}
				}
			}
		}
	}
	
	private ChessPiece findKingOnBoard(ChessPiece[][] board, ChessPieceColor color)
	{
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[x].length; y++)
			{
				try
				{
					if (board[x][y].getType() == ChessPieceType.KING && board[x][y].getColor() == color)
					{
						return board[x][y];
					}
				}
				catch (NullPointerException e)
				{
					// do nothing
				}
			}
		}
		return null;
	}
	
	private boolean isCheckmated(ChessPieceColor color)
	{
		King king = (King) findKingOnBoard(pieces, color);
		if (king == null)
		{
			return false;
		}
		Point loc = king.getLocation();
		// test if king is under attack
		if (!boardIsInCheck(pieces, color))
		{
			return false;
		}
		for (int x = 0; x < pieces.length; x++)
		{
			for (int y = 0; y < pieces[x].length; y++)
			{
				// test if king can move
				if (pieces[x][y] == null)
				{
					if (king.canMoveTo(new Point(x,y)) && !isPinned(king, new Point(x,y)) && boardIsClear(pieces, loc, new Point(x,y)))
					{
						return false;
					}
				}
				// test if king can capture
				else
				{
					if (king.canTakePiece(pieces[x][y]) && !isPinned(king, new Point(x,y)) && boardIsClear(pieces, loc, new Point(x,y)))
					{
						return false;
					}
				}
			}
		}
		// test if checking piece can be captured
		int numchecks = 0;
		boolean cantake = false;
		int cx = -1;
		int cy = -1;
		for (int x = 0; x < pieces.length; x++)
		{
			for (int y = 0; y < pieces[x].length; y++)
			{
				if (pieces[x][y] == null)
				{
					// do nothing
				}
				else
				{
					if (pieces[x][y].canTakePiece(king) && boardIsClear(pieces, new Point(x,y), loc))
					{
						cx = x;
						cy = y;
						numchecks++;
						for (int i = 0; i < pieces.length; i++)
						{
							for (int j = 0; j < pieces[i].length; j++)
							{
								if (pieces[i][j] == null)
								{
									// do nothing
								}
								else
								{
									if (pieces[i][j].getColor() == color && pieces[i][j].canTakePiece(pieces[x][y]) && boardIsClear(pieces, new Point(i,j), new Point(x,y)) && !isPinned(pieces[i][j], new Point(x,y)))
									{
										cantake = true;
									}
								}
							}
						}
					}
				}
			}
		}
		if (numchecks > 1)
		{
			return true;
		}
		if (cantake)
		{
			return false;
		}
		// test if a piece can be interposed.
		if (isDiagonal(new Point(cx,cy), loc))
		{
			int xDiff = Math.abs(cx - loc.x);
			if (cx > loc.x)
			{
				if (cy > loc.y)
				{
					// northeast of king
					for (int i = 1; i < xDiff; i++)
					{
						if (canBeMovedOnto(color, new Point(loc.x+i,loc.y+i)))
						{
							return false;
						}
					}
				}
				else
				{
					// southeast of king
					for (int i = 1; i < xDiff; i++)
					{
						if (canBeMovedOnto(color, new Point(loc.x+i,loc.y-i)))
						{
							return false;
						}
					}
				}
			}
			else
			{
				if (cy > loc.y)
				{
					// northwest of king
					for (int i = 1; i < xDiff; i++)
					{
						if (canBeMovedOnto(color, new Point(loc.x-i,loc.y+i)))
						{
							return false;
						}
					}
				}
				else
				{
					// southwest of king
					for (int i = 1; i < xDiff; i++)
					{
						if (canBeMovedOnto(color, new Point(loc.x-i,loc.y-i)))
						{
							return false;
						}
					}
				}
			}
		}
		else if (isHorizontal(new Point(cx,cy), loc))
		{
			int xDiff = Math.abs(cx - loc.x);
			if (cx > loc.x)
			{
				// east of king
				for (int i = 1; i < xDiff; i++)
				{
					if (canBeMovedOnto(color, new Point(loc.x+i,loc.y)))
					{
						return false;
					}
				}
			}
			else
			{
				// west of king
				for (int i = 1; i < xDiff; i++)
				{
					if (canBeMovedOnto(color, new Point(loc.x-i,loc.y)))
					{
						return false;
					}
				}
			}
		}
		else if (isVertical(new Point(cx,cy), loc))
		{
			int yDiff = Math.abs(cy - loc.y);
			if (cy > loc.y)
			{
				// north of king
				for (int i = 1; i < yDiff; i++)
				{
					if (canBeMovedOnto(color, new Point(loc.x,loc.y+i)))
					{
						return false;
					}
				}
			}
			else
			{
				// south of king
				for (int i = 1; i < yDiff; i++)
				{
					if (canBeMovedOnto(color, new Point(loc.x,loc.y-i)))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean isDiagonal(Point p1, Point p2)
	{
		if (p2.equals(p1))
		{
			return false;
		}
		else if (Math.abs(p2.x - p1.x) == Math.abs(p2.y - p1.y))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean isHorizontal(Point p1, Point p2)
	{
		if (p2.equals(p1))
		{
			return false;
		}
		else if (p2.y == p1.y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean isOutOfPieces(ChessPieceColor color)
	{
		for (int x = 0; x < pieces.length; x++)
		{
			for (int y = 0; y < pieces[x].length; y++)
			{
				if (pieces[x][y] != null && pieces[x][y].getColor() == color)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isPinned(ChessPiece piece, Point destination)
	{
		if (piece == null)
		{
			return false;
		}
		else
		{
			copyBoard();
			Point location = piece.getLocation();
			copy[location.x][location.y].placeOn(destination);
			copy[destination.x][destination.y] = copy[location.x][location.y];
			copy[location.x][location.y] = null;
			return boardIsInCheck(copy, piece.getColor());
		}
	}
	
	private boolean isVertical(Point p1, Point p2)
	{
		if (p2.equals(p1))
		{
			return false;
		}
		else if (p2.x == p1.x)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean newPlayer(ChessPieceColor color)
	{
		for (ChessPieceColor c : players)
		{
			if (c == color)
			{
				return false;
			}
		}
		fireNewPlayerEvent();
		return true;
	}
	
	private void nextPlayer()
	{
		turn = (turn + 1) % players.size();
	}
	
	private void removePlayer(ChessPieceColor color)
	{
		int index = players.indexOf(color);
		players.remove(color);
		canCastle.remove(index);
		turn = turn % players.size();
	}
	
	private void reportChecks(ChessPieceColor color)
	{
		ChessPiece king = findKingOnBoard(pieces, color);
		for (int x = 0; x < pieces.length; x++)
		{
			for (int y = 0; y < pieces[x].length; y++)
			{
				try
				{
					if (pieces[x][y].canTakePiece(king) && boardIsClear(pieces, new Point(x,y), king.getLocation()))
					{
						fireCheckEvent(pieces[x][y]);
					}
				}
				catch (NullPointerException e)
				{
					// do nothing
				}
			}
		}
	}
	
	private void testPawnPromotion(Pawn pawn)
	{
		ChessPieceDirection direction = pawn.getDirection();
		Point loc = pawn.getLocation();
		if (direction == ChessPieceDirection.NORTH)
		{
			if (loc.y == pieces[loc.x].length - 1)
			{
				firePromotePawnEvent(pawn);
			}
		}
		else if (direction == ChessPieceDirection.SOUTH)
		{
			if (loc.y == 0)
			{
				firePromotePawnEvent(pawn);
			}
		}
		else if (direction == ChessPieceDirection.EAST)
		{
			if (loc.x == pieces.length - 1)
			{
				firePromotePawnEvent(pawn);
			}
		}
		else if (direction == ChessPieceDirection.WEST)
		{
			if (loc.x == 0)
			{
				firePromotePawnEvent(pawn);
			}
		}
		else
		{
			// shouldn't happen
			System.exit(0);
		}
	}
	
	public void addChessEventListener(ChessEventListener listener)
	{
		listenerList.add(ChessEventListener.class, listener);
	}
	
	public void removeChessEventListener(ChessEventListener listener)
	{
		listenerList.remove(ChessEventListener.class, listener);
	}
	
	private void fireCaptureEvent(ChessPiece piece)
	{
		captureEvent.setCapturedPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchCaptureEvent(captureEvent);
			}
		}
	}
	
	private void fireCastleEvent(ChessPiece firstPiece, ChessPiece secondPiece)
	{
		canCastle.set(turn, new Boolean(false));
		castleEvent.setFirstPiece(firstPiece);
		castleEvent.setSecondPiece(secondPiece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchCastleEvent(castleEvent);
			}
		}
	}
	
	private void fireCheckEvent(ChessPiece piece)
	{
		checkEvent.setCheckingPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchCheckEvent(checkEvent);
			}
		}
	}
	
	private void fireMoveEvent(ChessPiece piece)
	{
		enPassantPawn = null;
		enPassantSquare = null;
		moveEvent.setMovedPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchMoveEvent(moveEvent);
			}
		}
	}
	
	private void fireNewPlayerEvent()
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchNewPlayerEvent(newPlayerEvent);
			}
		}
	}
	
	private void firePiecePlacedEvent(ChessPiece piece)
	{
		piecePlacedEvent.setPlacedPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchPiecePlacedEvent(piecePlacedEvent);
			}
		}
	}
	
	private void firePieceRemovedEvent(ChessPiece piece)
	{
		pieceRemovedEvent.setRemovedPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchPieceRemovedEvent(pieceRemovedEvent);
			}
		}
	}
	
	private void firePromotePawnEvent(Pawn piece)
	{
		promotePawnEvent.setPromotedPiece(piece);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChessEventListener.class)
			{
				((ChessEventListener)listeners[i+1]).catchPromotePawnEvent(promotePawnEvent);
			}
		}
	}
	
	public void newBoard(int w, int h)
	{
		if (pieces == null)
		{
		}
		else
		{
			for (int x = 0; x < pieces.length; x++)
			{
				for (int y = 0; y < pieces[x].length; y++)
				{
					if (pieces[x][y] != null)
					{
						firePieceRemovedEvent(pieces[x][y]);
					}
				}
			}
		}
		turn = 0;
		players = new ArrayList<ChessPieceColor>(0);
		canCastle = new ArrayList<Boolean>(0);
		pieces = new ChessPiece[w][h];
		mode = ModeMenuConstant.SANDBOX;
	}
	
	public void newStandardGame()
	{
		newBoard(8,8);
		try
		{
			placePiece(new Rook(ChessPieceColor.WHITE).placeOn(new Point(0,0)));
			placePiece(new Knight(ChessPieceColor.WHITE).placeOn(new Point(1,0)));
			placePiece(new Bishop(ChessPieceColor.WHITE).placeOn(new Point(2,0)));
			placePiece(new Queen(ChessPieceColor.WHITE).placeOn(new Point(3,0)));
			placePiece(new King(ChessPieceColor.WHITE).placeOn(new Point(4,0)));
			placePiece(new Bishop(ChessPieceColor.WHITE).placeOn(new Point(5,0)));
			placePiece(new Knight(ChessPieceColor.WHITE).placeOn(new Point(6,0)));
			placePiece(new Rook(ChessPieceColor.WHITE).placeOn(new Point(7,0)));
			
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(0,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(1,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(2,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(3,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(4,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(5,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(6,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(7,1)));
			
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(0,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(1,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(2,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(3,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(4,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(5,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(6,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(7,6)));
			
			placePiece(new Rook(ChessPieceColor.BLACK).placeOn(new Point(0,7)));
			placePiece(new Knight(ChessPieceColor.BLACK).placeOn(new Point(1,7)));
			placePiece(new Bishop(ChessPieceColor.BLACK).placeOn(new Point(2,7)));
			placePiece(new Queen(ChessPieceColor.BLACK).placeOn(new Point(3,7)));
			placePiece(new King(ChessPieceColor.BLACK).placeOn(new Point(4,7)));
			placePiece(new Bishop(ChessPieceColor.BLACK).placeOn(new Point(5,7)));
			placePiece(new Knight(ChessPieceColor.BLACK).placeOn(new Point(6,7)));
			placePiece(new Rook(ChessPieceColor.BLACK).placeOn(new Point(7,7)));
			mode = ModeMenuConstant.STANDARD;
		}
		catch (SquareOccupiedException e)
		{
			// shouldn't happen
			e.printStackTrace();
		}
	}

	public void newZMQuadGame()
	{
		newBoard(8,8);
		try
		{
			placePiece(new King(ChessPieceColor.WHITE).placeOn(new Point(0,0)));
			placePiece(new Rook(ChessPieceColor.WHITE).placeOn(new Point(1,0)));
			placePiece(new Bishop(ChessPieceColor.WHITE).placeOn(new Point(2,0)));
			placePiece(new Knight(ChessPieceColor.WHITE).placeOn(new Point(3,0)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(0,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(1,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(2,1)));
			placePiece(new Pawn(ChessPieceColor.WHITE).placeOn(new Point(3,1)));
			
			placePiece(new King(ChessPieceColor.RED).placeOn(new Point(0,7)));
			placePiece(new Rook(ChessPieceColor.RED).placeOn(new Point(0,6)));
			placePiece(new Bishop(ChessPieceColor.RED).placeOn(new Point(0,5)));
			placePiece(new Knight(ChessPieceColor.RED).placeOn(new Point(0,4)));
			placePiece(new Pawn(ChessPieceColor.RED).placeOn(new Point(1,7)));
			placePiece(new Pawn(ChessPieceColor.RED).placeOn(new Point(1,6)));
			placePiece(new Pawn(ChessPieceColor.RED).placeOn(new Point(1,5)));
			placePiece(new Pawn(ChessPieceColor.RED).placeOn(new Point(1,4)));
			
			placePiece(new King(ChessPieceColor.BLACK).placeOn(new Point(7,7)));
			placePiece(new Rook(ChessPieceColor.BLACK).placeOn(new Point(6,7)));
			placePiece(new Bishop(ChessPieceColor.BLACK).placeOn(new Point(5,7)));
			placePiece(new Knight(ChessPieceColor.BLACK).placeOn(new Point(4,7)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(7,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(6,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(5,6)));
			placePiece(new Pawn(ChessPieceColor.BLACK).placeOn(new Point(4,6)));
			
			placePiece(new King(ChessPieceColor.GREEN).placeOn(new Point(7,0)));
			placePiece(new Rook(ChessPieceColor.GREEN).placeOn(new Point(7,1)));
			placePiece(new Bishop(ChessPieceColor.GREEN).placeOn(new Point(7,2)));
			placePiece(new Knight(ChessPieceColor.GREEN).placeOn(new Point(7,3)));
			placePiece(new Pawn(ChessPieceColor.GREEN).placeOn(new Point(6,0)));
			placePiece(new Pawn(ChessPieceColor.GREEN).placeOn(new Point(6,1)));
			placePiece(new Pawn(ChessPieceColor.GREEN).placeOn(new Point(6,2)));
			placePiece(new Pawn(ChessPieceColor.GREEN).placeOn(new Point(6,3)));
			mode = ModeMenuConstant.ZMQUAD;
		}
		catch (SquareOccupiedException e)
		{
			// shouldn't happen
			e.printStackTrace();
		}
	}
}
