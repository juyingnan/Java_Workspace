package cn.edu.pku.ss.test.java.code;

import java.io.File;

import org.junit.Test;


import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.Setting;
import cn.edu.pku.ss.test.java.bp.BPVisitor;
import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class TryTests {
	
	@Test
	public void test(){
		TestData data = new TestData();
		data.setSourcePath(Setting.Base + "TryTest.java");
		data.put(JobConst.TARGET_CLASS, "TryTest");
		data.put(JobConst.TARGET_METHOD, "test");
		
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
	
	public boolean delete(Object arg){		
		try{
			boolean b = true;
			File f = new File(arg.toString());
			f.delete();
			return b;
		} catch(SecurityException ex){
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;			
		}		
	}
}
