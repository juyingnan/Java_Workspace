package cn.edu.pku.ss.test.java.service;


import java.util.Properties;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionStumpWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaConditionStumpTesting implements ITesting {

	/**
	 * 条件测试，以下三个参数是必的 <br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 
	 * */
	@Override
	public String testing(String code, String options) {
		
		ConditionStumpWorker w = new ConditionStumpWorker();
		TestData d = new TestData();
		Properties pro = PropertiesUtil.parse(options);
				
		String fileName = pro.getProperty(JobConst.FILENAME);
		d.setSourceCode(code, fileName);
		d.put(JobConst.TARGET_CLASS, pro.getProperty(JobConst.TARGET_CLASS));
		d.put(JobConst.TARGET_METHOD,pro.getProperty(JobConst.TARGET_METHOD));
		
		boolean b = w.run(d);
		if(b){			
			return d.getStr(JobConst.TARGET_SOURCE);
		} else {
			return d.getResult().getReport();
		}
		
	}


}
