/**
 * 
 */
package usa.or.boyd.chess.event;

import java.util.EventListener;

/**
 * @author Joshua
 *
 */
public interface ChessEventListener extends EventListener
{
	/**
	 * 
	 * @param event
	 */
	void catchCaptureEvent(CaptureEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchCastleEvent(CastleEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchCheckEvent(CheckEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchMoveEvent(MoveEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchNewPlayerEvent(NewPlayerEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchPiecePlacedEvent(PiecePlacedEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchPieceRemovedEvent(PieceRemovedEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void catchPromotePawnEvent(PromotePawnEvent event);
}
