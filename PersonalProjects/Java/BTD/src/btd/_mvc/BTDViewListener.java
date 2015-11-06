package btd._mvc;

import java.util.EventListener;

public interface BTDViewListener extends EventListener {

	void towerSelectedEvent(BTDViewEvent event);
	void sellTowerEvent(BTDViewEvent event);
	void upgradeTowerEvent(BTDViewEvent event);
	
	void cancelEvent(BTDViewEvent event);
	
	void newGameEvent(BTDViewEvent event);
	void sendWaveEvent(BTDViewEvent event);
	void pauseEvent(BTDViewEvent event);
	
	void fieldClickedEvent(BTDViewEvent event);

}
