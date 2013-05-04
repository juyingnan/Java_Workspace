package cn.edu.pku.ss.test.inject.monitors;

import java.util.ArrayList;

public class IDMonitor extends Monitor{

	private ArrayList<Integer> records = new ArrayList<Integer>();

	public boolean visit(int id){
		records.add(id);
		return true;
	}
	
	@Override
	public Object getResult() {		
		return records;
	}
	
}
