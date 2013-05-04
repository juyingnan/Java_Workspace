package cn.edu.pku.ss.test.jobs.compilers;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class JavaCompilerTests extends junit.framework.TestCase {
	

	 public void testCompile(){
		 String path = IOUtil.getBasePath() + "\\Testing\\";
		 String src = path + "TestObject.java";
		 
		 File f = new File(path + "TestObject.class");
		 if(f.exists()){
			 f.delete();
		 }
		 
		 boolean actual = JavaCompiler.compile("", path, src, "compiletest.txt");
		 assertEquals(true, actual);
		 
	 }

	
}
