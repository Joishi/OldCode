package btd._mvc;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public final class GameTester extends JFrame implements Runnable {

	private Model model;
	private View view;
	private Controller controller;
	private Thread t;
	
	public GameTester() {
		model = new Model();
		view = new View(model);
		controller = new Controller(model, view);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Game tester");
		add(view);
		pack();
		setResizable(false);
		setVisible(true);
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		while (true) {
			try {
				model.update();
				view.update();
				controller.update();
				Thread.sleep(33);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		new GameTester();
	}

}
