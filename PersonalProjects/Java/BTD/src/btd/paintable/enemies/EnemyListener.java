package btd.paintable.enemies;

import java.util.EventListener;

public interface EnemyListener extends EventListener {

	void deathEvent(EnemyEvent event);
	void pathCompleteEvent(EnemyEvent event);
	void teleportEvent(EnemyEvent event);

}
