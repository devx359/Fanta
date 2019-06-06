package NgUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class ToolBar extends JPanel implements ActionListener {

	private JButton savebtn;
	private JButton browseBtn;
	private TextPanel textpanel;
	private StringListener stringlistener;
	
	public ToolBar()
	{
		setBorder(BorderFactory.createEtchedBorder());
		savebtn= new JButton("Save");
		browseBtn= new JButton("Import XML");
		
		//since we are extending ActionListner ,now ToolBar is also a Actionlistener class , so we can pass this in addActionListener
		savebtn.addActionListener(this);
		browseBtn.addActionListener(this);
		
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//add components
		add(savebtn);
		add(browseBtn);
	}
	
	public void setStringListener(StringListener stringlistener)
	{
		this.stringlistener= stringlistener;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//getsource returns the object on which it was clicked
		if(e.getSource()==savebtn) {
			if(stringlistener!=null)
			{
				stringlistener.stringEmitter("Hello \n");
			}
		}
		
		if(e.getSource()==browseBtn) {
			if(stringlistener!=null)
			{
				
				BrowseFileUtil browse = new BrowseFileUtil();
				Dom4J dom4j = new Dom4J();
				
				//Browse files
				String filename=browse.OpenFile();				
				stringlistener.stringEmitter(filename);
				
				//Start Reading xml
				dom4j.openXML(filename);
				dom4j.getText("//suite/test");
				
				//Render Treel List
				//TreeListPanel tpnl = new TreeListPanel();
				//tpnl.createTree();
				
				
				
			}
		}
		
	}
	
	}
