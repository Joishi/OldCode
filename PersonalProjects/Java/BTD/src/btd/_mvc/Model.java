package btd._mvc;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import btd.paintable.Paintable;
import btd.paintable.bullets.Bullet;
import btd.paintable.bullets.BulletEvent;
import btd.paintable.bullets.BulletListener;
import btd.paintable.enemies.Enemy;
import btd.paintable.enemies.EnemyEvent;
import btd.paintable.enemies.EnemyListener;
import btd.paintable.paths.Path;
import btd.paintable.towers.Tower;
import btd.paintable.towers.TowerEvent;
import btd.paintable.towers.TowerListener;
import btd.paintable.waves.Wave;
import btd.paintable.waves.WaveEvent;
import btd.paintable.waves.WaveListener;
import btd.prebuilt.BTDGame;

public class Model implements BTDModel, BulletListener, TowerListener, EnemyListener, WaveListener {

	private EventListenerList listeners;
	
	private int lives;
	private long score;
	private long money;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Tower> towers;
	private ArrayList<Tower> readyTowers;
	private ArrayList<Bullet> bullets;
	private ArrayList<Wave> activeWaves;
	private ArrayList<Wave> futureWaves;
	private Path path;
	
	private Paintable selectedObject;
	
	private boolean paused;
	private boolean gameOver;
	private boolean gameOverReported;
	
	private boolean enemiesRepeatPath;
	
	public Model() {
		listeners = new EventListenerList();
		
		lives = 0;
		score = 0;
		money = 0;
		
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		readyTowers = new ArrayList<Tower>();
		bullets = new ArrayList<Bullet>();
		activeWaves = new ArrayList<Wave>();
		futureWaves = new ArrayList<Wave>();
		path = null;
		
		paused = true;
		gameOver = true;
		gameOverReported = true;
		
		enemiesRepeatPath = true;
	}
	
	@Override
	public void update() {
		if (!paused && !gameOver) {
			updatePath();
			updateWaves();
			updateBullets();
			updateEnemies();
			updateTowers();
			towersFire();
			if (testWin() || testLoss()) {
				gameOver = true;
				gameOverReported = false;
			}
		} else if (gameOver && !gameOverReported) {
			raiseGameOverEvent();
			gameOverReported = true;
		}
	}
	
	private void updatePath() {
		if (path != null) {
			path.update();
		}
	}
	
	private void updateWaves() {
		for (int i = 0; i < activeWaves.size(); i++) {
			activeWaves.get(i).update();
		}
	}
	
	private void updateBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
		}
	}
	
	private void updateEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
	}
	
	private void updateTowers() {
		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).update();
		}
		for (int i = 0; i < readyTowers.size(); i++) {
			readyTowers.get(i).update();
		}
	}
	
	private void towersFire() {
		if (enemies.size() > 0) {
			Enemy[] enemyArray = enemies.toArray(new Enemy[0]);
			for (int i = 0; i < readyTowers.size(); i++) {
				if (readyTowers.get(i).selectTarget(enemyArray)) {
					addBullet(readyTowers.get(i).fire());
					towers.add(readyTowers.get(i));
					readyTowers.remove(i);
				}
			}
		}
	}
	
	private boolean testWin() {
		if (futureWaves.size() == 0 && enemies.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean testLoss() {
		if (lives <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setupGame(BTDGame game) {
		paused = true;
		gameOver = true;
		gameOverReported = true;
		reset();
		raiseNewGameEvent();
		addPath(game.getPath());
		Tower[] tA = game.getTowers();
		money = Long.MAX_VALUE;
		for (int i = 0; i < tA.length; i++) {
			addTower(tA[i], tA[i].getLocation());
		}
		Wave[] wA = game.getWaves();
		for (int i = 0; i < wA.length; i++) {
			addWave(wA[i]);
		}
		lives = 0;
		updateLives(game.getLives());
		money = 0;
		updateMoney(game.getMoney());
		
		paused = false;
		gameOver = false;
		gameOverReported = false;
		clearSelectedObject();
	}
	
	private void reset() {
		removeBullets();
		removeTowers();
		removeEnemies();
		removeWaves();
		removePath();
	}
	
	private void removeBullets() {
		bullets.clear();
	}
	
	private void removeBullet(Bullet bullet) {
		bullet.removeBulletListener(this);
		raiseRemoveBulletEvent(bullet);
		bullets.remove(bullet);
	}
	
	private void removeTowers() {
		towers.clear();
		readyTowers.clear();
	}
	
	private void removeTower(Tower tower) {
		tower.removeTowerListener(this);
		raiseRemoveTowerEvent(tower);
		towers.remove(tower);
		readyTowers.remove(tower);
	}
	
	private void removeEnemies() {
		enemies.clear();
	}
	
	private void removeEnemy(Enemy enemy) {
		enemy.removeEnemyListener(this);
		raiseRemoveEnemyEvent(enemy);
		enemies.remove(enemy);
	}
	
	private void removeWaves() {
		activeWaves.clear();
		futureWaves.clear();
	}
	
	private void removeWave(Wave wave) {
		wave.removeWaveListener(this);
		raiseRemoveWaveEvent(wave);
		activeWaves.remove(wave);
		futureWaves.remove(wave);
	}
	
	private void removePath() {
		raiseRemovePathEvent();
		path = null;
	}
	
	private void addPath(Path path) {
		raiseAddPathEvent(path);
		this.path = path;
	}
	
	@Override
	public boolean addTower(Tower tower, Point2D location) {
		if (path != null && !gameOver) {
			boolean locationOpen = true;
			tower.setLocation(location);
			Rectangle2D tRect = tower.getBounds();
			if (path.intersects(tRect)) {
				locationOpen = false;
			}
			for (int i = 0; i < towers.size(); i++) {
				if (towers.get(i).getBounds().intersects(tRect)) {
					locationOpen = false;
				}
			}
			for (int i = 0; i < readyTowers.size(); i++) {
				if (readyTowers.get(i).getBounds().intersects(tRect)) {
					locationOpen = false;
				}
			}
			if (locationOpen && money >= tower.getCurrentValue()) {
				tower.addTowerListener(this);
				raiseAddTowerEvent(tower);
				towers.add(tower);
				updateMoney(-tower.getCurrentValue());
				return true;
			}
		}
		return false;
	}
	
	private void addWave(Wave wave) {
		wave.addWaveListener(this);
		raiseAddWaveEvent(wave);
		futureWaves.add(wave);
	}
	
	private void addBullet(Bullet bullet) {
		bullet.addBulletListener(this);
		raiseAddBulletEvent(bullet);
		bullets.add(bullet);
	}
	
	private void updateMoney(long money) {
		this.money += money;
		raiseUpdateMoneyEvent();
	}
	
	private void updateScore(long score) {
		this.score += score;
		raiseUpdateScoreEvent();
	}
	
	private void updateLives(int lives) {
		this.lives += lives;
		raiseUpdateLivesEvent();
	}
	
	private void addEnemy(Enemy enemy) {
		enemy.addEnemyListener(this);
		enemy.setPath(path);
		raiseAddEnemyEvent(enemy);
		enemies.add(enemy);
	}
	
	@Override
	public void sendWave() {
		if (futureWaves.size() > 0) {
			activeWaves.add(futureWaves.get(0));
			futureWaves.remove(0);
		}
	}
	
	@Override
	public void mouseClickAt(Point2D location) {
		boolean objectSelected = false;
		for (int i = 0; i < towers.size(); i++) {
			if (!objectSelected && towers.get(i).getBounds().contains(location)) {
				selectedObject = towers.get(i);
				objectSelected = !objectSelected;
			}
		}
		for (int i = 0; i < readyTowers.size(); i++) {
			if (!objectSelected && readyTowers.get(i).getBounds().contains(location)) {
				selectedObject = readyTowers.get(i);
				objectSelected = !objectSelected;
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			if (!objectSelected && enemies.get(i).getBounds().contains(location)) {
				selectedObject = enemies.get(i);
				objectSelected = !objectSelected;
			}
		}
		if (!objectSelected) {
			selectedObject = null;
		}
		raiseObjectSelectedEvent();
	}
	
	@Override
	public void pause() {
		paused = !paused;
	}
	
	@Override
	public Paintable getSelectedObject() {
		return selectedObject;
	}
	
	@Override
	public void clearSelectedObject() {
		selectedObject = null;
		raiseObjectSelectedEvent();
	}
	
	@Override
	public void sellTower() {
		if (selectedObject instanceof Tower) {
			Tower tower = (Tower) selectedObject;
			updateMoney(tower.getSellValue());
			removeTower(tower);
			selectedObject = null;
			raiseObjectSelectedEvent();
		}
	}
	
	@Override
	public void upgradeTower() {
		if (selectedObject instanceof Tower) {
			Tower tower = (Tower) selectedObject;
			if (tower.canUpgrade() && money >= tower.getUpgradeValue()) {
				updateMoney(-tower.getUpgradeValue());
				tower.upgrade();
			}
		}
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseGameOverEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		if (testWin()) {
			event.setWin(true);
		} else {
			event.setWin(false);
		}
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).gameOverEvent(event);
			}
		}
	}
	
	private void raiseRemoveBulletEvent(Bullet bullet) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setBullet(bullet);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).removeBulletEvent(event);
			}
		}
	}
	
	private void raiseRemoveTowerEvent(Tower tower) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setTower(tower);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).removeTowerEvent(event);
			}
		}
	}
	
	private void raiseRemoveEnemyEvent(Enemy enemy) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setEnemy(enemy);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).removeEnemyEvent(event);
			}
		}
	}
	
	private void raiseRemoveWaveEvent(Wave wave) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setWave(wave);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).removeWaveEvent(event);
			}
		}
	}
	
	private void raiseRemovePathEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).removePathEvent(event);
			}
		}
	}
	
	private void raiseAddPathEvent(Path path) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setPath(path);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).addPathEvent(event);
			}
		}
	}
	
	private void raiseAddTowerEvent(Tower tower) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setTower(tower);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).addTowerEvent(event);
			}
		}
	}
	
	private void raiseAddWaveEvent(Wave wave) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setWave(wave);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).addWaveEvent(event);
			}
		}
	}
	
	private void raiseAddBulletEvent(Bullet bullet) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setBullet(bullet);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).addBulletEvent(event);
			}
		}
	}
	
	private void raiseUpdateMoneyEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).updateMoneyEvent(event);
			}
		}
	}
	
	private void raiseUpdateScoreEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).updateScoreEvent(event);
			}
		}
	}
	
	private void raiseUpdateLivesEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).updateLivesEvent(event);
			}
		}
	}
	
	private void raiseAddEnemyEvent(Enemy enemy) {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		event.setEnemy(enemy);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).addEnemyEvent(event);
			}
		}
	}
	
	private void raiseNewGameEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).newGameEvent(event);
			}
		}
	}
	
	private void raiseObjectSelectedEvent() {
		Object[] lList = listeners.getListenerList();
		BTDModelEvent event = new BTDModelEvent(this,0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDModelListener.class) {
				((BTDModelListener)lList[i+1]).objectSelectedEvent(event);
			}
		}
	}
	//////////////////////////////////////////////////////////////
	//                END OF RAISE EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//               START OF CATCH EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	@Override
	public void detonationEvent(BulletEvent event) {
		Bullet bullet = event.getBullet();
		Point2D bLoc = bullet.getLocation();
		Enemy target = bullet.getTarget();
		if (target != null && target.getLocation().distance(bLoc) <= BLAST_RADIUS) {
			target.damage(bullet.getDamage());
		}
		if (bullet.isSplash()) {
			double rad = bullet.getSplashRadius();
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).getLocation().distance(bLoc) <= rad) {
					enemies.get(i).damage(bullet.getDamage());
				}
			}
		}
	}
	
	@Override
	public void retargetEvent(BulletEvent event) {
		Bullet bullet = event.getBullet();
		if (enemies.size() > 0) {
			Point2D bLoc = bullet.getLocation();
			Enemy closestEnemy = enemies.get(0);
			Point2D eLoc = closestEnemy.getLocation();
			for (int i = 1; i < enemies.size(); i++) {
				if (bLoc.distance(enemies.get(i).getLocation()) < bLoc.distance(eLoc)) {
					closestEnemy = enemies.get(i);
					eLoc = closestEnemy.getLocation();
				}
			}
			bullet.setTarget(closestEnemy);
		} else {
			bullet.setTarget(null);
		}
	}
	
	@Override
	public void spentEvent(BulletEvent event) {
		removeBullet(event.getBullet());
	}
	
	@Override
	public void readyToFireEvent(TowerEvent event) {
		Tower tower = event.getTower();
		readyTowers.add(tower);
		towers.remove(tower);
	}
	
	@Override
	public void deathEvent(EnemyEvent event) {
		Enemy enemy = event.getEnemy();
		if (selectedObject instanceof Enemy) {
			if (enemies.indexOf(selectedObject) == enemies.indexOf(enemy)) {
				selectedObject = null;
				raiseObjectSelectedEvent();
			}
		}
		removeEnemy(enemy);
		updateMoney(enemy.getMoneyCarried());
		updateScore(enemy.getPointsWorth());
	}
	
	@Override
	public void pathCompleteEvent(EnemyEvent event) {
		updateLives(-1);
		if (enemiesRepeatPath) {
			Enemy enemy = event.getEnemy();
			enemy.setPath(path);
		}
	}
	
	@Override
	public void teleportEvent(EnemyEvent event) { }
	
	@Override
	public void newEnemyEvent(WaveEvent event) {
		addEnemy(event.getEnemy());
	}
	
	@Override
	public void waveDoneEvent(WaveEvent event) {
		Wave wave = event.getWave();
		removeWave(wave);
	}
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public void addModelListener(BTDModelListener listener) {
		listeners.add(BTDModelListener.class, listener);
	}
	
	@Override
	public void removeModelListener(BTDModelListener listener) {
		listeners.remove(BTDModelListener.class, listener);
	}
	
	@Override
	public int getLives() {
		return lives;
	}
	
	@Override
	public void setLives(int lives) { }
	
	@Override
	public long getMoney() {
		return money;
	}
	
	@Override
	public void setMoney(long money) { }
	
	@Override
	public long getScore() {
		return score;
	}
	
	@Override
	public void setScore(long score) { }
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
