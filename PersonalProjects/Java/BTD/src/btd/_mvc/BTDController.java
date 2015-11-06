package btd._mvc;

public interface BTDController extends BTDViewListener {

	// this has a direct link to the model
	// this has a direct link to the view
	// this receives events from the view
	
	void update();

}
