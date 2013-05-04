package cn.edu.pku.ss.test.cpp.condition;

import org.jdom.Element;

import cn.edu.pku.ss.test.condition.ConditionReporter;
import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;

public class CppConditionReporter extends ConditionReporter{

	@Override
	public void generateHeader(TestData data) {
		Element root = (Element)data.get(JobConst.XMLROOT);
		
		Element classElement = new Element("Function");
		classElement.setAttribute("name", data.getStr(Const.TARGET_FUNCTION_NAME));
		root.addContent(classElement);
		
		data.put(JobConst.XMLROOT, root);
		
	}

}
