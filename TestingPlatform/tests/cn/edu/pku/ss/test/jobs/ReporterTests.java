package cn.edu.pku.ss.test.jobs;

import junit.framework.Assert;

import org.jdom.Element;
import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class ReporterTests {

	@Test
	public void testXML(){
		Element root = new Element("Root"); 
		Element sub  = new Element("Sub");
		sub.addContent("Success");
		root.addContent(sub);
		
		String actual = Reporter.generateXML(root);
		Log.println(actual);
		int expected = 81;
			//"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Root>\r\n  <Sub>Success</Sub>\r\n</Root>\r\n";
		//Debug.println(expected);
		Assert.assertEquals(expected, actual.length());
		
	
	}
}
