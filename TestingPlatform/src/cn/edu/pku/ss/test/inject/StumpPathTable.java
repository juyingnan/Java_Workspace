package cn.edu.pku.ss.test.inject;

import java.util.Hashtable;


public class StumpPathTable extends Hashtable<String,StumpTable>{

	public final int DefaultId = 1;

	public int put(String className, String methodName, Stump att){

		if(this.containsKey(className)){
			if(this.get(className).containsKey(methodName)){
				return this.get(className).get(methodName).add(att);
			}
			else{
				this.get(className).put(methodName, att);
				return DefaultId;
			}
		}
		else{
			StumpTable mt = new StumpTable();
			mt.put(methodName, att);
			this.put(className, mt);
			return DefaultId;
		}
	}

}
