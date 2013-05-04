package cn.edu.pku.ss.test.java;

import java.io.File;
import junit.framework.Assert;

import org.junit.Test;

import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.cfg.graph.GraphPathArray;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BasisPathVisitor;
import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.Log;

public class BPConditionVisitorTests {
	
	@Test
	public void test(){
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test1");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test2");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test3");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test4");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test5");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test6");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test7");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test8");
		//run(Setting.Base + "New\\" ,"IFTest.java", "IFTest", "test9");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test1");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test2");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test3");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test4");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test5");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test6");
		//run(Setting.Base + "New\\" ,"ForTest.java", "ForTest", "test7");
		//run(Setting.Base + "New\\" ,"WhileTest.java", "WhileTest", "test1");
		//run(Setting.Base + "New\\" ,"WhileTest.java", "WhileTest", "test2");
		//run(Setting.Base + "New\\" ,"WhileTest.java", "WhileTest", "test3");
		//run(Setting.Base + "New\\" ,"DoTest.java", "DoTest", "test1");
		//run(Setting.Base + "New\\" ,"MultipleTest.java", "MultipleTest", "test1");
		run(Setting.Base + "New\\" ,"MultipleTest.java", "MultipleTest", "test2");
	}
	

	public void run(String path , String file, String className, String methodName){
		String actual = testRun( path+ file, className, methodName).trim();
		String expected = "";
		
		File exFile = new File(path+ className + "_" + methodName + ".txt");
		if(exFile.exists()){
			expected = IOUtil.read(exFile.toString()).trim();
			Assert.assertEquals(expected, actual);
			Log.Debug("method " + methodName + " passed!");
		} else {			
			//IOUtil.write(exFile, actual);
			Log.Debug(actual);
			Assert.fail();
		}
	}
	
	public String testRun(String file, String className, String methodName){
		TestData data = new TestData();
		data.setSourcePath(file);
		data.put(JobConst.TARGET_CLASS, className);
		data.put(JobConst.TARGET_METHOD, methodName);
		
		JavaParser p = new JavaParser();
		BasisPathVisitor v = new BasisPathVisitor();
		
		p.run(data);
		v.run(data);
		Log.EnableLog =false;
		//Log.println(data.getSourceCode());
		Log.println(data.getStr(JobConst.TARGET_SOURCE));
		Log.println(GraphNode.HEADER);
		Log.println(v.getGraph());
		GraphPathArray gpa= v.getGraph().generatPaths();		
		Log.Debug(gpa);
		Log.Debug(gpa.size());
		return v.getGraph().toString();
		//Debug.println(v.getGraph().generatPaths());
	}

}
