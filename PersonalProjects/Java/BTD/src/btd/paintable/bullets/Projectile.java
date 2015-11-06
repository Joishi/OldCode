package btd.paintable.bullets;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import btd.paintable.enemies.Enemy;

public class Projectile extends AbstractBullet {

	public Projectile() {
		super(null, 1);
		rLoc[0] = new Point2D.Double(0,0);
		rRot[0] = 0;
		rCol[0] = new Color(0,0,0);
		rFill[0] = true;
		rShape[0] = new Ellipse2D.Double(-1, -1, 2, 2);
		
		moveRate = 20;
	}

	@Override
	public void setStart(Point2D startLoc, Enemy startTarget) {
		super.setStart(startLoc, startTarget);
		removeTarget();
	}
	
	@Override
	protected void move() {
		super.move();
		moveRate *= .98;
	}

}
