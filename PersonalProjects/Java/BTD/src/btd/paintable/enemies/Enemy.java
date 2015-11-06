package btd.paintable.enemies;

import java.awt.geom.Point2D;

import btd.paintable.Paintable;
import btd.paintable.bullets.DamagePacket;
import btd.paintable.paths.Path;



public interface Enemy extends Paintable {

	// state accessor and mutator methods
	void addEnemyListener(EnemyListener listener);
	void removeEnemyListener(EnemyListener listener);
	
	double getTurnRate();
	void setTurnRate(double degreesPerGameTick);
	double getMoveRate();
	void setMoveRate(double pixelsPerGameTick);
	
	Path getPath();
	void setPath(Path path);
	Point2D getDestination();
	void setDestination(Point2D destination);
	Point2D getLastTeleportLocation();
	void setLastTeleportLocation(Point2D location);
	int getPointsVisited();
	void setPointsVisited(int numberOfPointsVisited);
	
	long getMaxHP();
	void setMaxHP(long maximumHP);
	long getCurrentHP();
	void setCurrentHP(long currentHP);
	long getMoneyCarried();
	void setMoneyCarried(long gold);
	long getPointsWorth();
	void setPointsWorth(long points);
	
	int getLevel();
	void setLevel(int level);
	double getHPGrowthRate();
	void setHPGrowthRate(double percentGrowthAsDecimal);
	double getMoneyGrowthRate();
	void setMoneyGrowthRate(double percentGrowthAsDecimal);
	double getPointsGrowthRate();
	void setPointsGrowthRate(double percentGrowthAsDecimal);
	
	// helper methods
	
	// workhorse methods
	void damage(DamagePacket[] damage);

}
