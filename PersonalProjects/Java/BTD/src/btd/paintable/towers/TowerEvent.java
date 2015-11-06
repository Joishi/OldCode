package btd.paintable.towers;

import java.awt.AWTEvent;

@SuppressWarnings("serial")
public class TowerEvent extends AWTEvent {

	public TowerEvent(Tower source, int id) {
		super(source, id);
	}
	
	public Tower getTower() {
		return (Tower) source;
	}

}
