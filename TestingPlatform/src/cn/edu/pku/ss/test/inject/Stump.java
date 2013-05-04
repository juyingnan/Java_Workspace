package cn.edu.pku.ss.test.inject;

public abstract class Stump<T> {

	private T node;
	private int Id;
	private String catalog;
	private boolean flushed;
	private String injectMethod;
	
	public void setNode(T node) {
		this.node = node;
	}
	public T getNode() {
		return node;
	}

	public void setId(int id) {
		Id = id;
	}
	public int getId() {
		return Id;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getCatalog() {
		return catalog;
	}
	
	public void setFlushed(boolean flushed) {
		this.flushed = flushed;
	}
	public boolean isFlushed() {
		return flushed; 
	}
	

	public void setInjectMethod(String injectMethod) {
		this.injectMethod = injectMethod;
	}
	public String getInjectMethod() {
		return injectMethod;
	}

	public abstract String getMethodStumpCode();
	public abstract String getParameterStumpCode();
	
}
