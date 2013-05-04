package cn.edu.pku.ss.test.java.service;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.jobs.ASTWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.services.ITesting;

public class JavaASTTesting implements ITesting {
	
	/**
	 *  获取 Java的AST树，只需要Code参数
	 * */
	@Override
	public String testing(String code, String options) {
		
		TestData d = new TestData();
		d.setSourceCode(code, "Testing.java");
		ASTWorker w  = new ASTWorker();
		boolean b = w.run(d);
		if(b){
//			System.out.println("Entered into JavaASTTesting...");
			System.out.println("AST TREE : " + d.getStr(JobConst.ASTVIEW));
			return d.getStr(JobConst.ASTVIEW);
		} else {
//			System.out.println("GET REPORT");
			return d.getResult().getReport();
		}
	}
	
	
}
