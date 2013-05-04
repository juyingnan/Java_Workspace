package cn.edu.pku.ss.test.util;

public class IDValuePair<IDType, ValueType> {

	private IDType id;

	private ValueType value;
	
	public IDValuePair(){
		
	}
	
	public IDValuePair(IDType id, ValueType value){
		setId(id);
		setValue(value);
	}
	
	
	public IDType getId() {
		return id;
	}
	public void setId(IDType id) {
		this.id = id;
	}
	public ValueType getValue() {
		return value;
	}
	public void setValue(ValueType value) {
		this.value = value;
	}
	
	public String toString(){
		return "id=" +this.id + " value=" + this.value;
	}
	
}
