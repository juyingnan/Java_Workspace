package cn.edu.pku.ss.test.inject.monitors;

import cn.edu.pku.ss.test.util.IDValuePair;
import cn.edu.pku.ss.test.util.IDValuePairArray;

public class ValueMonitor extends Monitor {

	private IDValuePairArray<Integer,Object> records = new  IDValuePairArray<Integer,Object>();

	public ValueMonitor(){
		
	}
	
	public boolean visit(int id, Object value){
		records.add(new IDValuePair<Integer, Object>(id, value));
		return true;
	}
	
	@Override
	public Object getResult() {		
		return records;
	}
	
	
}
