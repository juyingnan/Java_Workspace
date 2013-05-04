package cn.edu.pku.ss.test.java.symbols;

import java.util.ArrayList;
import java.util.Collection;

public class SymbolTable extends ArrayList<Symbol> {

	private int number = 0;
	
	public SymbolTable(){
		
	}
	
	@Override
	public boolean add(Symbol symbol){
		if(this.add(symbol)){
			symbol.setId(number++);
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void add(int index, Symbol symbol){
		symbol.setId(number++);
		this.add(index, symbol);
	}
	
	@Override
	public boolean addAll(Collection<? extends Symbol> c){
		int start = this.size();
		boolean result = this.addAll(c);
		for(int i= start; i < this.size(); i++){
			this.get(i).setId(number++);
		}			
		return result;			
	}
	
	@Override
	public boolean addAll(int index,Collection<? extends Symbol> c){
		 int start = index;
		 int end = index + c.size();
		 boolean result = this.addAll(c);
		 if(result){
			for(int i= start; i < end; i++){
				this.get(i).setId(number++);
			}		
		 }
			return result;	
	}
	
	public Symbol getSymbolById(int id){
		for (int i = 0; i < this.size(); i++) {
			if(this.get(i).getId()==id)
				return this.get(i);
		}
		return null;
	}
	
	public SymbolTable getSymbolsByClass(String className){
	
		return searchSymbol(className, SearchType.ClassName);
	}

	
	public SymbolTable getSymbolsByMethod(String methodName){
		
		return searchSymbol(methodName, SearchType.MethodName);
	}
	
	public SymbolTable getSymbolsByFullName(String fullName){
		
		return searchSymbol(fullName, SearchType.FullName);
	}
	
	private SymbolTable searchSymbol(String name, SearchType type) {
		SymbolTable st = new SymbolTable();
		for (int i = 0; i < this.size(); i++) {
			switch (type) {
			case ClassName:
				if (this.get(i).getParentClass().getName() == name) {
					st.add(this.get(i));
				}
				break;
			case MethodName:
				if (this.get(i).getParentMethod().getName() == name) {
					st.add(this.get(i));
				}
				break;
			case FullName:
				if (this.get(i).getNamePosition().getCurrentName() == name) {
					st.add(this.get(i));
				}
				break;
			}

		}
		return st;
	}
	
	@Override
	public String toString(){		
		StringBuilder sb  = new StringBuilder();

		for(int i =0; i < this.size(); i++){
			sb.append(this.get(i).toString() + "\r\n");
		
		}
		return sb.toString();
	}
	
	private enum SearchType{
		ClassName,
		MethodName,
		FullName
	}
	
}
