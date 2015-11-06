package btd.paintable.bullets;

import java.awt.AWTEvent;

@SuppressWarnings("serial")
public class BulletEvent extends AWTEvent {

	public BulletEvent(Bullet source, int id) {
		super(source, id);
	}
	
	public Bullet getBullet() {
		return (Bullet) source;
	}

}
