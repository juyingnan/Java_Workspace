package cn.edu.pku.ss.test.java.bp;

import org.jdom.Element;

import cn.edu.pku.ss.test.bp.BPReporter;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;

public class JavaBPReporter extends BPReporter{

	@Override
	public void generateHeader(TestData data) {
		Element root = (Element)data.get(JobConst.XMLROOT);
		
		Element classElement = new Element("Class");		
		classElement.setAttribute("name", data.getStr(JobConst.TARGET_CLASS));
		classElement.setAttribute("method", data.getStr(JobConst.TARGET_METHOD));
		root.addContent(classElement);
		
		data.put(JobConst.XMLROOT, root);
		
	}

}
