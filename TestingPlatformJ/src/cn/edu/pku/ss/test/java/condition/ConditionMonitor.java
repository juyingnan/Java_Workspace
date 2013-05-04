package cn.edu.pku.ss.test.java.condition;

import java.util.ArrayList;

import cn.edu.pku.ss.test.inject.monitors.Monitor;

public class ConditionMonitor extends Monitor {

	private ConditionPath pathRecords;	
	
	public ConditionMonitor(){
		pathRecords = new ConditionPath();
	}
	
	public ConditionMonitor(Integer[] args){
		pathRecords = new ConditionPath();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i=0; i < args.length; i++){
			arr.add(args[i]);
		}	
		pathRecords.setConditionInfo(arr);
	}
	
	
	public boolean visit(int conditionId, int subConditionId, boolean condition){	
		pathRecords.addPath(conditionId,subConditionId,condition);
		return true;
	}
	
	@Override
	public Object getResult() {	
		
		//Debug.println(pathRecords.getResult());
		return pathRecords;
	}

}
