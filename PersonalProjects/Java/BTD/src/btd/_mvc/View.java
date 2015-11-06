package btd._mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import btd.paintable.Paintable;
import btd.paintable.bullets.Bullet;
import btd.paintable.enemies.Enemy;
import btd.paintable.paths.Path;
import btd.paintable.towers.LaserTower;
import btd.paintable.towers.PelletTower;
import btd.paintable.towers.RocketTower;
import btd.paintable.towers.Tower;
import btd.prebuilt.BTDGame;
import btd.prebuilt.TestGame;

@SuppressWarnings("serial")
public class View extends JPanel implements BTDView, BTDModelListener, MouseListener, MouseMotionListener, KeyListener, ActionListener {

	private EventListenerList listeners;
	private BTDModel model;
	
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private Path path;
	
	private int lives;
	private long money;
	private long score;
	
	private Image topBackground;
	private Image rightBackground;
	private Image fieldBackground;
	
	private Color backgroundColor; // used if Images are null
	
	private Color gameStatsColor;
	private ArrayList<String> statusMessages;
	private Color statusMessageColor;
	private int numMessagesToDisplay;
	private Color gameControlEnabledColor;
	private Color gameControlDisabledColor;
	private Color towerListNColor;
	private Color towerListXColor;
	private Color selectedTowerZoneColor;
	
	private Tower[] towerList;
	private int selectedTower;
	
	private Paintable selectedObject;
	private Color selectedObjectColor;
	private boolean drawSelectedObjectBounds;
	
	private boolean updateTop;
	private boolean updateRight;
	private boolean updateField;
	
	public View(BTDModel model) {
		setMinimumSize(frame);
		setMaximumSize(frame);
		setPreferredSize(frame);
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
		setLayout(new BorderLayout());
		add(makeMenuBar(), BorderLayout.NORTH);
		
		listeners = new EventListenerList();
		this.model = model;
		this.model.addModelListener(this);
		
		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		path = null;
		
		lives = 0;
		money = 0;
		score = 0;
		
		try {
			topBackground = ImageIO.read(new File("./pics/topBackground.jpg"));
			rightBackground = ImageIO.read(new File("./pics/rightBackground.jpg"));
			fieldBackground = ImageIO.read(new File("./pics/fieldBackground.jpg"));
		} catch(IOException e) {
			topBackground = null;
			rightBackground = null;
			fieldBackground = null;
		}
		
		backgroundColor = Color.WHITE;
		gameStatsColor = Color.BLACK;
		statusMessages = new ArrayList<String>();
		statusMessageColor = Color.RED;
		numMessagesToDisplay = 9;
		gameControlEnabledColor = Color.BLUE;
		gameControlDisabledColor = Color.GRAY;
		towerListNColor = Color.BLACK;
		towerListXColor = Color.RED;
		selectedTowerZoneColor = Color.GREEN;
		
		towerList = new Tower[towerLoc.length];
		selectedTower = -1;
		
		towerList[0] = new RocketTower();
		towerList[1] = new PelletTower();
		towerList[2] = new LaserTower();
		
		selectedObject = null;
		selectedObjectColor = Color.GREEN;
		drawSelectedObjectBounds = true;
		
		updateTop = true;
		updateRight = true;
		updateField = true;
	}
	
	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("One");
		menuItem = new JMenuItem(GAME1);
		menuItem.addActionListener(this);
		menuItem.setActionCommand(GAME1);
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		return menuBar;
	}
	
	@Override
	public void update() {
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
//		super.paint(g);  // only useful for drawing the JMenuBar .....
		Graphics2D g2 = (Graphics2D) g;
		
		if (updateTop) {
			updateTop(g2);
			updateTop = false;
		}
		
		if (updateRight) {
			updateRight(g2);
			updateRight = false;
		}
		
		if (updateField) {
			updateField(g2);
//			updateField = false;
		}
		paintComponents(g2);
		
		g2.dispose();
	}
	
	private void updateTop(Graphics2D g2) {
		int fontSize;
		if (topBackground != null) {
			g2.drawImage(topBackground, 0, 0, null);
		} else {
			g2.setColor(backgroundColor);
			g2.fill(gameStatsZone);
		}
		
		// paint messages
		fontSize = g2.getFont().getSize();
		g2.setColor(statusMessageColor);
		int level = numMessagesToDisplay-1;
		for (int i = statusMessages.size()-1; i >= statusMessages.size() - numMessagesToDisplay; i--) {
			if (i >= 0) {
				g2.drawString((i+1) + ": " + statusMessages.get(i), (int)x[0], (int)y[1]+fontSize*(numMessagesToDisplay-level--));
			}
		}
		
		// paint game stats
		fontSize = g2.getFont().getSize();
		g2.setColor(gameStatsColor);
		String stats = "Lives: " + lives + "    Money: " + money + "    Score: " + score;
		g2.drawString(stats, (int)x[0], (int)y[3]);
		
		// paint game controls
		AffineTransform baseTransform = g2.getTransform();
		AffineTransform verticalPauseTransform = new AffineTransform();
		verticalPauseTransform.translate(x[3]+fontSize, y[3]);
		verticalPauseTransform.rotate(-Math.PI/2);
		
		g2.setColor(gameControlEnabledColor);
		g2.drawString("Send next wave", (int)x[1], (int)y[2]+fontSize);
		g2.setTransform(verticalPauseTransform);
		g2.drawString("Pause", 0, 0);
		g2.setTransform(baseTransform);
	}
	
	private void updateRight(Graphics2D g2) {
		int fontSize;
		if (rightBackground != null) {
			g2.drawImage(rightBackground, 0, 0, null);
		} else {
			g2.setColor(backgroundColor);
			g2.fill(towerListZone);
			g2.fill(selectedZone);
		}
		
		// paint tower list
		for (int i = 0; i < towerList.length; i++) {
			if (towerList[i] != null) {
				towerList[i].paintAt(g2, towerLoc[i]);
			}
		}
		// TODO replace X and N with 1 selection box that alternates between X and N for the situation (or delete/new)
		fontSize = g2.getFont().getSize();
		g2.setColor(towerListNColor);
		for (int i = 0; i < towerN.length; i++) {
			g2.drawString("N", (int)towerN[i].getMinX(), (int)towerN[i].getMaxY());
		}
		fontSize = g2.getFont().getSize();
		g2.setColor(towerListXColor);
		for (int i = 0; i < towerX.length; i++) {
			g2.drawString("X", (int)towerX[i].getMinX(), (int)towerX[i].getMaxY());
		}
		
		// paint selected tower
		if (selectedTower >= 0 && towerList[selectedTower] != null) {
			g2.setColor(selectedTowerZoneColor);
			g2.draw(towerZone[selectedTower]);
		}
		
		// paint selected object
		if (selectedObject != null) {
			selectedObject.paintAt(g2, selectedTowerLoc);
			if (drawSelectedObjectBounds) {
				g2.setColor(selectedObjectColor);
				g2.draw(selectedObject.getBounds());
			}
			if (selectedObject instanceof Tower) {
				g2.setColor(gameControlEnabledColor);
			} else if (selectedObject instanceof Enemy) {
				g2.setColor(gameControlDisabledColor);
			}
		} else {
			g2.setColor(gameControlDisabledColor);
		}
		
		// paint selected object controls
		AffineTransform baseTransform = g2.getTransform();
		AffineTransform verticalSellTransform = new AffineTransform();
		verticalSellTransform.translate(x[3]+fontSize, y[24]);
		verticalSellTransform.rotate(-Math.PI/2);
		
		g2.drawString("Upgrade", (int)x[1], (int)y[23]+fontSize);
		g2.setTransform(verticalSellTransform);
		g2.drawString("Sell", 0, 0);
		g2.setTransform(baseTransform);
	}
	
	private void updateField(Graphics2D g2) {
		if (fieldBackground != null) {
			g2.drawImage(fieldBackground, 0, 0, null);
		} else {
			g2.setColor(backgroundColor);
			g2.fill(gameFieldZone);
		}
		
		// paint path/towers/enemies/bullets
		if (path != null) {
			path.paint(g2);
			for (int i = 0; i < towers.size(); i++) {
				towers.get(i).paint(g2);
			}
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).paint(g2);
			}
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).paint(g2);
			}
		}
	}
	
	private void eraseTowerList(int index) {
		// TODO needs some fixing
		if (towerList[index] != null) {
			raisePauseEvent();
			JOptionPane confirmPane = new JOptionPane();
			confirmPane.setSize(100, 75);
			confirmPane.setVisible(true);
			
//			JInternalFrame confirmDialogue = new JInternalFrame();
//			confirmDialogue.setSize(100, 75);
//			add(confirmDialogue);
//			int confirm = JOptionPane.showInternalConfirmDialog(confirmDialogue, "Do you really want to remove tower " + (selectedTower+1) + "?", "", JOptionPane.YES_NO_OPTION);
//			if (confirm == JOptionPane.YES_OPTION) {
//				towerList[index] = null;
//				raiseTowerSelectedEvent();
//			} else {
//				selectedTower = -1;
//				raiseTowerSelectedEvent();
//			}
//			confirmDialogue.dispose();
//			remove(confirmDialogue);
			raisePauseEvent();
		}
		updateTop = true;
		updateRight = true;
		updateField = true;
	}
	
	private void addTowerList(int index) {
		// TODO Auto-generated method stub
		// when a user wants to create their own tower
		selectedTower = index;
		raiseTowerSelectedEvent();
		
		updateTop = true;
		updateRight = true;
		updateField = true;
	}
	
	private void addMessage(String message) {
		if (message != null) {
			statusMessages.add(message);
		}
		
		updateTop = true;
	}
	
	// TODO add sell tower and update tower methods...
	
	@Override
	public Tower getSelectedTower() {
		if (selectedTower >= 0 && selectedTower < towerList.length) {
			if (towerList[selectedTower] != null) {
				return towerList[selectedTower].makeBlankCopy();
			}
		}
		return null;
	}
	
	@Override
	public void cancelTowerListSelection() {
		selectedTower = -1;
		raiseTowerSelectedEvent();
		
		updateRight = true;
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	private void raiseTowerSelectedEvent() {
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).towerSelectedEvent(event);
			}
		}
	}
	
	private void raiseCancelEvent() {
		selectedTower = -1;
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).cancelEvent(event);
			}
		}
	}
	
	private void raiseNewGameEvent(BTDGame game) {
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		event.setGame(game);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).newGameEvent(event);
			}
		}
	}
	
	private void raiseSellTowerEvent() {
		selectedTower = -1;
		raiseTowerSelectedEvent();
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).sellTowerEvent(event);
			}
		}
	}
	
	private void raiseUpgradeTowerEvent() {
		selectedTower = -1;
		raiseTowerSelectedEvent();
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).upgradeTowerEvent(event);
			}
		}
	}
	
	private void raiseFieldClickedEvent(Point2D mouseLoc) {
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		event.setLocation(mouseLoc);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).fieldClickedEvent(event);
			}
		}
	}
	
	private void raiseSendWaveEvent() {
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).sendWaveEvent(event);
			}
		}
	}
	
	private void raisePauseEvent() {
		Object[] lList = listeners.getListenerList();
		BTDViewEvent event = new BTDViewEvent(this, 0);
		for (int i = lList.length - 2; i >= 0; i -= 2) {
			if (lList[i] == BTDViewListener.class) {
				((BTDViewListener)lList[i+1]).pauseEvent(event);
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
	public void addBulletEvent(BTDModelEvent event) {
		bullets.add(event.getBullet());
	}
	
	@Override
	public void addEnemyEvent(BTDModelEvent event) {
		enemies.add(event.getEnemy());
	}
	
	@Override
	public void addPathEvent(BTDModelEvent event) {
		path = event.getPath();
	}
	
	@Override
	public void addTowerEvent(BTDModelEvent event) {
		towers.add(event.getTower());
	}
	
	@Override
	public void addWaveEvent(BTDModelEvent event) { }
	
	@Override
	public void gameOverEvent(BTDModelEvent event) {
		// TODO Auto-generated method stub
		// make a pop-up internal frame to say game is over..
	}
	
	@Override
	public void removeBulletEvent(BTDModelEvent event) {
		bullets.remove(event.getBullet());
	}
	
	@Override
	public void removeEnemyEvent(BTDModelEvent event) {
		enemies.remove(event.getEnemy());
	}
	
	@Override
	public void removePathEvent(BTDModelEvent event) {
		path = null;
	}
	
	@Override
	public void removeTowerEvent(BTDModelEvent event) {
		towers.remove(event.getTower());
	}
	
	@Override
	public void removeWaveEvent(BTDModelEvent event) { }
	
	@Override
	public void updateLivesEvent(BTDModelEvent event) {
		lives = model.getLives();
		
		updateTop = true;
	}
	
	@Override
	public void updateMoneyEvent(BTDModelEvent event) {
		money = model.getMoney();
		
		updateTop = true;
	}
	
	@Override
	public void updateScoreEvent(BTDModelEvent event) {
		score = model.getScore();
		
		updateTop = true;
	}
	
	public void newGameEvent(BTDModelEvent event) {
		towers.clear();
		enemies.clear();
		bullets.clear();
		path = null;
		
		updateTop = true;
		updateRight = true;
		updateField = true;
	}
	
	@Override
	public void objectSelectedEvent(BTDModelEvent event) {
		selectedObject = model.getSelectedObject();
		
		updateRight = true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) { }
	
	@Override
	public void mouseEntered(MouseEvent e) { }
	
	@Override
	public void mouseExited(MouseEvent e) { }
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Point2D mouseLoc = e.getPoint();
			if (towerListZone.contains(mouseLoc)) {
				updateRight = true;
				for (int i = 0; i < towerZone.length; i++) {
					if (towerZone[i].contains(mouseLoc)) {
						if (towerX[i].contains(mouseLoc)) {
							eraseTowerList(i);
						} else if (towerN[i].contains(mouseLoc)) {
							if (towerList[i] == null) {
								addTowerList(i);
							} else {
								addMessage("You can't create a new tower in a used slot");
							}
						} else {
							selectedTower = i;
							raiseTowerSelectedEvent();
						}
					}
				}
			} else if (selectedZone.contains(mouseLoc)) {
				updateRight = true;
				if (sellTowerZone.contains(mouseLoc)) {
					raiseSellTowerEvent();
				} else if (upgradeTowerZone.contains(mouseLoc)) {
					raiseUpgradeTowerEvent();
				} else {
					
				}
			} else if (gameFieldZone.contains(mouseLoc)) {
				raiseFieldClickedEvent(mouseLoc);
			} else if (gameStatsZone.contains(mouseLoc)) {
				updateTop = true;
				if (sendZone.contains(mouseLoc)) {
					raiseSendWaveEvent();
				} else if (pauseZone.contains(mouseLoc)) {
					raisePauseEvent();
				} else {
					
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			// middle mouse button
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			// right mouse button
		} else {
			// what happened?
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { }
	
	@Override
	public void mouseDragged(MouseEvent e) { }
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ESCAPE) {
			raiseCancelEvent();
		} else if (keyCode == KeyEvent.VK_1) {
			selectedTower = 0;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_2) {
			selectedTower = 1;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_3) {
			selectedTower = 2;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_4) {
			selectedTower = 3;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_5) {
			selectedTower = 4;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_6) {
			selectedTower = 5;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_7) {
			selectedTower = 6;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_8) {
			selectedTower = 7;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_9) {
			selectedTower = 8;
			raiseTowerSelectedEvent();
		} else if (keyCode == KeyEvent.VK_U) {
			raiseUpgradeTowerEvent();
		} else if (keyCode == KeyEvent.VK_S) {
			raiseSellTowerEvent();
		}
		updateRight = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(GAME1)) {
			raiseNewGameEvent(new TestGame());
		}
	}
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	@Override
	public void addViewListener(BTDViewListener listener) {
		listeners.add(BTDViewListener.class, listener);
	}
	
	@Override
	public void removeViewListener(BTDViewListener listener) {
		listeners.remove(BTDViewListener.class, listener);
	}
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
