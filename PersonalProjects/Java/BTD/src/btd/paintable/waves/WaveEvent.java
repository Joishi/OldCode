package btd.paintable.waves;

import java.awt.AWTEvent;

import btd.paintable.enemies.Enemy;


@SuppressWarnings("serial")
public class WaveEvent extends AWTEvent {

	private Enemy enemy;
	
	public WaveEvent(Wave source, int id) {
		super(source, id);
	}
	
	public Wave getWave() {
		return (Wave) source;
	}
	
	public void setEnemy(Enemy e) {
		enemy = e;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}

}
