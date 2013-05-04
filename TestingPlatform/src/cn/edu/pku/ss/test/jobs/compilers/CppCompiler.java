package cn.edu.pku.ss.test.jobs.compilers;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.util.Log;

public class CppCompiler extends Compiler {

	public final static String CPP_COMPILER_EXE = "g++"; 
	public final static String CPP_COMPILER_WARN = "warn.txt";
	
	public static boolean compileDll(String args){
		return compile(" -shared -Wl,--kill-at -o " + args);
	}
	
	public static boolean compile(String args){
		Runtime rn = Runtime.getRuntime();
		Process p = null;
		try {
			String command = CPP_COMPILER_EXE;			
			p = rn.exec(command + args);
			//FileOutputStream s = (ByteArrayOutputStream) p.getOutputStream();
			
			//String 
			//p.destroy();
			Log.println(command + args);
			Thread.sleep(5000);
			// TODO: ÍêÉÆµ÷ÓÃ
		} catch (Exception e) {
			System.out.println("Error int "+ CPP_COMPILER_EXE);
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public boolean compile(TestData data) {
		
		return false;
	}

}
