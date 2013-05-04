package cn.edu.pku.ss.test.java.service;

import java.util.Properties;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPStumpWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.util.PropertiesUtil;

public class JavaBPStumpTesting implements ITesting {

	/**
	 * BP���ԣ��������������Ǳص� <br>
	 * FileName=[TestClass.java]<br>
	 * TargetClass=[TestClass]<br>
	 * TargetMethod=[testMethod]<br>
	 * 
	 * */
	@Override
	public String testing(String code, String options) {
		
		BPStumpWorker w = new BPStumpWorker();
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
