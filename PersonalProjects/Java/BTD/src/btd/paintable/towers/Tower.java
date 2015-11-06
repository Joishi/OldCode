package btd.paintable.towers;

import btd.paintable.Paintable;
import btd.paintable.bullets.Bullet;
import btd.paintable.bullets.DamagePacket;
import btd.paintable.enemies.Enemy;

public interface Tower extends Paintable {

	// state accessor and mutator methods
	void addTowerListener(TowerListener listener);
	void removeTowerListener(TowerListener listener);
	
	long getAgeOfLastFired();
	void setAgeOfLastFired(long ageInGameTicks);
	int getDelay();
	void setDelay(int delayInGameTicks);
	double getRange();
	void setRange(double rangeInPixels);
	int getTargetSelectionMethod();
	void setTargetSelectionMethod(int selectionMethod);
	boolean getLockedOnStatus();
	void setLockedOnStatus(boolean trueFalse);
	
	DamagePacket[] getInitialDamage();
	void setInitialDamage(DamagePacket[] damages);
	DamagePacket[] getContinuingDamage();
	void setContinuingDamage(DamagePacket[] damages);
	
	int getMaxLevel();
	void setMaxLevel(int level);
	
	int getCurrentValue();
	void setCurrentValue(int value);
	int getSellValue();
	void setSellRatio(double ratio);
	int getUpgradeValue();
	void setUpgradeRatio(double ratio);
	
	// helper methods
	Tower makeBlankCopy();
	
	boolean canUpgrade();
	void upgrade();
	
	// workhorse methods
	boolean selectTarget(Enemy[] enemies);
	Bullet fire();

}
