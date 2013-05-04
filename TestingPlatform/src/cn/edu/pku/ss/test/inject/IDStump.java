package cn.edu.pku.ss.test.inject;

public class IDStump extends Stump {

	@Override
	public String getMethodStumpCode() {		
		return "visit(" + this.getId()+")";
	}

	@Override
	public String getParameterStumpCode() {		
		return this.getId() + "";
	}

	
}
