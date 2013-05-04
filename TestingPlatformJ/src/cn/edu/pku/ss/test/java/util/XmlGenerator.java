/**
 * 
 */
package cn.edu.pku.ss.test.java.util;

import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author taco
 * 
 */
public class XmlGenerator {

	public static String testCaseToXml(String className, String methodName,
			ArrayList[] solutionSet, Object[] retVals, String[] paths) throws Exception {

		Element root = new Element("UnitTest");

		Element classElement = new Element("Class");
		classElement.setAttribute("name", className);
		root.addContent(classElement);

		for (int i = 0; i < solutionSet.length; i++) {
			ArrayList solution = solutionSet[i];
			Element caseElement = new Element("TestCase");
			caseElement.setAttribute("method", methodName);

			for (Object param : solution) {
				Element paramElement = new Element("Parameter");
				paramElement.setAttribute("type", param.getClass().getName());
				paramElement.setAttribute("value", param.toString());
				caseElement.addContent(paramElement);
			}

			if (retVals != null) {
				Element retElement = new Element("ReturnValue");
				Object retVal = retVals[i];
				if (retVal == null) {
					retElement.setAttribute("type", "void");
					retElement.setAttribute("value", "");
				} else {
					retElement.setAttribute("type", retVal.getClass().getName());
					retElement.setAttribute("value", retVal.toString());
				}
				caseElement.addContent(retElement);
			} 
			
			if (paths != null) {
				Element pathElement = new Element("Path");
				String path = paths[i];
				if (path != null) {
					pathElement.setAttribute("path", path);
				} else {
					pathElement.setAttribute("path", "");
				}
				caseElement.addContent(pathElement);
			}

			root.addContent(caseElement);
		}

		Document doc = new Document(root);
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());

		return xmlOut.outputString(doc);
	}
}
