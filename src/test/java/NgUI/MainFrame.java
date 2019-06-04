package NgUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	public TextPanel txtpanel;
	private ToolBar toolbar;
	private FormPanel formpanel;
	private TreeListPanel treelistpanel;

	public MainFrame() {
		super("TestNg UI :) by Debapriyo"); // calls JFrames constructor

		setLayout(new BorderLayout());// set layout

		// creating objects for my custom components
		txtpanel = new TextPanel();
		toolbar = new ToolBar();
		formpanel = new FormPanel();
		treelistpanel = new TreeListPanel();
		
		toolbar.setStringListener(new StringListener() {

			@Override
			public void stringEmitter(String text) {
				txtpanel.apppend(text);

			}

		});
		
		formpanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent e) {
				String testname= e.getTestname();
				String classname= e.getClassname();
				String suitename= e.getSuite();
				
				txtpanel.apppend(testname);
				txtpanel.apppend(classname);
				txtpanel.apppend(suitename);
				
				
			}
		});

		// add to frame
		add(formpanel,BorderLayout.EAST);
		add(toolbar, BorderLayout.NORTH);
		
		add(treelistpanel, BorderLayout.CENTER);
		add(txtpanel, BorderLayout.SOUTH);

		// set frame dimensions
		setSize(900, 550);

		// what happens when we click on close button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set frame visible along with all the components
		setVisible(true);
	}
}
