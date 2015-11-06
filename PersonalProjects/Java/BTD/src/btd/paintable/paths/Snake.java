package btd.paintable.paths;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Snake extends AbstractPath {

	private Color pathColor = Color.BLUE;
	
	public Snake() {
		super(11, 12);
		loc = new Point2D.Double(0,0);
		rot = 0;
		
		rLoc[0] = new Point2D.Double(0,0);
		rRot[0] = 0;
		rCol[0] = pathColor;
		rFill[0] = false;
		rShape[0] = new Arc2D.Double(0-42,220-42,84,84,90,-90,Arc2D.OPEN);
		
		rLoc[1] = new Point2D.Double(0,0);
		rRot[1] = 0;
		rCol[1] = pathColor;
		rFill[1] = false;
		rShape[1] = new Arc2D.Double(84-42,220-42,84,84,180,180,Arc2D.OPEN);
		
		rLoc[2] = new Point2D.Double(0,0);
		rRot[2] = 0;
		rCol[2] = pathColor;
		rFill[2] = false;
		rShape[2] = new Arc2D.Double(168-42,220-42,84,84,180,-180,Arc2D.OPEN);
		
		rLoc[3] = new Point2D.Double(0,0);
		rRot[3] = 0;
		rCol[3] = pathColor;
		rFill[3] = false;
		rShape[3] = new Arc2D.Double(252-42,220-42,84,84,180,90,Arc2D.OPEN);
		
		rLoc[4] = new Point2D.Double(0,0);
		rRot[4] = 0;
		rCol[4] = pathColor;
		rFill[4] = false;
		rShape[4] = new Arc2D.Double(252-42,304-42,84,84,90,-90,Arc2D.OPEN);
		
		rLoc[5] = new Point2D.Double(0,0);
		rRot[5] = 0;
		rCol[5] = pathColor;
		rFill[5] = false;
		rShape[5] = new Line2D.Double(294,304,294,346);
		
		rLoc[6] = new Point2D.Double(0,0);
		rRot[6] = 0;
		rCol[6] = pathColor;
		rFill[6] = false;
		rShape[6] = new Arc2D.Double(252-42,346-42,84,84,0,-90,Arc2D.OPEN);
		
		rLoc[7] = new Point2D.Double(0,0);
		rRot[7] = 0;
		rCol[7] = pathColor;
		rFill[7] = false;
		rShape[7] = new Arc2D.Double(252-42,430-42,84,84,90,90,Arc2D.OPEN);
		
		rLoc[8] = new Point2D.Double(0,0);
		rRot[8] = 0;
		rCol[8] = pathColor;
		rFill[8] = false;
		rShape[8] = new Arc2D.Double(168-42,430-42,84,84,0,-180,Arc2D.OPEN);
		
		rLoc[9] = new Point2D.Double(0,0);
		rRot[9] = 0;
		rCol[9] = pathColor;
		rFill[9] = false;
		rShape[9] = new Arc2D.Double(84-42,430-42,84,84,0,180,Arc2D.OPEN);
		
		rLoc[10] = new Point2D.Double(0,0);
		rRot[10] = 0;
		rCol[10] = pathColor;
		rFill[10] = false;
		rShape[10] = new Arc2D.Double(0-42,430-42,84,84,0,-90,Arc2D.OPEN);
		
		path.append(rShape[0], true);
		path.append(rShape[1], true);
		path.append(rShape[2], true);
		path.append(rShape[3], true);
		path.append(rShape[4], true);
		path.append(rShape[5], true);
		path.append(rShape[6], true);
		path.append(rShape[7], true);
		path.append(rShape[8], true);
		path.append(rShape[9], true);
		path.append(rShape[10], true);
	}

}
