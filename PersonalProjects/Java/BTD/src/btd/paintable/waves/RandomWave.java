package btd.paintable.waves;

import java.util.Random;

import btd.paintable.enemies.Grub;
import btd.paintable.enemies.Grub2;
import btd.paintable.enemies.Grub3;

public class RandomWave extends EmptyWave {

	Random rand;
	
	public RandomWave() {
		super(0);
		
		rand = new Random();
		int choice;
		
		for (int i = 0; i < 20; i++) {
			choice = rand.nextInt(3);
			if (choice == 0) {
				addEnemy(new Grub(), new Integer(DEFAULT_WAVE_DELAY));
			} else if (choice == 1) {
				addEnemy(new Grub2(), new Integer(DEFAULT_WAVE_DELAY));
			} else {
				addEnemy(new Grub3(), new Integer(DEFAULT_WAVE_DELAY));
			}
		}
	}

}
