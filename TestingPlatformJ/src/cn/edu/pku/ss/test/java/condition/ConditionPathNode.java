package cn.edu.pku.ss.test.java.condition;

import java.util.ArrayList;

public class ConditionPathNode{
	
	private int conditionId;
	private int subConditionId;
	private ArrayList<Boolean> history;
	
	public ConditionPathNode(int conditionId, int subConditionId) {
		this.conditionId = conditionId;
		this.subConditionId = subConditionId;
		history = new ArrayList<Boolean>();
	}
	
	public boolean equals(ConditionPathNode node){
		if(this.conditionId == node.conditionId && this.subConditionId== node.subConditionId){
			return true;
		} else {
			return false;
		}
		
	}
	
	public int plusCount(Boolean bool){		
		history.add(bool);
		return history.size();
	}
	
	public int getCount() {		
		return history.size();
	}

	public int getConditionId() {
		return conditionId;
	}
	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}
	public int getSubConditionId() {
		return subConditionId;
	}
	public void setSubConditionId(int subconditionId) {
		this.subConditionId = subconditionId;
	}

	@Override
	public String toString(){
		return conditionId + ":" + subConditionId + "=" + history.size();
	}


	public ArrayList<Boolean> getHistory() {
		return history;
	}
	
}
