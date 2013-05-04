package cn.edu.pku.ss.test.java;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.jobs.ASTWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class ASTWorkerTests {

	@Test
	public void test(){
		
		TestData d = new TestData();
		d.setSourcePath(Setting.Base + "BPVisitorTest.java");
		
		ASTWorker w  = new ASTWorker();
		w.run(d);
		
		Log.println(d.getStr(JobConst.ASTVIEW));
	}
}
