package cn.edu.pku.ss.test.util;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlUtil{
	
	public static String generateXML(org.jdom.Element root ){
		Document doc = new Document(root);
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());
		return xmlOut.outputString(doc);
		
	}
}
