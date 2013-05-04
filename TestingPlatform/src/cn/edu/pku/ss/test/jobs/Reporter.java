package cn.edu.pku.ss.test.jobs;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.edu.pku.ss.test.data.TestData;

public abstract class Reporter implements IJob{

	@Override
	public boolean run(TestData data) {
		
		Element root = new Element("UnitTest");		
		data.put(JobConst.XMLROOT, root);
		
		generateHeader(data);
		calculateCoverage(data);
		generateXmlReport(data);
		
		return true;
	}

	public abstract void generateHeader(TestData data);
	public abstract void calculateCoverage(TestData data);
	public abstract void generateXmlReport(TestData data);

	public static String generateXML(Element root){
		Document doc = new Document(root);
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat());
		return xmlOut.outputString(doc).replace("&#xD;", "");
	}
}
