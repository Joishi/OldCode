package btd.paintable.towers;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import btd.paintable.AbstractPaintable;
import btd.paintable.bullets.Bullet;
import btd.paintable.bullets.DamagePacket;
import btd.paintable.enemies.Enemy;
import btd.paintable.enemies.EnemyEvent;
import btd.paintable.enemies.EnemyListener;

public abstract class AbstractTower extends AbstractPaintable implements Tower, EnemyListener {

	protected EventListenerList listeners;
	
	protected long lastFired;
	protected int delay;
	protected double range;
	protected Enemy target;
	protected int targetSelectionMethod;
	protected boolean lockedOn;
	
	protected ArrayList<DamagePacket> initialDamage;
	protected ArrayList<DamagePacket> continuingDamage;
	
	protected boolean reported;
	
	protected int currentLevel;
	protected int maxLevel;
	
	protected int currentValue;
	protected double sellBackRatio;
	protected double upgradeCostRatio;
	
	public static final int DEFAULT_TOWER_DELAY = 30;        // in ticks per shot
	public static final double DEFAULT_TOWER_RANGE = 100;    // in pixels
	
	public static final double DEFAULT_SELL_COST_RATIO = .75;
	public static final double DEFAULT_UPGRADE_COST_RATIO = .5;
	
	public static final int SELECT_OLDEST = 0;
	public static final int SELECT_YOUNGEST = 1;
	public static final int SELECT_CLOSEST = 2;
	public static final int SELECT_FARTHEST = 3;
	// TODO etc all...
	
	private long oldestAge;
	private long youngestAge;
	private double shortestDistance;
	private double farthestDistance;
	
	private int oldestEnemy;
	private int youngestEnemy;
	private int closestEnemy;
	private int farthestEnemy;
	
	public AbstractTower(Image picture, int depth) {
		super(picture, depth);
		
		listeners = new EventListenerList();
		
		lastFired = 0; // or -delay if fire immediately
		delay = DEFAULT_TOWER_DELAY;
		range = DEFAULT_TOWER_RANGE;
		target = null;
		targetSelectionMethod = SELECT_CLOSEST;
		lockedOn = true;
		
		initialDamage = new ArrayList<DamagePacket>();
		continuingDamage = new ArrayList<DamagePacket>();
		
		reported = false;
		
		currentLevel = 0;
		maxLevel = 0;
		
		currentValue = 0;
		sellBackRatio = DEFAULT_SELL_COST_RATIO;
		upgradeCostRatio = DEFAULT_UPGRADE_COST_RATIO;
		
		super.drawConcrete = true;
	}
	
	@Override
	public abstract Tower makeBlankCopy();
	protected abstract Bullet getBullet();
	
	@Override
	public void update() {
		super.update();
		checkBarrel();
	}
	
	private void checkBarrel() {
		if (lastFired + delay < age) {
			if (!reported) {
				raiseReadyToFireEvent();
			}
		}
	}
	
	@Override
	public boolean selectTarget(Enemy[] enemies) {
		if ((target != null) && (!lockedOn || loc.distance(target.getLocation()) > range)) {
			removeTarget();
		}
		oldestEnemy = -1;
		youngestEnemy = -1;
		closestEnemy = -1;
		farthestEnemy = -1;
		if (enemies.length > 0 && target == null) {
			oldestAge = 0;
			youngestAge = Integer.MAX_VALUE;
			shortestDistance = Integer.MAX_VALUE;
			farthestDistance = 0;
			for (int i = 0; i < enemies.length; i++) {
				Enemy e = enemies[i];
				// Get stats of enemies in range
				if (loc.distance(e.getLocation()) <= range) {
					long eAge = e.getAge();
					double eDist = loc.distance(e.getLocation());
					if (eAge > oldestAge) {
						oldestAge = eAge;
						oldestEnemy = i;
					}
					if (eAge < youngestAge) {
						youngestAge = eAge;
						youngestEnemy = i;
					}
					if (eDist > farthestDistance) {
						farthestDistance = eDist;
						farthestEnemy = i;
					}
					if (eDist < shortestDistance) {
						shortestDistance = eDist;
						closestEnemy = i;
					}
				}
			}
			// select target
			if (targetSelectionMethod == SELECT_OLDEST) {
				if (oldestEnemy >= 0) {
					addTarget(enemies[oldestEnemy]);
				}
			} else if (targetSelectionMethod == SELECT_YOUNGEST) {
				if (youngestEnemy >= 0) {
					addTarget(enemies[youngestEnemy]);
				}
			} else if (targetSelectionMethod == SELECT_CLOSEST) {
				if (closestEnemy >= 0) {
					addTarget(enemies[closestEnemy]);
				}
			} else if (targetSelectionMethod == SELECT_FARTHEST) {
				if (farthestEnemy >= 0) {
					addTarget(enemies[farthestEnemy]);
				}
			} else {
				removeTarget();
			}
		}
		return (target != null);
	}
	
	@Override
	public Bullet fire() {
		if (target != null) {
			Bullet payload = getBullet();
			payload.setStart(loc, target);
			payload.setInitialDamage(initialDamage.toArray(new DamagePacket[0]));
			payload.setContinuingDamage(continuingDamage.toArray(new DamagePacket[0]));
			reported = false;
			lastFired = age;
			return payload;
		} else {
			return null;
		}
	}
	
	protected void addTarget(Enemy target) {
		this.target = target;
		this.target.addEnemyListener(this);
	}
	
	protected void removeTarget() {
		target.removeEnemyListener(this);
		target = null;
	}
	
	public boolean canUpgrade() {
		return currentLevel < maxLevel;
	}
	
	public void upgrade() {
		// TODO upgrade on towers.
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseReadyToFireEvent() {
		reported = true;
		TowerEvent event = new TowerEvent(this,0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == TowerListener.class) {
				((TowerListener)lList[i+1]).readyToFireEvent(event);
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
	public void deathEvent(EnemyEvent event) {
		// remove target
		removeTarget();
	}
	
	@Override
	public void pathCompleteEvent(EnemyEvent event) {
		// do nothing
	}
	
	@Override
	public void teleportEvent(EnemyEvent event) {
		// do nothing
	}
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public void addTowerListener(TowerListener listener) {
		listeners.add(TowerListener.class, listener);
	}
	
	@Override
	public void removeTowerListener(TowerListener listener) {
		listeners.remove(TowerListener.class, listener);
	}
	
	@Override
	public long getAgeOfLastFired() {
		return lastFired;
	}
	
	@Override
	public void setAgeOfLastFired(long ageInGameTicks) {
		lastFired = ageInGameTicks;
	}
	
	@Override
	public int getDelay() {
		return delay;
	}
	
	@Override
	public void setDelay(int delayInGameTicks) {
		delay = delayInGameTicks;
	}
	
	@Override
	public double getRange() {
		return range;
	}
	
	@Override
	public void setRange(double rangeInPixels) {
		range = rangeInPixels;
	}
	
	@Override
	public int getTargetSelectionMethod() {
		return targetSelectionMethod;
	}
	
	@Override
	public void setTargetSelectionMethod(int selectionMethod) {
		targetSelectionMethod = selectionMethod;
	}
	
	@Override
	public boolean getLockedOnStatus() {
		return lockedOn;
	}
	
	@Override
	public void setLockedOnStatus(boolean trueFalse) {
		lockedOn = trueFalse;
	}
	
	@Override
	public DamagePacket[] getInitialDamage() {
		return initialDamage.toArray(new DamagePacket[0]);
	}
	
	@Override
	public void setInitialDamage(DamagePacket[] damages) {
		for (int i = 0; i < damages.length; i++) {
			initialDamage.add(damages[i]);
		}
	}
	
	@Override
	public DamagePacket[] getContinuingDamage() {
		return continuingDamage.toArray(new DamagePacket[0]);
	}
	
	@Override
	public void setContinuingDamage(DamagePacket[] damages) {
		for (int i = 0; i < damages.length; i++) {
			continuingDamage.add(damages[i]);
		}
	}
	
	@Override
	public int getMaxLevel() {
		return maxLevel;
	}
	
	@Override
	public void setMaxLevel(int level) {
		maxLevel = level;
	}
	
	@Override
	public int getCurrentValue() {
		return currentValue;
	}
	
	@Override
	public void setCurrentValue(int value) {
		currentValue = value;
	}
	
	@Override
	public int getSellValue() {
		return (int) (currentValue * sellBackRatio);
	}
	
	@Override
	public void setSellRatio(double ratio) {
		sellBackRatio = ratio;
	}
	
	@Override
	public int getUpgradeValue() {
		return (int) (currentValue * upgradeCostRatio);
	}
	
	@Override
	public void setUpgradeRatio(double ratio) {
		upgradeCostRatio = ratio;
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
