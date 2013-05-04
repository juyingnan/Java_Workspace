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
	 * ��õ�ǰ����
	 * */
	public String getCurrentName() {
		if(IsInMethod())
			return getFullClassName() + ConstText.DOT + methodName;
		else
			return getFullClassName();
			
	}
	
	/**
	 * �������
	 * */
	public String getClassName(){
		return className;
	}

	/**
	 * ��ȡ����
	 * */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * ��ȡ�����İ���.����
	 * */
	public String getFullClassName(){
		if(packageName==null || packageName.length()==0)
			return className;
		else
			return packageName + ConstText.DOT + className;
	}
	
	/**
	 * ָʾ��ǰλ���Ƿ����ڷ�����
	 * */
	public boolean IsInMethod(){		
		return methodName.length()!=0;
	}
	
	/**
	 * ˢ������
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
