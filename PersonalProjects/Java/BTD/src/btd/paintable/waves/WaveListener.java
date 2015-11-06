package btd.paintable.waves;

import java.util.EventListener;

public interface WaveListener extends EventListener {

	void newEnemyEvent(WaveEvent event);
	void waveDoneEvent(WaveEvent event);

}
