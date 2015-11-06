package btd.paintable.bullets;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Rocket extends AbstractBullet {

	protected double rocketSpeedIncrease;
	
	public Rocket() {
		super(null, 2);
		
		Polygon rocket = new Polygon();
		rocket.xpoints = new int[] { 0, 6,  0 };
		rocket.ypoints = new int[] { 2, 0, -2 };
		rocket.npoints = 3;
		Polygon fire = new Polygon();
		fire.xpoints = new int[] { 0, -3,  0 };
		fire.ypoints = new int[] { 2,  0, -2 };
		fire.npoints = 3;
		
		rLoc[0] = new Point2D.Double(0,0);
		rRot[0] = 0;
		rCol[0] = new Color(20,20,20);
		rFill[0] = true;
		rShape[0] = rocket;
		
		rLoc[1] = new Point2D.Double(0,0);
		rRot[1] = 0;
		rCol[1] = new Color(255,50,0);
		rFill[1] = true;
		rShape[1] = fire;
		
		splashRadius = 10;
		
		addProperty(HOMING);
		
		moveRate = 2;
		rocketSpeedIncrease = .2;
	}
	
	@Override
	protected void move() {
		super.move();
		moveRate += rocketSpeedIncrease;
	}

}
