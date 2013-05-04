package cn.edu.pku.ss.test.java.service;


import org.junit.Test;

import junit.framework.Assert;
import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaMethodTestingTests {
	

	@Test
	public void test() {
		JavaMethodTesting t = new JavaMethodTesting();
		String code = IOUtil.read(Setting.Base + "InnerClassTest.java");
		String r = t.testing(code, "");
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}
	
	
}
