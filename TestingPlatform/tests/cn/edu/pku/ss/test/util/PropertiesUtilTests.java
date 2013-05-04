package cn.edu.pku.ss.test.util;

import junit.framework.Assert;

import org.junit.Test;

public class PropertiesUtilTests {
	
	@Test
	public void testClassPath() {		
		String value = PropertiesUtil.getInstance().readValue(PropertiesUtil.CLASS_PATH);
		Log.println(value);
		Assert.assertNotNull(value);
		System.out.println("OK");
	}
	
	@Test
	public void testUploadPath() {		
		String value = PropertiesUtil.getInstance().readValue(PropertiesUtil.CLASS_PATH);
		Log.println(value);
		Assert.assertNotNull(value);
		System.out.println("OK");
	}
}
