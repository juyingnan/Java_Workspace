package cn.edu.pku.ss.test.jobs.compilers;

import java.io.File;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class CompilerTests extends junit.framework.TestCase {
	
	public class TestCompiler extends Compiler{
		
		@Override
		public boolean compile(TestData data) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	
	 public void testPath(){
		 TestCompiler c = new TestCompiler();
		 
		 File bin = new File(c.getBin());
		 File src = new File(c.getSrc());
		 //Debug.println(bin.getParent());
		 //Debug.println(src.getParent());
		 assertEquals(true, bin.getParent().equals(src.getParent()));
		 
		 
	 }
}
