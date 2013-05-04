package cn.edu.pku.ss.test.java;

import org.junit.Test;

import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.bp.BPVisitor;
import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class BPVisitorTests {
	
	
	
	@Test
	public void test(){
		TestData data = new TestData();
		data.setSourcePath(Setting.Base + "BPVisitorTest.java");
		data.put(JobConst.TARGET_CLASS, "BPVisitorTest");
		data.put(JobConst.TARGET_METHOD, "testMethod");
		
		JavaParser p = new JavaParser();
		BPVisitor v = new BPVisitor();
		
		p.run(data);
		v.run(data);
		
		Log.println(data.getSourceCode());
		Log.println(data.getStr(JobConst.TARGET_SOURCE));
		Log.println(GraphNode.HEADER);
		Log.println(v.getGraph());
		Log.println(v.getGraph().generatPaths());
	}
}
