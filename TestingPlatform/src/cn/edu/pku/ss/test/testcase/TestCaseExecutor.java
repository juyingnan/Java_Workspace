package cn.edu.pku.ss.test.testcase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;

import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.util.Log;


public class TestCaseExecutor {
	private String testClassName;
	@SuppressWarnings("unchecked")
	private Class testClass;
	private String testMethodName;
	private Method testMethod;
	private String testPath;
	private String monitorName;
	private MonitorType monitorType;
	private boolean isLoaded;
	private ExecuteResult result;
	private Object data;
	
	public TestCaseExecutor() {
		monitorType = MonitorType.Field;
	}

	public TestCaseExecutor(String testPath, String testClassName,
		String testMethodName, String monitorName) {
		setTestPath(testPath);
		setTestClassName(testClassName);
		setTestMethodName(testMethodName);
		setMonitorName(monitorName);
		//Debug.println(monitorName);
		monitorType = MonitorType.Field;
	}

	public boolean loadClass(){
		isLoaded = false;		
		try {
			
			//加载.class
			File file = new File(testPath);
			URL[] urls = new URL[]{file.toURI().toURL()};
			ClassLoader testCaseLoader = new CustomLoader(urls,Thread.currentThread().getContextClassLoader());
			
			try{
				testClass = testCaseLoader.loadClass(testClassName);
				//testClass.newInstance();
			} catch (Exception  e){
				e.printStackTrace();

			}
	
			//加载方法
			Method[] methods = testClass.getMethods();
			for (Method method : methods) {
				String str = method.getName();
				if (str.equals(testMethodName)){
					testMethod = method;
					isLoaded = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isLoaded;
	}

	public boolean isLoaded(){
		return isLoaded;
	}
	
	@SuppressWarnings("unchecked")
	public ExecuteResult execute(MethodParam params){
		
		if(!isLoaded)loadClass();
		
		result = new ExecuteResult();		
		
		try {
			Object obj = testClass.newInstance();
			Method method = getTestMethod();
			result.setReturn(method.invoke(obj, params.toArray()));
			result.setSuccess(true);
			
			//Debug.println("--start--");
			if(monitorType == MonitorType.Null){
				return result;
			}
			else if (monitorType == MonitorType.Field){
				
				if(monitorName!=null && monitorName.length()>0){
					Field[] fs = testClass.getFields();
					for(int i =0 ; i< fs.length; i++){
						
						if(fs[i].getName().equals(monitorName)){
							//Debug.println(fs[i].getName() + "****" +monitorName);
							result.setMonitor(fs[i].get(obj));	
							break;
						}
					}
				}
			} else if (monitorType == MonitorType.Method){
				if(monitorName!=null && monitorName.length()>0){
					Method[] ms = testClass.getMethods();
						for(int i =0 ; i< ms.length; i++){
						if(ms[i].getName() == monitorName){
							result.setMonitor(ms[i].invoke(obj));	
							break;
						}
					}
				}
			} else {
				result.setMonitor(obj);	
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			result.setSuccess(false);
			result.setException(ex);

		}
		
		return result;
	}
	
	public String getTestClassName() {
		return testClassName;
	}
	public void setTestClassName(String testClassName) {
		this.testClassName = testClassName;
	}
	@SuppressWarnings("unchecked")
	public Class getTestClass() {
		return testClass;
	}

	public String getTestMethodName() {
		return testMethodName;
	}
	public void setTestMethodName(String testMethodName) {
		this.testMethodName = testMethodName;
	}
	
	public Method getTestMethod() {
		return testMethod;
	}

	public String getTestPath() {
		return testPath;
	}
	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
	
	public ArrayList<Type> getMethodParaTypes() {
		Type[] paraTypes = getTestMethod().getParameterTypes();
		ArrayList<Type> paraTypesList = new ArrayList<Type>();

		Collections.addAll(paraTypesList, paraTypes);

		return paraTypesList;
	}
	
	public Type getReturnType(){
		return getTestMethod().getGenericReturnType();		
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public ExecuteResult getExecutedResult() {
		return result;
	}
	
	
	public void setMonitorType(MonitorType monitorType) {
		this.monitorType = monitorType;
	}

	public MonitorType getMonitorType() {
		return monitorType;
	}


	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}


	public enum MonitorType{
		Field,
		Method,
		Object,
		Null
	}
	
	
}
