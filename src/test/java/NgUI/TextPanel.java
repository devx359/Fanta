package NgUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

 /**
 * <pre>TextPanel is a custom component extending on JPanel which extends  Component</pre>
 * @author debapriyo
 * @since May 2019
 *
 */
public class TextPanel extends JPanel {

	public JTextArea txtarea;

	public TextPanel() {

		txtarea = new JTextArea();

		setLayout(new BorderLayout());
		//txtarea.setBackground(Color.CYAN);
		txtarea.setRows(5);
		
		add(new JScrollPane(txtarea), BorderLayout.CENTER);
		
	}

	/**
	 * <pre>
	 * Appends Text in text area
	 * </pre>
	 * @author debapriyo
	 * @param text string to add in text area
	 */
	public void apppend(String text)
	{
		txtarea.append(text+"\n");
	}
}
