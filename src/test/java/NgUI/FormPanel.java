package NgUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	
	private JLabel suitelbl;
	private JLabel testlbl;
	private JLabel classNamelbl;
	private JTextField suiteTxt;
	private JTextField testTxt;
	private JTextField classTxt;
	private JButton save;
	private FormListener formlistener;
	private JList mylist;
	
	public FormPanel() {
		//Dimension dim = getPreferredSize();
		//dim.width=200;
		setPreferredSize(new Dimension(500,0)); //height value is not respected
		
		//set Border
		Border innerBorder= BorderFactory.createTitledBorder("My Forms");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
		
		suitelbl = new JLabel("Suite");
		testlbl = new JLabel("Test");
		classNamelbl = new JLabel("Class");
		
		suiteTxt= new JTextField("Suite Name",15);
		testTxt= new JTextField("Test Case Name here",15);
		classTxt= new JTextField("Class Name here",15);
		
		mylist = new JList();
		DefaultListModel lstmodel = new DefaultListModel();
		lstmodel.addElement("apple");
		lstmodel.addElement("orange");
		lstmodel.addElement("pear");
		mylist.setModel(lstmodel);
		mylist.setPreferredSize(new Dimension(160,66));
		mylist.setBorder(BorderFactory.createEtchedBorder());
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String suitename = suiteTxt.getText();
				String testname= testTxt.getText();
				String classname= classTxt.getText();
				
				FormEvent ev = new FormEvent(this,suitename,classname,testname);
				
				if(formlistener!=null)
				{
					formlistener.formEventOccured(ev);
				}
				
			}
		});
		
		
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx=0;
		gc.gridy=0;
		Insets label = new Insets(0,0,0,5);
		
		//row 1
		gc.weightx=1;
		gc.weighty=0.1;
		gc.insets=label;
		gc.fill=GridBagConstraints.NONE;
		gc.anchor=GridBagConstraints.LINE_END;
		
		add(suitelbl,gc);
		
		gc.gridx=1;
		gc.gridy=0;
		gc.anchor=GridBagConstraints.LINE_START;
		add(suiteTxt,gc);
		
		//row 2
		gc.gridx=0;
		gc.gridy=1;
		gc.anchor=GridBagConstraints.LINE_END;
		add(testlbl,gc);
		gc.gridx=1;
		gc.gridy=1;
		gc.anchor=GridBagConstraints.LINE_START;
		add(testTxt,gc);
		
		//row 3
		gc.gridx=0;
		gc.gridy=2;
		gc.anchor=GridBagConstraints.LINE_END;
		add(classNamelbl,gc);
		gc.gridx=1;
		gc.gridy=2;
		gc.anchor=GridBagConstraints.LINE_START;
		add(classTxt,gc);
		
		//row4
		gc.gridx=1;
		gc.gridy=3;
		//gc.weighty=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		add(mylist,gc);
		
		//row 5
		gc.gridx=1;
		gc.gridy=4;
		gc.weighty=3;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		add(save,gc);
		
		
	}

	public void setFormListener(FormListener formListener) {
		// TODO Auto-generated method stub
		this.formlistener=formListener;
		
	}
}
