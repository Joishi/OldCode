package btd._mvc;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import btd.paintable.towers.Tower;

public interface BTDView extends BTDModelListener {

	// this has a direct link to the model
	// this receives events from the model
	// this sends events to the controller
	// the controller has a direct link to this
	
	// state accessor and mutator methods
	void addViewListener(BTDViewListener listener);
	void removeViewListener(BTDViewListener listener);
	
	// helper methods
	Tower getSelectedTower();
	void cancelTowerListSelection();
	
	// workhorse methods
	void update();
	
	
	
	
	public static final int frameULX = 0;
	public static final int frameULY = 0;
	public static final int frameWidth = 500;
	public static final int frameHeight = 500;
	
	public static final Dimension frame = new Dimension(frameWidth,frameHeight);
	
	public static final double[] x = { 0, 350, 370, 485, 500 };
	public static final double[] y = { 0,  21, 100, 150, 165,
		                             180, 195, 210, 225, 240,
		                             255, 270, 285, 300, 315,
		                             330, 345, 360, 375, 390,
		                             405, 420, 435, 450, 500 };
	
	public static final Rectangle2D gameStatsZone = new Rectangle2D.Double(x[0],y[1],x[4]-x[0],y[3]-y[1]);
		public static final Rectangle2D sendZone = new Rectangle2D.Double(x[1],y[2],x[3]-x[1],y[3]-y[2]);
		public static final Rectangle2D pauseZone = new Rectangle2D.Double(x[3],y[2],x[4]-x[3],y[3]-y[2]);
	
	public static final Rectangle2D gameFieldZone = new Rectangle2D.Double(x[0],y[3],x[1]-x[0],y[24]-y[3]);
	
	public static final Rectangle2D towerListZone = new Rectangle2D.Double(x[1],y[3],x[4]-x[1],y[21]-y[3]);
	
	public static final Rectangle2D selectedZone = new Rectangle2D.Double(x[1],y[21],x[4]-x[1],y[24]-y[21]);
		public static final Point2D selectedTowerLoc = new Point2D.Double(x[2],y[22]);
		public static final Rectangle2D selectedTowerZone = new Rectangle2D.Double(x[1],y[21],x[4]-x[1],y[23]-y[21]);
		public static final Rectangle2D upgradeTowerZone = new Rectangle2D.Double(x[1],y[23],x[3]-x[1],y[24]-y[23]);
		public static final Rectangle2D sellTowerZone = new Rectangle2D.Double(x[3],y[23],x[4]-x[3],y[24]-y[23]);
	
	public static final Point2D[] towerLoc = {
		new Point2D.Double(x[2],y[4]),
		new Point2D.Double(x[2],y[6]),
		new Point2D.Double(x[2],y[8]),
		new Point2D.Double(x[2],y[10]),
		new Point2D.Double(x[2],y[12]),
		new Point2D.Double(x[2],y[14]),
		new Point2D.Double(x[2],y[16]),
		new Point2D.Double(x[2],y[18]),
		new Point2D.Double(x[2],y[20])
	};
	
	public static final Rectangle2D[] towerZone = {
		new Rectangle2D.Double(x[1],y[3],x[4]-x[1],y[5]-y[3]),
		new Rectangle2D.Double(x[1],y[5],x[4]-x[1],y[7]-y[5]),
		new Rectangle2D.Double(x[1],y[7],x[4]-x[1],y[9]-y[7]),
		new Rectangle2D.Double(x[1],y[9],x[4]-x[1],y[11]-y[9]),
		new Rectangle2D.Double(x[1],y[11],x[4]-x[1],y[13]-y[11]),
		new Rectangle2D.Double(x[1],y[13],x[4]-x[1],y[15]-y[13]),
		new Rectangle2D.Double(x[1],y[15],x[4]-x[1],y[17]-y[15]),
		new Rectangle2D.Double(x[1],y[17],x[4]-x[1],y[19]-y[17]),
		new Rectangle2D.Double(x[1],y[19],x[4]-x[1],y[21]-y[19])
	};
	
	public static final Rectangle2D[] towerX = {
		new Rectangle2D.Double(x[3],y[3],x[4]-x[3],y[4]-y[3]),
		new Rectangle2D.Double(x[3],y[5],x[4]-x[3],y[6]-y[5]),
		new Rectangle2D.Double(x[3],y[7],x[4]-x[3],y[8]-y[7]),
		new Rectangle2D.Double(x[3],y[9],x[4]-x[3],y[10]-y[9]),
		new Rectangle2D.Double(x[3],y[11],x[4]-x[3],y[12]-y[11]),
		new Rectangle2D.Double(x[3],y[13],x[4]-x[3],y[14]-y[13]),
		new Rectangle2D.Double(x[3],y[15],x[4]-x[3],y[16]-y[15]),
		new Rectangle2D.Double(x[3],y[17],x[4]-x[3],y[18]-y[17]),
		new Rectangle2D.Double(x[3],y[19],x[4]-x[3],y[20]-y[19])
	};
	
	public static final Rectangle2D[] towerN = {
		new Rectangle2D.Double(x[3],y[4],x[4]-x[3],y[5]-y[4]),
		new Rectangle2D.Double(x[3],y[6],x[4]-x[3],y[7]-y[6]),
		new Rectangle2D.Double(x[3],y[8],x[4]-x[3],y[9]-y[8]),
		new Rectangle2D.Double(x[3],y[10],x[4]-x[3],y[11]-y[10]),
		new Rectangle2D.Double(x[3],y[12],x[4]-x[3],y[13]-y[12]),
		new Rectangle2D.Double(x[3],y[14],x[4]-x[3],y[15]-y[14]),
		new Rectangle2D.Double(x[3],y[16],x[4]-x[3],y[17]-y[16]),
		new Rectangle2D.Double(x[3],y[18],x[4]-x[3],y[19]-y[18]),
		new Rectangle2D.Double(x[3],y[20],x[4]-x[3],y[21]-y[20])
	};
	
	public static final String PATH1 = "path1";
	public static final String PATH2 = "path2";
	
	public static final String WAVE1 = "wave1";
	public static final String WAVE2 = "wave2";
	
	public static final String GAME1 = "one";

}
