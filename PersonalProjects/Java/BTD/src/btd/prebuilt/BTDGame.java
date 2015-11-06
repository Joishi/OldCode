package btd.prebuilt;

import java.util.ArrayList;

import btd.paintable.paths.Path;
import btd.paintable.towers.Tower;
import btd.paintable.waves.Wave;

public abstract class BTDGame {

	protected Path path;
	protected ArrayList<Tower> towers;
	protected ArrayList<Wave> waves;
	protected int lives;
	protected long money;
	
	public BTDGame() {
		towers = new ArrayList<Tower>();
		waves = new ArrayList<Wave>();
	}
	
	public Path getPath() {
		return path;
	}
	
	public Tower[] getTowers() {
		return towers.toArray(new Tower[0]);
	}
	
	public Wave[] getWaves() {
		return waves.toArray(new Wave[0]);
	}
	
	public int getLives() {
		return lives;
	}
	
	public long getMoney() {
		return money;
	}

}
