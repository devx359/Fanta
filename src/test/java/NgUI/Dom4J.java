package NgUI;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Dom4J {
	Document document;

	public void openXML(String filePath) {
		if (filePath != null) {

			try {

				SAXReader reader = new SAXReader();
				File file = new File(filePath);
				document = reader.read(file);
				System.out.println("inside dom4j with " + filePath);
				/*
				 * List<Node> elements =
				 * document.selectNodes("//PROPERTY/TOWN"); for (Node n :
				 * elements) { System.out.println(n.getText() + ": " +
				 * n.getUniquePath()); }
				 */

			} catch (Exception e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}
		} else {
			System.out.println("No File chosen");
		}
	}

	public void getText(String xpath) {
		List<Node> elements = document.selectNodes(xpath);
		for (Node n : elements)
		{
			System.out.println(n.getUniquePath());
			String text=document.selectSingleNode(n.getUniquePath()+"/@name").getText();
			System.out.println(text);
		}
	}
}
