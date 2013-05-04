package cn.edu.pku.ss.test.java.util;

import java.util.Stack;


public class NamePosition {
	protected String packageName = "";
	protected String className = "";
	protected String methodName = "";
	protected Stack<String> classNameStack;
	
	public NamePosition(){
		classNameStack = new Stack<String>();
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	
	public void enterClass(String className){
		classNameStack.push(className);
		refreshClassName();
	}
	
	public void existClass(){
		classNameStack.pop();
		refreshClassName();
	}
	
	/**
	 * 获得当前名称
	 * */
	public String getCurrentName() {
		if(IsInMethod())
			return getFullClassName() + ConstText.DOT + methodName;
		else
			return getFullClassName();
			
	}
	
	/**
	 * 获得类名
	 * */
	public String getClassName(){
		return className;
	}

	/**
	 * 获取包名
	 * */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * 获取完整的包名.类名
	 * */
	public String getFullClassName(){
		if(packageName==null || packageName.length()==0)
			return className;
		else
			return packageName + ConstText.DOT + className;
	}
	
	/**
	 * 指示当前位置是否是在方法中
	 * */
	public boolean IsInMethod(){		
		return methodName.length()!=0;
	}
	
	/**
	 * 刷新类名
	 * */
	protected void refreshClassName(){
		className = "";
		for(int i =0; i < classNameStack.size()-1; i++){
			className += classNameStack.get(i) + ConstText.DOT;
		}
		if(classNameStack.size()>0){
			className += classNameStack.get(classNameStack.size()-1);
		}
	}
}
