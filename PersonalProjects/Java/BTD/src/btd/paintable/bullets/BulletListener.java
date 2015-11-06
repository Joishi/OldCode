package btd.paintable.bullets;

import java.util.EventListener;

public interface BulletListener extends EventListener {

	void detonationEvent(BulletEvent event);
	void retargetEvent(BulletEvent event);
	void spentEvent(BulletEvent event);

}
