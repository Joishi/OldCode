package btd.paintable.enemies;

import java.awt.AWTEvent;

@SuppressWarnings("serial")
public class EnemyEvent extends AWTEvent {

	public EnemyEvent(Enemy source, int id) {
		super(source, id);
	}
	
	public Enemy getEnemy() {
		return (Enemy) source;
	}

}
