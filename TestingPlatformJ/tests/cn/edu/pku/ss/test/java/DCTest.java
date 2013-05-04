package cn.edu.pku.ss.test.java;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionWorker;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class DCTest {
	
	@Test
	public void testDC(){
		
		TestData data = new TestData();
		data.setSourcePath(Setting.Base+ Setting.CONDITION_FILE + ".java");
		//data.setSourcePath("C:\\Users\\SHOOTS~1\\AppData\\Local\\Temp\\shootsoft_testing_879771614\\ConditionTest.java");
		data.put(JobConst.TARGET_CLASS, Setting.CONDITION_FILE);
		data.put(JobConst.TARGET_METHOD, "test3");
		
		ConditionWorker worker = new ConditionWorker();
		worker.run(data);
		
		Log.println(data.getResult().getReport());
		Log.println(data.get(JobConst.COMPILED_REPORT));
		
		Log.println("Before:");		
		Log.println(data.get(JobConst.AST));
		Log.println("After:");
		Log.println(data.get(JobConst.TARGET_SOURCE));
		Log.println("Target path :" + data.get(JobConst.RESULT_TARGET_PATH));
		Log.println("Excuted path :" + data.get(JobConst.RESULT_ACTUAL_PATH));
		Log.println("Test case :" +data.get(JobConst.RESULT_TESTCASE));
		Double d = (Double)data.get(JobConst.RESULT_CONVERAGE);
		d = d * 100;
		Log.println("Coverage: " + d + "%");
		Log.println(data.get(JobConst.RESULT_CONVERAGE_PATH));
		
		Log.println(data.get("XML"));
	}
}
