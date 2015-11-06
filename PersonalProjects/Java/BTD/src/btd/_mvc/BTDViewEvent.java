package btd._mvc;

import java.awt.AWTEvent;
import java.awt.geom.Point2D;

import btd.paintable.towers.Tower;
import btd.prebuilt.BTDGame;

@SuppressWarnings("serial")
public class BTDViewEvent extends AWTEvent {

	private Tower tower;
	private BTDGame game;
	private Point2D location;
	
	public BTDViewEvent(BTDView source, int id) {
		super(source, id);
	}
	
	public void setTower(Tower tower) {
		this.tower = tower;
	}
	
	public Tower getTower() {
		return tower;
	}
	
	public void setGame(BTDGame game) {
		this.game = game;
	}
	
	public BTDGame getGame() {
		return game;
	}
	
	public void setLocation(Point2D location) {
		this.location = location;
	}
	
	public Point2D getLocation() {
		return location;
	}

}
