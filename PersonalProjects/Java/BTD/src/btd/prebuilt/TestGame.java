package btd.prebuilt;

import java.awt.geom.Point2D;

import btd._mvc.BTDModel;
import btd.paintable.paths.Snake;
import btd.paintable.towers.RocketTower;
import btd.paintable.towers.Tower;
import btd.paintable.waves.RandomWave;
import btd.paintable.waves.Wave;

public class TestGame extends BTDGame {

	public TestGame() {
		path = new Snake();
		Tower tower = new RocketTower();
		tower.setLocation(new Point2D.Double(300,200));
		towers.add(tower);
		Wave wave;
		for (int i = 0; i < 20; i++) {
			wave = new RandomWave();
			wave.setLevel(i);
			waves.add(wave);
		}
		lives = BTDModel.DEFAULT_STARTING_LIVES;
		money = BTDModel.DEFAULT_STARTING_MONEY;
	}

}
