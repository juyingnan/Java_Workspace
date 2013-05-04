package cn.edu.pku.ss.test.java.condition;

import cn.edu.pku.ss.test.inject.Stump;

public class ConditionStump extends Stump{

	
	private int conditionId;
	private int subId;
	
	private String expression;

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}


	public void setSubId(int subId) {
		this.subId = subId;
	}

	public int getSubId() {
		return subId;
	}

	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}

	public int getConditionId() {
		return conditionId;
	}
	
	@Override
	public String getMethodStumpCode() {
//		System.out.println("~~~~~~~~~~~~~~~~~visit(" + this.getConditionId()+ " , " + getSubId()  + ", " + expression +" )");
		return "visit(" + this.getConditionId()+ " , " + getSubId()  + ", " + expression +" )";
	}

	@Override
	public String getParameterStumpCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
