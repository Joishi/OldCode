package btd.paintable.waves;

import btd.paintable.Paintable;
import btd.paintable.enemies.Enemy;

public interface Wave extends Paintable {

	// state accessor and mutator methods
	void addWaveListener(WaveListener listener);
	void removeWaveListener(WaveListener listener);
	
	void addEnemy(Enemy e, int delayFromPreviousEnemyInGameTicks);
	
	int getLevel();
	void setLevel(int level);
	
	// helper methods
	int size();
	
	// workhorse methods

}
