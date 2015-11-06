package btd._mvc;

import java.awt.geom.Point2D;

import btd.paintable.Paintable;
import btd.paintable.towers.Tower;
import btd.prebuilt.BTDGame;

public interface BTDModel {

	// this sends events to the view
	// the view has a direct link to this
	// the controller has a direct link to this
	
	// state accessor and mutator methods
	void addModelListener(BTDModelListener listener);
	void removeModelListener(BTDModelListener listener);
	
	int getLives();            // for BTDView to access
	void setLives(int lives);  // for BTDControllre to access
	long getMoney();           // for BTDView to access
	void setMoney(long money); // for BTDController to access
	long getScore();           // for BTDView to access
	void setScore(long score); // for BTDController to access
	
	// helper methods
	void setupGame(BTDGame game);
	void sendWave();
	boolean addTower(Tower tower, Point2D location);
	void mouseClickAt(Point2D location);
	Paintable getSelectedObject();
	void clearSelectedObject();
	void sellTower();
	void upgradeTower();
	
	// workhorse methods
	void update();
	void pause();
	
	public static final double BLAST_RADIUS = 15;
	
	public static final int DEFAULT_STARTING_LIVES = 20;
	public static final long DEFAULT_STARTING_MONEY = 200;

}
