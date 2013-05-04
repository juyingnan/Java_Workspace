package cn.edu.pku.ss.test.services;

import org.junit.Test;

import cn.edu.pku.ss.test.cpp.service.*;
import cn.edu.pku.ss.test.java.service.*;
import cn.edu.pku.ss.test.util.IOUtil;

public class TestingImpTests {
	
	@Test
	public void testing() {	
		TestingImp imp = new TestingImp();
		System.out.println(imp.testing("", ""));
	}
	
	@Test
	public void testingImpJavaBP(){
		TestingImp imp = new TestingImp();
		String code=IOUtil.read(IOUtil.getBasePath("\\TestingFiles\\BPTest.java"));
		String options = "FileName=BPTest.java\r\n"
			+ "TargetClass=BPTest\r\n"
			+ "TargetMethod=test";
		System.out.println(imp.testingImp(7,code, options));
	}
	
	@Test
	public void testingImpCppBP(){
		TestingImp imp = new TestingImp();
		String code=IOUtil.read(IOUtil.getBasePath("\\TestingFiles\\BPTest.cpp"));
		String options = "TargetFunctionName=test";
		System.out.println(imp.testingImp(2,code, options));
	}


}
