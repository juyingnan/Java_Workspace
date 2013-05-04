package cn.edu.pku.ss.test.java.df;

import cn.edu.pku.ss.test.inject.Stump;

public class DuStump extends Stump{

	protected DuStumpType type;
	
	public DuStump(DuStumpType type){
		this.type = type;
	}
	
	@Override
	public String getMethodStumpCode() {		
		return "call(" + this.getId() + ", DuStumpType." + this.type.toString()+ ");";
	}

	@Override
	public String getParameterStumpCode() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
