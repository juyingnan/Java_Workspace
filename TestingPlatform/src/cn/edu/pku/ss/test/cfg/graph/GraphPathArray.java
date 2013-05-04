package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;

public class GraphPathArray extends ArrayList<GraphPath>{
	
	public void add(ArrayList<Integer> item) {
		this.add(new GraphPath(item));		
	}
	
	@Override
	public String toString(){
		StringBuilder buff = new StringBuilder();
		
		for(int i=0;i< this.size(); i++){
			buff.append(this.get(i).toString() + "\r\n");
		}
		
		return buff.toString();
	}


}
