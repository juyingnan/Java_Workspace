package cn.edu.pku.ss.test.java.service;


import java.util.Properties;

import org.jdom.Element;

import cn.edu.pku.ss.test.condition.ConditionType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaConditionTesting implements ITesting {

	/**
	 * 条件测试，以下三个参数是必的 <br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 以下条件是可选的<br>
	 * ConditionType=[DecisionCoverage|ConditionCoverage|MutipleCoverage|AllCoverage]<br>
	 * */
	@Override
	public String testing(String code, String options) {
		
		ConditionWorker w = new ConditionWorker();
		TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);
				
		String fileName = pro.getProperty(JobConst.FILENAME);
		d.setSourceCode(code, fileName);
		d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		d.put(JobConst.TARGET_METHOD,pro.getProperty(JobConst.TARGET_METHOD));
		
		String type = pro.getProperty(JobConst.CONDITION_TYPE);		
		ConditionType condition =  ConditionType.DecisionCoverage;
		if(type!=null){
			try{
				condition =  Enum.valueOf(ConditionType.class, type);
			}catch(Exception ex){
				Log.println(ex);
			}			
		}
//		System.out.println("____________________________________________");
//		System.out.println("put data : condition type " + condition);
//		System.out.println("____________________________________________");
		
		d.put(JobConst.CONDITION_TYPE, condition);
		
		
		
		boolean b = w.run(d);
		if(b){			
			Element s = (Element) d.get(JobConst.XMLROOT);			
			return Reporter.generateXML(s);
		} else {
			return d.getResult().getReport();
		}
		
	}


}
