package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

public class ConditionResult extends ArrayList<Integer>{

	public ConditionResult(){
		super();
	}
	
	public ConditionResult(String tfStr, int count) {
		super();
		for(int i=0; i< count;i++){
			if(i<tfStr.length()){
				if(tfStr.charAt(i) == 'T'){
					this.add(1);
				} else if(tfStr.charAt(i) == 'F'){
					this.add(0);
				} else{
					this.add(-1);
				}
			}
			else this.add(0);
		}
	}
	
	public void join(ConditionResult path){
		
		for(int i=0; i< path.size(); i++){
			this.add(path.get(i));
		}
	}

	
}
