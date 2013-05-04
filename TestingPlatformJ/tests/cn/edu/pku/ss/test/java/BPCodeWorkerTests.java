package cn.edu.pku.ss.test.java;

import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPStumpWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class BPCodeWorkerTests {

	@Test
	public void test(){
		
		BPStumpWorker w = new BPStumpWorker();
		TestData d = new TestData();
		d.setSourcePath(Setting.Base + "BPTest.java");
		d.put(JobConst.TARGET_CLASS, "BPTest");
		d.put(JobConst.TARGET_METHOD, "test");
		boolean b = w.run(d);
		String s = d.getStr(JobConst.TARGET_SOURCE);
		Log.println(s);
		Assert.assertEquals(true, b);
		Assert.assertEquals(true, s.length()>0);
				
	}
	
}
