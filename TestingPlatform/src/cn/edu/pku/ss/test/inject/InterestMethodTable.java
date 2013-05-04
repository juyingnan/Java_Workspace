package cn.edu.pku.ss.test.inject;

import java.util.ArrayList;
import java.util.Hashtable;



public class InterestMethodTable extends Hashtable<String,ArrayList<String>> {


	public void register(String className, String method){
		
		ArrayList<String> list;
		
		if(this.containsKey(className)){
			list = this.get(className);
		}
		else{
			list = new ArrayList<String>();
		}
		
		if(!list.contains(method)){
			list.add(method);
		}		
		
		this.put(className, list);		
		
	}
	
	public boolean unregister(String className, String method){
		
		ArrayList<String> list;		
		if(this.containsKey(className)){
			list = this.get(className);
			
			return list.remove(method);
		
		}
		else return false;
		
	}
	
	
	
}
