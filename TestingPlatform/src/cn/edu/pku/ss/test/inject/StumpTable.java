package cn.edu.pku.ss.test.inject;


import java.util.Hashtable;


public class StumpTable extends Hashtable<String,StumpArray>{


	public void put(String methodName, Stump att){
		if(!this.containsKey(methodName)){
			this.put(methodName, new StumpArray());
		}
		this.get(methodName).add(att);
	}
}
