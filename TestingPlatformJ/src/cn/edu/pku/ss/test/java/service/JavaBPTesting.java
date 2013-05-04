package cn.edu.pku.ss.test.java.service;


import java.util.Properties;

import org.jdom.Element;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaBPTesting implements ITesting {

	/**
	 * BP测试，以下三个参数是必的 :<br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 以下参数是可选的:<br>
	 * 
	 * */
	@Override
	public String testing(String code, String options) {
		
		BPWorker w = new BPWorker();
		TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);
				
		String fileName = pro.getProperty(JobConst.FILENAME);
		d.setSourceCode(code, fileName);
		d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		d.put(JobConst.TARGET_METHOD,pro.getProperty(JobConst.TARGET_METHOD));
		d.put(JobConst.MONITOR_TYPE, MonitorType.Field);
		boolean b = w.run(d);
		if(b){
			Element s = (Element) d.get(JobConst.XMLROOT);			
			return Reporter.generateXML(s);
		} else {
			return d.getResult().getReport();
		}
		
	}
}
