package btd._mvc;

import java.awt.AWTEvent;

import btd.paintable.bullets.Bullet;
import btd.paintable.enemies.Enemy;
import btd.paintable.paths.Path;
import btd.paintable.towers.Tower;
import btd.paintable.waves.Wave;

@SuppressWarnings("serial")
public class BTDModelEvent extends AWTEvent {

	private Bullet bullet;
	private Tower tower;
	private Enemy enemy;
	private Wave wave;
	private Path path;
	private boolean win;
	
	public BTDModelEvent(BTDModel source, int id) {
		super(source, id);
	}
	
	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}
	
	public Bullet getBullet() {
		return bullet;
	}
	
	public void setTower(Tower tower) {
		this.tower = tower;
	}
	
	public Tower getTower() {
		return tower;
	}
	
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}
	
	public void setWave(Wave wave) {
		this.wave = wave;
	}
	
	public Wave getWave() {
		return wave;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return path;
	}
	
	public void setWin(boolean win) {
		this.win = win;
	}
	
	public boolean isWin() {
		return win;
	}

}
