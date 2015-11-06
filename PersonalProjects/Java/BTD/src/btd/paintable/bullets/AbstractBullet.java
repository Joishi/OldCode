package btd.paintable.bullets;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.EventListenerList;

import btd.paintable.AbstractPaintable;
import btd.paintable.enemies.Enemy;
import btd.paintable.enemies.EnemyEvent;
import btd.paintable.enemies.EnemyListener;

public abstract class AbstractBullet extends AbstractPaintable implements Bullet, EnemyListener {

	protected EventListenerList listeners;
	
	protected double turnRate;
	protected double moveRate;
	
	protected Enemy target;
	protected Point2D dest;
	
	protected ArrayList<DamagePacket> initialDamage;
	protected ArrayList<DamagePacket> continuingDamage;
	protected ArrayList<Integer> ticksToNextDamage;
	protected int explosionCounter;
	protected int explosionNumber;
	
	protected ArrayList<Integer> properties;
	protected double splashRadius;
	
	public static final Integer DETONATED = new Integer(0);
	public static final Integer SMART = new Integer(1);
	public static final Integer HOMING = new Integer(2);
	public static final Integer SPLASH = new Integer(3);
	
	public static final double DEFAULT_TURN_RATE = 4; // in degrees per tick
	public static final double DEFAULT_MOVE_RATE = 2; // in pixels per tick
	public static final double DEFAULT_SPLASH_RADIUS = 0; // in pixels
	
	public AbstractBullet(Image picture, int depth) {
		super(picture, depth);
		listeners = new EventListenerList();
		
		turnRate = DEFAULT_TURN_RATE;
		moveRate = DEFAULT_MOVE_RATE;
		
		target = null;
		dest = new Point2D.Double();
		
		initialDamage = new ArrayList<DamagePacket>();
		continuingDamage = new ArrayList<DamagePacket>();
		ticksToNextDamage = new ArrayList<Integer>();
		explosionCounter = 0;
		explosionNumber = 0;
		
		properties = new ArrayList<Integer>();
		splashRadius = DEFAULT_SPLASH_RADIUS;
	}
	
	@Override
	public void setStart(Point2D startLoc, Enemy startTarget) {
		addTarget(startTarget);
		loc.setLocation(startLoc);
		dest.setLocation(target.getLocation());
		rot = calcTargetAngle();
	}
	
	@Override
	public void updateTarget(Enemy newTarget) {
		if (isSmart()) {
			removeTarget();
			addTarget(newTarget);
			if (isHoming()) {
				dest.setLocation(target.getLocation());
			}
		}
	}
	
	@Override
	public void update() {
		super.update();
		turn();
		move();
	}
	
	protected void turn() {
		if (!hasDetonated() && isHoming()) {
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
	}
	
	private double calcTargetAngle() {
		Point2D d = new Point2D.Double();
		if (isHoming()) {
			d.setLocation(target.getLocation());
		} else {
			d.setLocation(dest);
		}
		double xDist = d.getX() - loc.getX();
		double yDist = d.getY() - loc.getY();
		return Math.atan2(yDist, xDist);
	}
	
	protected void move() {
		if (!hasDetonated()) {
			Point2D d = new Point2D.Double();
			if (isHoming()) {
				d.setLocation(target.getLocation());
			} else {
				d.setLocation(dest);
			}
			double dist = d.distance(loc);
			if (dist > moveRate) {
				double xDist = moveRate*Math.cos(rot);
				double yDist = moveRate*Math.sin(rot);
				loc.setLocation(loc.getX() + xDist, loc.getY() + yDist);
			} else {
				loc.setLocation(d);
				destReached();
			}
		} else {
			destReached();
		}
	}
	
	protected void destReached() {
		if (hasDetonated()) {
			explosionCounter++;
			if (explosionCounter >= ticksToNextDamage.get(explosionNumber).intValue()) {
				raiseDetonationEvent();
			}
		} else {
			raiseDetonationEvent();
		}
	}
	
	protected void addTarget(Enemy target) {
		if (target != null) {
			this.target = target;
			this.target.addEnemyListener(this);
		}
	}
	
	protected void removeTarget() {
		if (target != null) {
			target.removeEnemyListener(this);
			target = null;
		}
	}
	
	protected void addProperty(Integer property) {
		properties.add(property);
	}
	
	protected void removeProperty(Integer property) {
		properties.remove(property);
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseDetonationEvent() {
		if (target != null) {
			target.removeEnemyListener(this);
		}
		BulletEvent event = new BulletEvent(this,0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -=2) {
			if (lList[i] == BulletListener.class) {
				((BulletListener)lList[i+1]).detonationEvent(event);
			}
		}
		if (explosionNumber >= ticksToNextDamage.size()) {
			raiseSpentEvent();
		}
	}
	
	private void raiseRetargetEvent() {
		BulletEvent event = new BulletEvent(this,0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -=2) {
			if (lList[i] == BulletListener.class) {
				((BulletListener)lList[i+1]).retargetEvent(event);
			}
		}
	}
	
	private void raiseSpentEvent() {
		BulletEvent event = new BulletEvent(this,0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -=2) {
			if (lList[i] == BulletListener.class) {
				((BulletListener)lList[i+1]).spentEvent(event);
			}
		}
	}
	//////////////////////////////////////////////////////////////
	//                END OF RAISE EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//               START OF CATCH EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	@Override
	public void deathEvent(EnemyEvent e) {
		if (isSmart()) {
			dest.setLocation(target.getLocation());
			raiseRetargetEvent();
		} else {
			if (isHoming()) {
				dest.setLocation(target.getLocation());
				removeProperty(HOMING);
			}
			removeTarget();
		}
	}
	
	@Override
	public void pathCompleteEvent(EnemyEvent e) {
		if (isSmart()) {
			raiseRetargetEvent();
		} else {
			if (isHoming()) {
				dest.setLocation(target.getLastTeleportLocation());
				removeProperty(HOMING);
			}
			removeTarget();
		}
	}
	
	@Override
	public void teleportEvent(EnemyEvent e) {
		// TODO how do bullets respond to an enemy teleporting?
	}
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public void addBulletListener(BulletListener listener) {
		listeners.add(BulletListener.class, listener);
	}
	
	@Override
	public void removeBulletListener(BulletListener listener) {
		listeners.remove(BulletListener.class, listener);
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
	public Enemy getTarget() {
		return target;
	}
	
	@Override
	public void setTarget(Enemy target) {
		this.target = target;
	}
	
	@Override
	public Point2D getDestination() {
		return dest;
	}
	
	@Override
	public void setDestination(Point2D destination) {
		dest.setLocation(destination);
	}
	
	@Override
	public DamagePacket[] getDamage() {
		if (!properties.contains(DETONATED)) {
			properties.add(DETONATED);
			return initialDamage.toArray(new DamagePacket[0]);
		} else {
			explosionCounter = 0;
			explosionNumber++;
			return continuingDamage.toArray(new DamagePacket[0]);
		}
	}
	
	@Override
	public void setInitialDamage(DamagePacket[] damage) {
		for (int i = 0; i < damage.length; i++) {
			initialDamage.add(damage[i]);
		}
	}
	
	@Override
	public void setContinuingDamage(DamagePacket[] damage) {
		for (int i = 0; i < damage.length; i++) {
			continuingDamage.add(damage[i]);
		}
	}
	
	@Override
	public Integer[] getTicksOfExplosions() {
		return ticksToNextDamage.toArray(new Integer[0]);
	}
	
	@Override
	public void setTicksOfExlposions(Integer[] durationsInGameTicks) {
		for (int i = 0; i < durationsInGameTicks.length; i++) {
			ticksToNextDamage.add(durationsInGameTicks[i]);
		}
	}
	
	@Override
	public boolean hasDetonated() {
		return properties.contains(DETONATED);
	}
	
	@Override
	public boolean isSmart() {
		return properties.contains(SMART);
	}
	
	@Override
	public boolean isHoming() {
		return properties.contains(HOMING);
	}
	
	@Override
	public boolean isProjectile() {
		return	!properties.contains(SMART) &&
				!properties.contains(HOMING);
	}
	
	@Override
	public boolean isSplash() {
		return properties.contains(SPLASH);
	}
	
	@Override
	public double getSplashRadius() {
		return splashRadius;
	}
	
	@Override
	public void setSplashRadius(double radiusInPixels) {
		splashRadius = radiusInPixels;
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
