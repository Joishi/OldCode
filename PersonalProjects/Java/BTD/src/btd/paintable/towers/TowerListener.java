package btd.paintable.towers;

import java.util.EventListener;

public interface TowerListener extends EventListener {

	void readyToFireEvent(TowerEvent event);

}
