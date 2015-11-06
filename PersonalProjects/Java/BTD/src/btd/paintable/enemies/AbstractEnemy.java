package btd.paintable.enemies;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.event.EventListenerList;

import btd.paintable.AbstractPaintable;
import btd.paintable.bullets.DamagePacket;
import btd.paintable.paths.Path;

public abstract class AbstractEnemy extends AbstractPaintable implements Enemy {

	protected EventListenerList listeners;
	
	protected double turnRate;
	protected double moveRate;
	
	protected Path path;
	protected Point2D dest;
	protected Point2D lastTeleportLoc;
	protected int pointsVisited;
	
	protected long maxHP;
	protected long currentHP;
	protected long goldCarried;
	protected long pointsWorth;
	
	protected int level;
	protected double hpGrowthRate;
	protected double goldGrowthRate;
	protected double pointsGrowthRate;
	
	public static final double DEFAULT_TURN_RATE = 3;
	public static final double DEFAULT_MOVE_RATE = 2;
	public static final double DEFAULT_GROWTH_RATE = .5;
	
	public AbstractEnemy(Image picture, int depth) {
		super(picture, depth);
		
		listeners = new EventListenerList();
		
		turnRate = Math.toRadians(DEFAULT_TURN_RATE);
		moveRate = DEFAULT_MOVE_RATE;
		
		path = null;
		dest = null;
		lastTeleportLoc = new Point2D.Double();
		pointsVisited = 0;
		
		maxHP = 0;
		currentHP = 0;
		goldCarried = 0;
		pointsWorth = 0;
		
		level = 0;
		hpGrowthRate = DEFAULT_GROWTH_RATE;
		goldGrowthRate = DEFAULT_GROWTH_RATE;
		pointsGrowthRate = DEFAULT_GROWTH_RATE;
	}
	
	@Override
	public void update() {
		super.update();
		if (dest !=  null) {
			turn();
			move();
		}
	}
	
	protected void turn() {
		double targetAngle = calcTargetAngle();
		if (rot != targetAngle) {
			double ccw = targetAngle - rot;
			if (ccw < 0) {
				ccw += 2*Math.PI;
			}
			double cw = 2*Math.PI - ccw;
			if (ccw < turnRate || cw < turnRate) {
				rot = targetAngle;
			} else if (ccw < cw) {
				rot += turnRate;
			} else if (ccw > cw) {
				rot -= turnRate;
			} else {
				Random rand = new Random();
				if (rand.nextBoolean()) {
					rot += turnRate;
				} else {
					rot -= turnRate;
				}
			}
		}
	}
	
	private double calcTargetAngle() {
		double deltaX = dest.getX() - loc.getX();
		double deltaY = dest.getY() - loc.getY();
		return Math.atan2(deltaY, deltaX);
	}
	
	protected void move() {
		double dist = dest.distance(loc);
		if (path.isWarpPoint(pointsVisited)) {
			raiseTeleportEvent();
			destReached();
		} else if (dist > moveRate) {
			double xDist = moveRate*Math.cos(rot);
			double yDist = moveRate*Math.sin(rot);
			loc.setLocation(loc.getX() + xDist, loc.getY() + yDist);
		} else {
			double distRemaining = moveRate;
			while (dest != null && distRemaining >= dist) {
				distRemaining -= dist;
				destReached();
				if (dest != null) {
					dist = dest.distance(loc);
				}
			}
		}
	}
	
	private void destReached() {
		loc.setLocation(dest);
		pointsVisited++;
		dest = path.getPoint(pointsVisited);
		if (dest == null) {
			raisePathCompleteEvent();
		}
	}
	
	@Override
	public void damage(DamagePacket[] damage) {
		// TODO needs lots of special attention
		for (int i = 0; i < damage.length; i++) {
			currentHP -= damage[i].getDamage();
		}
		if (currentHP <= 0) {
			raiseDeathEvent();
		}
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseDeathEvent() {
		EnemyEvent event = new EnemyEvent(this, 0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == EnemyListener.class) {
				((EnemyListener)lList[i+1]).deathEvent(event);
			}
		}
	}
	
	private void raisePathCompleteEvent() {
		lastTeleportLoc.setLocation(loc);
		EnemyEvent event = new EnemyEvent(this, 0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == EnemyListener.class) {
				((EnemyListener)lList[i+1]).pathCompleteEvent(event);
			}
		}
	}
	
	private void raiseTeleportEvent() {
		lastTeleportLoc.setLocation(loc);
		EnemyEvent event = new EnemyEvent(this, 0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == EnemyListener.class) {
				((EnemyListener)lList[i+1]).teleportEvent(event);
			}
		}
	}
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
	@Override
	public void addEnemyListener(EnemyListener listener) {
		listeners.add(EnemyListener.class, listener);
	}
	
	@Override
	public void removeEnemyListener(EnemyListener listener) {
		listeners.remove(EnemyListener.class, listener);
	}
	
	@Override
	public double getTurnRate() {
		return turnRate;
	}
	
	@Override
	public void setTurnRate(double degreesPerGameTick) {
		turnRate = degreesPerGameTick;
	}
	
	@Override
	public double getMoveRate() {
		return moveRate;
	}
	
	@Override
	public void setMoveRate(double pixelsPerGameTick) {
		moveRate = pixelsPerGameTick;
	}
	
	@Override
	public Path getPath() {
		return path;
	}
	
	@Override
	public void setPath(Path path) {
		this.path = path;
		loc.setLocation(path.getPoint(0));
		dest = path.getPoint(1);
		pointsVisited = 1;
		rot = calcTargetAngle();
	}
	
	@Override
	public Point2D getDestination() {
		return dest;
	}
	
	@Override
	public void setDestination(Point2D destination) {
		dest = destination;
	}
	
	@Override
	public Point2D getLastTeleportLocation() {
		return lastTeleportLoc;
	}
	
	@Override
	public void setLastTeleportLocation(Point2D location) {
		lastTeleportLoc.setLocation(location);
	}
	
	@Override
	public int getPointsVisited() {
		return pointsVisited;
	}
	
	@Override
	public void setPointsVisited(int numberOfPointsVisited) {
		pointsVisited = numberOfPointsVisited;
	}
	
	@Override
	public long getMaxHP() {
		return maxHP;
	}
	
	@Override
	public void setMaxHP(long maximumHP) {
		maxHP = maximumHP;
	}
	
	@Override
	public long getCurrentHP() {
		return currentHP;
	}
	
	@Override
	public void setCurrentHP(long currentHP) {
		this.currentHP = currentHP;
	}
	
	@Override
	public long getMoneyCarried() {
		return goldCarried;
	}
	
	@Override
	public void setMoneyCarried(long gold) {
		goldCarried = gold;
	}
	
	@Override
	public long getPointsWorth() {
		return pointsWorth;
	}
	
	@Override
	public void setPointsWorth(long points) {
		pointsWorth = points;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public void setLevel(int level) {
		if (this.level < level) {
			for ( ; this.level < level; this.level++) {
				maxHP *= (1+hpGrowthRate);
				currentHP *= (1+hpGrowthRate);
				goldCarried *= (1+goldGrowthRate);
				pointsWorth *= (1+pointsGrowthRate);
			}
		} else {
			for ( ; this.level > level; this.level--) {
				maxHP /= (1+hpGrowthRate);
				currentHP /= (1+hpGrowthRate);
				goldCarried /= (1+goldGrowthRate);
				pointsWorth /= (1+pointsGrowthRate);
			}
		}
	}
	
	@Override
	public double getHPGrowthRate() {
		return hpGrowthRate;
	}
	
	@Override
	public void setHPGrowthRate(double percentGrowthAsDecimal) {
		hpGrowthRate = percentGrowthAsDecimal;
	}
	
	@Override
	public double getMoneyGrowthRate() {
		return goldGrowthRate;
	}
	
	@Override
	public void setMoneyGrowthRate(double percentGrowthAsDecimal) {
		goldGrowthRate = percentGrowthAsDecimal;
	}
	
	@Override
	public double getPointsGrowthRate() {
		return pointsGrowthRate;
	}
	
	@Override
	public void setPointsGrowthRate(double percentGrowthAsDecimal) {
		pointsGrowthRate = percentGrowthAsDecimal;
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
