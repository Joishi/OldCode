package btd.paintable.waves;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import btd.paintable.enemies.Grub;


public class GrubWave extends EmptyWave {

	public GrubWave() {
		super(3);
		
		rLoc[0] = new Point2D.Double(-8,0);
		rRot[0] = 0;
		rCol[0] = new Color(20,80,5);
		rFill[0] = true;
		rShape[0] = new Ellipse2D.Double(-3,-3,6,6);
		
		rLoc[1] = new Point2D.Double(-4,0);
		rRot[1] = 0;
		rCol[1] = new Color(39,158,10);
		rFill[1] = true;
		rShape[1] = new Ellipse2D.Double(-4,-4,8,8);
		
		rLoc[2] = new Point2D.Double(2,0);
		rRot[2] = 0;
		rCol[2] = new Color(55,222,14);
		rFill[2] = true;
		rShape[2] = new Ellipse2D.Double(-6,-6,12,12);
		
		for (int i = 0; i < 20; i++) {
			addEnemy(new Grub(), new Integer(DEFAULT_WAVE_DELAY));
		}
	}

}
