package cn.edu.pku.ss.test.jobs.compilers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Test;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;

public class CppCompilerTests extends junit.framework.TestCase {
	
	 public void testCompileCPP(){
		 String path = IOUtil.getBasePath() + "Testing\\";
		 
		 File f = new File(path + "Test.dll");
		 if(f.exists()){
			 f.delete();
		 }		 
		 
		 Log.println(new File(path + "Test.cpp").exists());
		 
		 boolean actual = CppCompiler.compileDll(f + " " + path + "Test.cpp");		 
		 assertEquals(true, actual);		  
		 assertEquals(true, f.exists());
		 
	 }

}
