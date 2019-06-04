package NgUI;

import java.io.File;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class BrowseFileUtil {

	/***
	 * Returns File name after browsing .
	 */
	public String OpenFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		FileFilter filter = new FileNameExtensionFilter(".xml","xml");		
		jfc.setFileFilter(filter);
		jfc.setAcceptAllFileFilterUsed(false); // remove the accept-all (.*) file filter
		
		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			return selectedFile.getAbsolutePath();
		}
		if (returnValue == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return null;
	}

	public String getExtension(File f) {

		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
