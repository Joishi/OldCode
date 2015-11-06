package btd.paintable.waves;

import java.util.ArrayList;

public class EmptyWavePack {

	protected ArrayList<Wave> waves;
	
	public EmptyWavePack() {
		waves = new ArrayList<Wave>();
	}
	
	public void addWave(Wave wave) {
		waves.add(wave);
	}
	
	public void removeWave(Wave wave) {
		waves.remove(wave);
	}

}
