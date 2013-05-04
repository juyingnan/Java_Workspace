package cn.edu.pku.ss.test.java.symbols;

import cn.edu.pku.ss.test.java.util.NamePosition;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.type.Type;

public class Symbol {
	/**
	 * 按照出现顺序的编号
	 * */
	private int id;
	
	/**
	 * 变量名称
	 * */
	private String name;
	
	/**
	 * 定义变量的节点
	 * */
	private Node node;
	
	/**
	 * 作用域
	 * */
	private Node scope;
	
	/**
	 * 所属的类名
	 * */
	private ClassOrInterfaceDeclaration parentClass;
	
	/**
	 * 所属的方法名
	 * */
	private MethodDeclaration parentMethod;

	/**
	 * 所属方法的名称位置，包括：包名，类名，方法名
	 * */
	private NamePosition namePosition;
	
	
	
	public Symbol(){
		
	}
	
	public Symbol(String name, Node node, Node scope, NamePosition namePosition,
			ClassOrInterfaceDeclaration parentClass,MethodDeclaration parentMethod ){
		this.name = name;
		this.node = node;
		this.scope = scope;
		this.parentClass = parentClass;
		this.parentMethod = parentMethod;
		this.namePosition = namePosition;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		if (node != null) {
			if (node.getClass() == FieldDeclaration.class) {
				return ((FieldDeclaration) node).getType();
			} else if (node.getClass() == VariableDeclarationExpr.class) {
				return ((VariableDeclarationExpr) node).getType();
			} else if (node.getClass() == Parameter.class) {
				return ((Parameter) node).getType();
			}
		}
		return null;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Node getScope() {
		return scope;
	}

	public void setScope(Node scope) {
		this.scope = scope;
	}

	public void setParentClass(ClassOrInterfaceDeclaration parentClass) {
		this.parentClass = parentClass;
	}

	public ClassOrInterfaceDeclaration getParentClass() {
		return parentClass;
	}

	public void setParentMethod(MethodDeclaration parentMethod) {
		this.parentMethod = parentMethod;
	}

	public MethodDeclaration getParentMethod() {
		return parentMethod;
	}

	public void setNamePosition(NamePosition sourcePosition) {
		this.namePosition = sourcePosition;
	}

	public NamePosition getNamePosition() {
		return namePosition;
	}
	
	@Override
	public String toString(){
		String summary = id + "  " + name + "  ";
		if(scope!=null){
			summary += scope.getClass() + "  ";
		}
		else summary += "null" + "  ";
		if(parentClass!=null){
			summary += parentClass.getName() + "  ";
		}
		else summary += "null" + "  ";		
		if(parentMethod!=null){
			summary += parentMethod.getName() + "  ";
		}
		else summary += "null" + "  ";		
		summary +=  namePosition.getCurrentName();
		return summary;
	}
	
	public boolean equals(Symbol symbol){
		if(this.node!=null && symbol.node!=null 
				&& this.node.equals(symbol)
				&& this.getType() == symbol.getType() 
				&& this.name == symbol.name){
			return true;
		}
		else return false;
	}
}
