package cn.edu.pku.ss.test.util;

import java.util.ArrayList;

public class IDValuePairArray<IDType, ValueType> extends ArrayList<IDValuePair<IDType, ValueType>>{
	
	public ValueType getValue(IDType id){
		for(int i=0; i< this.size(); i++){
			if(this.get(i).getId().equals(id)){
				return this.get(i).getValue();
			}
		}
		return null;
	}
	
	public boolean containsId(IDType id){
		for(int i=0; i< this.size(); i++){
			if(this.get(i).getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	public void add(IDType id, ValueType value) {
		
		this.add(new IDValuePair<IDType, ValueType>(id,value));
	}

}
