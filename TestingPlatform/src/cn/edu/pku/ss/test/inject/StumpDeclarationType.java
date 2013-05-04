package cn.edu.pku.ss.test.inject;

import java.util.ArrayList;

import cn.edu.pku.ss.test.inject.monitors.Monitor;

public class StumpDeclarationType {
	
	private ArrayList<String> imports;
	private String stumpManagerName;
	
	public StumpDeclarationType(){
		imports = new  ArrayList<String>();
	}
	
	public void setStumpManagerName(Class<? extends Monitor> stumpManager) {
		this.stumpManagerName = stumpManager.getName();
	}
	public String getStumpManagerName() {
		return stumpManagerName;
	}
	
	public void addImport(String type){
		if(!imports.contains(type))
			imports.add(type);
	}
	
	public boolean removeImport(String type){
		return imports.remove(type);
	}

	public ArrayList<String> getImports() {
		return imports;
	}
	
}
