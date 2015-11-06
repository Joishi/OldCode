package btd.paintable.bullets;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import btd.paintable.enemies.Enemy;

public class Laser extends AbstractBullet {

	public Laser() {
		super(null, 1);
		rLoc[0] = new Point2D.Double(0,0);
		rRot[0] = 0;
		rCol[0] = new Color(21,230,57);
		rFill[0] = false;
	}

	@Override
	public void setStart(Point2D startLoc, Enemy startTarget) {
		super.setStart(startLoc, startTarget);
		rot = 0;
		loc.setLocation(dest);
		rShape[0] = new Line2D.Double(0,0,startLoc.getX() - loc.getX(),startLoc.getY() - loc.getY());
	}
	
	@Override
	protected void move() {
		destReached();
	}
	
	@Override
	protected void turn() {
		
	}

}
