package cn.edu.pku.ss.test.java.df;

import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.java.df.chains.DuChain;
import cn.edu.pku.ss.test.java.df.chains.DuNode;

public class DuStumpManager extends Monitor<DuChain>{

	DuChain duStumpPath;
	
	public DuStumpManager(){
		duStumpPath = new DuChain();
	}
	
	public void call(int id, DuStumpType type){
		DuNode node = new DuNode();
		node.setId(id);
		node.setType(type);
		duStumpPath.add(node);
	}
	
	@Override
	public DuChain getResult() {
		return duStumpPath;
	}
	 
}
