package cn.edu.pku.ss.test.java;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class ClassLoadTests {

	@Test
	public void Test() {
		// ╪сть.class
		File file = new File("C:\\Users\\SHOOTS~1\\AppData\\Local\\Temp\\shootsoft_testing_1358676862\\bin\\");
		URL[] urls;
		try {
			
			urls = new URL[] { file.toURI().toURL() };
			Log.println("[" + file + "]");
			ClassLoader testCaseLoader = new URLClassLoader(urls);
			Class testClass = testCaseLoader.loadClass("DCTest");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
