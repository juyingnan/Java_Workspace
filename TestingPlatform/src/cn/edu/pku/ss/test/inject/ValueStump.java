package cn.edu.pku.ss.test.inject;

public class ValueStump extends Stump{
	
	private Object value;

	public ValueStump(){
		this.setInjectMethod("visit");
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
	
	@Override
	public String getMethodStumpCode() {
		return this.getInjectMethod()+ "(" +  getParameterStumpCode() + ")";
	}

	@Override
	public String getParameterStumpCode() {		
		return this.getId()+ " , " + this.getValue();
	}

}
