package btd.paintable.paths;

import java.awt.geom.Point2D;

import btd.paintable.Paintable;



public interface Path extends Paintable {

	// state accessor and mutator methods
	
	// helper methods
	Point2D getPoint(int index);
	void setWarpPoint();
	boolean isWarpPoint(int index);
	
	// workhorse methods

}
