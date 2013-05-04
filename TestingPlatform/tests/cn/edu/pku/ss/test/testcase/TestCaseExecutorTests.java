package cn.edu.pku.ss.test.testcase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor.MonitorType;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;


public class TestCaseExecutorTests  extends junit.framework.TestCase{
   
	private TestCaseExecutor get(){
		
		TestCaseExecutor t = new TestCaseExecutor(
				IOUtil.getBasePath() + "\\Testing\\",
				"TestObject",
				"add",
				"");		
		return t;
		
	}
	
	private MethodParam getParam(){
		MethodParam mp = new MethodParam();
		mp.add(new Integer(10));
		mp.add(new Integer(3));
		return mp;
	}
	

    public void testLoad(){
    	System.out.println("load in testLoad");
		TestCaseExecutor t = get();
		assertEquals(true, t.loadClass());
    }
	
    public void testExecute(){
    	System.out.println("load in testExecute");
		TestCaseExecutor t = get();
		t.loadClass();
		ExecuteResult r = t.execute(getParam());
		assertEquals(true, r.isSuccess());
		assertEquals(new Integer(13),r.getReturn());
    }
	
    public void testMonitorField(){
    	System.out.println("load in testMonitorField");
		TestCaseExecutor t = get();
		t.setMonitorName("result");
		t.setMonitorType(MonitorType.Field);
		t.loadClass();
		ExecuteResult r = t.execute(getParam());
		assertEquals(true, r.isSuccess());
		assertEquals(new Integer(7), r.getMonitor());
    }
	
	
    public void testMonitorMethod(){ 
    	System.out.println("load in testMonitorMethod");
    	TestCaseExecutor t = get();
		t.setMonitorName("getResult");
		t.setMonitorType(MonitorType.Method);
		t.loadClass();
		ExecuteResult r = t.execute(getParam());
		assertEquals(true, r.isSuccess());
		//Debug.println(r.getMonitor());
		assertEquals(new Integer(7), r.getMonitor());
    	
    }
	
    public void testMonitorSelf(){
    	System.out.println("load in testMonitorSelf");
		TestCaseExecutor t = get();
		t.setMonitorType(MonitorType.Object);
		t.loadClass();
		ExecuteResult r = t.execute(getParam());
		assertEquals(true, r.isSuccess());
		assertEquals(true, r.getMonitor().equals(new Integer(7)));
    	
    }
    

    public void testMonitorNull(){
    	System.out.println("load in testMonitorNull");
		TestCaseExecutor t = get();
		t.setMonitorType(MonitorType.Null);
		t.loadClass();
		ExecuteResult r = t.execute(getParam());
		assertEquals(true, r.isSuccess());
		assertEquals(null, r.getMonitor());
    	
    }
	
}
