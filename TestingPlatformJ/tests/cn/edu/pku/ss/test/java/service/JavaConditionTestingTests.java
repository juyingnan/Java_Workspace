package cn.edu.pku.ss.test.java.service;

import junit.framework.Assert;
import junit.framework.TestCase;

import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaConditionTestingTests extends TestCase{

	public void testDC() {
		
		JavaConditionTesting t = new JavaConditionTesting();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		options+="\r\nConditionType=DecisionCoverage";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}
	
	public void testCC() {
		
		JavaConditionTesting t = new JavaConditionTesting();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		options+="\r\nConditionType=ConditionCoverage";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}

	public void testMC() {
		
		JavaConditionTesting t = new JavaConditionTesting();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		options+="\r\nConditionType=MutipleCoverage";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}

	public void testAll() {
	
		JavaConditionTesting t = new JavaConditionTesting();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		options+="\r\nConditionType=AllCoverage";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}

}
