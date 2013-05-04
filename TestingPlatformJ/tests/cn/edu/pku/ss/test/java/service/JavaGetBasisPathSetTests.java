package cn.edu.pku.ss.test.java.service;


import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.services.ITesting;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaGetBasisPathSetTests{

	//@Test
	//public void test() {
	    public static void main(String args[]) { 
		
//	    JavaBPTesting t = new JavaBPTesting();
//	    JavaGetBasisPathSet t = new JavaGetBasisPathSet();
	    JavaGetFlowGraph t = new JavaGetFlowGraph();
		String code = IOUtil.read(Setting.Base + "BPTest.java");
		String options = "FileName=BPTest.java";
		options+="\r\nTargetClass=BPTest";
		options+="\r\nTargetMethod=test";
		
		String r = t.testing(code, options);
		System.out.println("The result is: \n" + r);
		//Log.println(r);
		//Assert.assertNotNull(r);
		//Assert.assertEquals(true, r.length()>0);
	}

}
