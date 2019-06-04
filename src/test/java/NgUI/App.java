package NgUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() { // to prevent any threading issues run the code inside this

			@Override
			public void run() {
				new MainFrame();

			}
		});

	}

}
