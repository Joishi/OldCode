package btd.paintable.bullets;

import java.awt.geom.Point2D;

import btd.paintable.Paintable;
import btd.paintable.enemies.Enemy;




public interface Bullet extends Paintable {

	// state accessor and mutator methods
	void addBulletListener(BulletListener listener);
	void removeBulletListener(BulletListener listener);
	
	double getTurnRate();
	void setTurnRate(double degreesPerGameTick);
	double getMoveRate();
	void setMoveRate(double pixelsPerGameTick);
	
	Enemy getTarget();
	void setTarget(Enemy target);
	Point2D getDestination();
	void setDestination(Point2D destination);
	
	DamagePacket[] getDamage();
	void setInitialDamage(DamagePacket[] damage);
	void setContinuingDamage(DamagePacket[] damage);
	Integer[] getTicksOfExplosions();
	void setTicksOfExlposions(Integer[] durationsInGameTicks);
	
	boolean hasDetonated();
	boolean isSmart();
	boolean isHoming();
	boolean isProjectile();
	boolean isSplash();
	
	double getSplashRadius();
	void setSplashRadius(double radiusInPixels);
	
	// helper methods
	
	// workhorse methods
	void setStart(Point2D startLoc, Enemy startTarget);
	void updateTarget(Enemy newTarget);

}
