package cn.edu.pku.ss.test.java.service;


import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaBPStumpTestingTests{

	@Test
	public void test() {
		
		JavaBPStumpTesting t = new JavaBPStumpTesting();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}


}
