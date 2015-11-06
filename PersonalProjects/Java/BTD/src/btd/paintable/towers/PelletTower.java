package btd.paintable.towers;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import btd.paintable.bullets.Bullet;
import btd.paintable.bullets.DamagePacket;
import btd.paintable.bullets.Projectile;

public class PelletTower extends AbstractTower {

	public PelletTower() {
		super(null, 3);
		
		rLoc[0] = new Point2D.Double(0,0);
		rRot[0] = 0;
		rCol[0] = new Color(20,40,10);
		rFill[0] = true;
		rShape[0] = new Ellipse2D.Double(-10,-10,20,20);
		
		rLoc[1] = new Point2D.Double(0,0);
		rRot[1] = Math.toRadians(45);
		rCol[1] = new Color(70,40,25);
		rFill[1] = true;
		rShape[1] = new Rectangle2D.Double(-7,-7,14,14);
		
		rLoc[2] = new Point2D.Double(0,0);
		rRot[2] = 0;
		rCol[2] = new Color(30,35,86);
		rFill[2] = true;
		rShape[2] = new Rectangle2D.Double(-6,-6,12,12);
		
		initialDamage.add(new DamagePacket(10, DamagePacket.PHYSICAL));
		currentValue = 50;
		maxLevel = 10;
	}
	
	public Tower makeBlankCopy() {
		return new PelletTower();
	}
	
	protected Bullet getBullet() {
		return new Projectile();
	}

}
