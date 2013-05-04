package cn.edu.pku.ss.test.java.service;


import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaConditionStumpTestingTests{

	@Test
	public void test() {
		
		JavaConditionStumpTesting t = new JavaConditionStumpTesting();
		String code = IOUtil.read(Setting.Base + "ConditionTest.java");
		String options = "FileName=ConditionTest.java";
		options+="\r\nTargetClass=ConditionTest";
		options+="\r\nTargetMethod=test";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}


}
