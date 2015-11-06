package btd._mvc;

import btd.paintable.towers.Tower;
import btd.prebuilt.BTDGame;

public class Controller implements BTDViewListener {

	private Model model;
	private View view;
	
	private boolean towerListSelected;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.addViewListener(this);
		
		towerListSelected = false;
	}
	
	public void update() {
		
	}
	//////////////////////////////////////////////////////////////
	//               START OF RAISE EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//                END OF RAISE EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	//               START OF CATCH EVENT METHODS               //
	//////////////////////////////////////////////////////////////
	public void cancelEvent(BTDViewEvent event) {
		// TODO Auto-generated method stub
		towerListSelected = false;
		model.clearSelectedObject();
		view.cancelTowerListSelection();
	}
	
	public void fieldClickedEvent(BTDViewEvent event) {
		if (towerListSelected) {
			// clicked on field with tower selected
			Tower tower = view.getSelectedTower();
			if (!model.addTower(tower, event.getLocation())) {
				towerListSelected = false;
				view.cancelTowerListSelection();
			}
		}
		model.mouseClickAt(event.getLocation());
	}
	
	public void newGameEvent(BTDViewEvent event) {
		towerListSelected = false;
		view.cancelTowerListSelection();
		BTDGame game = event.getGame();
		model.setupGame(game);
	}
	
	public void pauseEvent(BTDViewEvent event) {
		model.pause();
	}
	
	public void sellTowerEvent(BTDViewEvent event) {
		model.sellTower();
	}
	
	public void sendWaveEvent(BTDViewEvent event) {
		model.sendWave();
	}
	
	public void towerSelectedEvent(BTDViewEvent event) {
		if (view.getSelectedTower() != null) {
			towerListSelected = true;
		} else {
			towerListSelected = false;
		}
	}
	
	public void upgradeTowerEvent(BTDViewEvent event) {
		model.upgradeTower();
	}
	//////////////////////////////////////////////////////////////
	//                END OF CATCH EVENT METHODS                //
	//////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                START OF ACCESSOR/MUTATOR                //
	/////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////
	//                 END OF ACCESSOR/MUTATOR                 //
	/////////////////////////////////////////////////////////////

}
