package cn.edu.pku.ss.test.java.service;

import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.Log;

public class JavaFlowGraphTestingTests {

	@Test
	public void test() {
		
		JavaGetFlowGraph t = new JavaGetFlowGraph();
//		String code = IOUtil.read(Setting.Base + "BPTest.java");
//		Log.println("The path is: " + Setting.Base + "BPTest.java");
//		String options = "FileName=BPTest.java";
//		options+="\r\nTargetClass=BPTest";
//		options+="\r\nTargetMethod=test0";
		String code = IOUtil.read(Setting.Base + "LoopTest.java");
		Log.println("The path is: " + Setting.Base + "LoopTest.java");
		String options = "FileName=LoopTest.java";
		options+="\r\nTargetClass=LoopTest";
		options+="\r\nTargetMethod=forLoop";
		
		//options+="\r\nTargetMethod=fun1";
		
		String r = t.testing(code, options);
		Log.println(r);
		Assert.assertNotNull(r);
		Assert.assertEquals(true, r.length()>0);
	}
}
