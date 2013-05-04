package cn.edu.pku.ss.test.java;

import junit.framework.Assert;

import org.jdom.Element;
import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.Reporter;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.util.Log;

public class BPWorkerTests {
	
	@Test
	public void test(){
		
		BPWorker w = new BPWorker();
		TestData d = new TestData();
		d.setSourcePath(Setting.Base + "TestExample2.java");
		d.addAttachedFile(Setting.Base + "Student.java", true);
//		d.addAttachedFile(Setting.Base + "Address.java", true);
		d.put(JobConst.TARGET_CLASS, "TestExample2");
		d.put(JobConst.TARGET_METHOD, "fun");
//		d.setSourcePath(Setting.Base + "BPTest.java");
//		d.put(JobConst.TARGET_CLASS, "BPTest");
//		d.put(JobConst.TARGET_METHOD, "test");
		d.put(JobConst.MONITOR_TYPE, MonitorType.Field);
		System.out.println("zhaodaola:"+d.getStr(JobConst.TARGET_PATH_BIN));
		boolean b = w.run(d);
		Log.println(d.getResult().getReport());
		
		Assert.assertEquals(true, b);
		
		Element s = (Element) d.get(JobConst.XMLROOT);
		Assert.assertNotNull(s);
		
		Log.println(Reporter.generateXML(s));
		
	
				
	}
}
