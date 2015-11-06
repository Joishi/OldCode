package btd._mvc;

import java.util.EventListener;

public interface BTDModelListener extends EventListener {

	void gameOverEvent(BTDModelEvent event);
	
	void removeBulletEvent(BTDModelEvent event);
	void removeTowerEvent(BTDModelEvent event);
	void removeEnemyEvent(BTDModelEvent event);
	void removeWaveEvent(BTDModelEvent event);
	void removePathEvent(BTDModelEvent event);
	void addPathEvent(BTDModelEvent event);
	void addTowerEvent(BTDModelEvent event);
	void addWaveEvent(BTDModelEvent event);
	void addBulletEvent(BTDModelEvent event);
	void addEnemyEvent(BTDModelEvent event);
	
	void updateMoneyEvent(BTDModelEvent event);
	void updateScoreEvent(BTDModelEvent event);
	void updateLivesEvent(BTDModelEvent event);
	
	void newGameEvent(BTDModelEvent event);
	void objectSelectedEvent(BTDModelEvent event);

}
