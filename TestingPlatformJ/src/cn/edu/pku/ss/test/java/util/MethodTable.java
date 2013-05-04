package cn.edu.pku.ss.test.java.util;


import japa.parser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class MethodTable extends Hashtable<String, ArrayList<MethodDeclaration>> {
	
	public void put(String className,MethodDeclaration method){
		
		ArrayList<MethodDeclaration> list;
		
		if(!super.containsKey(className)){
			list = new ArrayList<MethodDeclaration>();
		}
		else{
			list = super.get(className);
		}
		
		list.add(method);
		super.put(className, list);	
		
	}
	
	public String toLinerString(){
		StringBuilder buff = new StringBuilder();
		Enumeration<String> classNames =this.keys();
		
		while(classNames.hasMoreElements()){
			String className = classNames.nextElement();			
			buff.append(";"+ className + ",");			
			ArrayList<MethodDeclaration> arr = 	this.get(className);
			
			for(int i =0; i < arr.size(); i++){	
				MethodDeclaration method = arr.get(i);
				buff.append(method.getName() + ",");		
			}
		}
		
		return buff.toString();
	}
	
	
	@Override
	public String toString(){
		StringBuilder buff = new StringBuilder();
		Enumeration<String> classNames =this.keys();
		
		while(classNames.hasMoreElements()){
			String className = classNames.nextElement();			
			buff.append("["+ className + "]\r\n");			
			ArrayList<MethodDeclaration> arr = 	this.get(className);
			
			for(int i =0; i < arr.size(); i++){	
				MethodDeclaration method = arr.get(i);
				buff.append("("+ method.getName() + ")\r\n");		
			}
		}
		
		return buff.toString();
	}
	
}
