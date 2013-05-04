package cn.edu.pku.ss.test.cpp.condition;

import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.util.IDValuePairArray;

public class ConditionMonitor extends Monitor {

	private IDValuePairArray<Integer, Boolean> pathRecorder = new IDValuePairArray<Integer, Boolean>();
	
	static {
		System.loadLibrary("$LibName$");
	}
	
	public boolean visit(int id, boolean value){		
		pathRecorder.add(id,value);		
		return true;
	}
	
	@Override
	public Object getResult() {
		return pathRecorder;
	}
	
	//$Methods$

}
