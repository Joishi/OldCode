package btd.paintable.waves;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import btd.paintable.AbstractPaintable;
import btd.paintable.enemies.Enemy;



public class EmptyWave extends AbstractPaintable implements Wave {

	protected EventListenerList listeners;
	
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Integer> delays;
	
	protected int spawnCounter;
	protected int currentEnemy;
	
	protected int level;
	protected boolean active;
	
	public static final int DEFAULT_WAVE_DELAY = 30;         // in ticks
	
	public EmptyWave(int depth) {
		super(null, depth);
		
		listeners = new EventListenerList();
		
		enemies = new ArrayList<Enemy>();
		delays = new ArrayList<Integer>();
		
		spawnCounter = 0;
		currentEnemy = 0;
		
		level = 0;
		active = false;
	}
	
	@Override
	public void update() {
		super.update();
		active = true;
		if (currentEnemy < enemies.size()) {
			spawnCounter++;
			if (spawnCounter >= delays.get(currentEnemy)) {
				raiseNewEnemyEvent();
			}
		} else {
			raiseWaveDoneEvent();
		}
	}
	
	@Override
	public int size() {
		return enemies.size();
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseNewEnemyEvent() {
		WaveEvent event = new WaveEvent(this,0);
		event.setEnemy(enemies.get(currentEnemy));
		currentEnemy++;
		spawnCounter = 0;
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == WaveListener.class) {
				((WaveListener)lList[i+1]).newEnemyEvent(event);
			}
		}
	}
	
	private void raiseWaveDoneEvent() {
		WaveEvent event = new WaveEvent(this,0);
		Object[] lList = listeners.getListenerList();
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == WaveListener.class) {
				((WaveListener)lList[i+1]).waveDoneEvent(event);
			}
		}
	}
	//////////////////////////////////////////////////////////////
	//                END OF RAISE EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//               START OF CATCH EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public void addWaveListener(WaveListener listener) {
		listeners.add(WaveListener.class, listener);
	}
	
	@Override
	public void removeWaveListener(WaveListener listener) {
		listeners.remove(WaveListener.class, listener);
	}
	
	@Override
	public void addEnemy(Enemy e, int delayFromPreviousEnemyInGameTicks) {
		enemies.add(e);
		delays.add(new Integer(delayFromPreviousEnemyInGameTicks));
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public void setLevel(int level) {
		if (!active) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).setLevel(level);
			}
			this.level = level;
		}
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
