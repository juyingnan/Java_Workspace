package cn.edu.pku.ss.test.testcase;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * A customize class loader, try to load class by itself firstly.
 * If failed, will try to use its parent.
 * @author Zhang Le
 * 
 */
public class CustomLoader extends URLClassLoader {
	public CustomLoader(URL[] urls) {
		super(urls);
		for(int i=0; i< urls.length; i++){
			System.out.println("CustomLoader: " + urls[i].toString());
		}
		
	}
	
	public CustomLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class c = findLoadedClass(name);
		try {
			c = findClass(name);
		} catch (ClassNotFoundException e) {
			c = getParent().loadClass(name);
		}
		return c;
	}
}
