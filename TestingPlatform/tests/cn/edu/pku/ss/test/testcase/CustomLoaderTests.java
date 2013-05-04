package cn.edu.pku.ss.test.testcase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import cn.edu.pku.ss.test.util.IOUtil;


public class CustomLoaderTests extends junit.framework.TestCase {
	
	public void testCustomLoader(){
		String path = IOUtil.getBasePath() + "Testing\\";
		File f = new File(path);
		try {
			CustomLoader l = new CustomLoader(new URL[]{f.toURI().toURL()});
			Class c = l.loadClass("TestObject");
			assertNotNull(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNotNull(null);		
		}
	}
}
