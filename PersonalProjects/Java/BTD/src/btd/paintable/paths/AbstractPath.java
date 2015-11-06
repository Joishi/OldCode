package btd.paintable.paths;

import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import btd.paintable.AbstractPaintable;



public abstract class AbstractPath extends AbstractPaintable implements Path {

	protected Path2D path;
	protected int pathWidth;
	
	protected ArrayList<Integer> warpPoints;
	
	public static final int DEFAULT_PATH_WIDTH = 20;
	public static final double DEFAULT_PATH_ACCURACY = 0.5;
	
	public AbstractPath(int depth, int pathWidth) {
		super(null, depth);
		
		path = new Path2D.Double();
		this.pathWidth = pathWidth;
		
		warpPoints = new ArrayList<Integer>();
		
		stroke = new BasicStroke(pathWidth);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void setWarpPoint() {
		PathIterator pi = path.getPathIterator(null, DEFAULT_PATH_ACCURACY);
		int i = 0;
		while (!pi.isDone()) {
			i++;
			pi.next();
		}
		warpPoints.add(new Integer(i));
	}
	
	@Override
	public boolean intersects(Rectangle2D rect) {
		// TODO somehow get this to work correctly with path width.
		Point2D first = new Point2D.Double();
		Point2D second;
		first.setLocation(getPoint(0));
		int next = 1;
		do {
			second = getPoint(next);
			next++;
			if (second != null) {
				Line2D line = new Line2D.Double(first, second);
				if (rect.intersectsLine(line)) {
					return true;
				} else {
					first.setLocation(second);
				}
			}
		} while (second != null);
		
		return false;
	}
	
	@Override
	public Point2D getPoint(int index) {
		PathIterator pi = path.getPathIterator(null, DEFAULT_PATH_ACCURACY);
		int i;
		for (i = 0; i < index; i++) {
			if (pi.isDone()) {
				return null;
			} else {
				pi.next();
			}
		}
		if (pi.isDone()) {
			return null;
		} else {
			double[] coords = new double[6];
			pi.currentSegment(coords);
			Point2D loc = new Point2D.Double(coords[0],coords[1]);
			return loc;
		}
	}
	
	@Override
	public boolean isWarpPoint(int index) {
		return warpPoints.contains(new Integer(index));
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//                END OF RAISE EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//               START OF CATCH EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
