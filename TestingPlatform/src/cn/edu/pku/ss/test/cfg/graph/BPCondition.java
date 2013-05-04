package cn.edu.pku.ss.test.cfg.graph;

public class BPCondition {
	private int id;
	private boolean isThen;
	private boolean isElse;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isThen() {
		return isThen;
	}
	public void setThen(boolean isThen) {
		this.isThen = isThen;
	}
	
	public void setElse(boolean isElse) {
		this.isElse = isElse;
	}
	public boolean isElse() {
		return isElse;
	}
	
	
	
	public BPCondition(){}
	
	public BPCondition(int id){
		setId(id);
	}
	public void setThenElse(boolean isThen) {
		if(isThen){
			setThen(true);
		} else {
			setElse(true);
		}
		
	}



}
