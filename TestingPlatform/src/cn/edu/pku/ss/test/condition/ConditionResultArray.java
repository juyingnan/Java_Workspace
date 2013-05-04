package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import cn.edu.pku.ss.test.util.Log;

public class ConditionResultArray extends ArrayList<ConditionResult> {

	public ConditionResultArray() {
		super();
	}

	public ConditionResultArray(ArrayList<String> tfArr, int count) {
		super();
		for (int i = 0; i < tfArr.size(); i++) {
			ConditionResult path = new ConditionResult(tfArr.get(i), count);
			this.add(path);
		}

	}

	public void join(ConditionResultArray arr){
		//Debug.println(arr.size());
		//Debug.println(this.size());
		if(this.size()==0){
			for(int i=0; i< arr.size(); i++){
				this.add(arr.get(i));
			}
		}
		else{
			if(arr.size() == this.size()){
				for(int i=0; i< arr.size(); i++){
					this.get(i).join(arr.get(i));
				}
			}
			else {
				Log.println("err!");
			}
		}
		
	}
	
	
}
