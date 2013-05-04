package cn.edu.pku.ss.test.java.df;

import java.util.List;


import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;



import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.java.symbols.Symbol;
import cn.edu.pku.ss.test.java.symbols.SymbolTable;
import cn.edu.pku.ss.test.java.symbols.SymbolVisitor;




public class DataFlowVisitor extends SymbolVisitor{
	
	protected Symbol targetSymbol;
	protected boolean enterScope;
	
	public DataFlowVisitor(){
		super();
		this.getStumpType().setStumpManagerName(DuStumpManager.class);
		enterScope = false;
	}
	
	public Symbol getTargetSymbol() {
		return targetSymbol;
	}

	public void setTargetSymbol(Symbol targetSymbol) {
		this.targetSymbol = targetSymbol;
		//注册要关注的类
		if(targetSymbol!=null && targetSymbol.getParentClass()!=null){
			this.register(targetSymbol.getParentClass().getName());
		}
		else{
			this.clearRegistered();
		}
		enterScope = false;
	}
	
	
	
	@Override
	public SymbolTable visit(MethodDeclaration n, Object arg) {
		super.visit(n, arg);
		enterScope = false;
		return symbolTable;
	}

	@Override
	public SymbolTable visit(BlockStmt n, Object arg) {
		super.visit(n, arg);
		//如果目标变量是一个局部变量，那么当退出这个局部时，结束
		if(targetSymbol.getScope().equals(n)){
			enterScope = false;
		}
		return symbolTable;
	}
	
	@Override
	public SymbolTable visit(VariableDeclarationExpr n, Object arg) {

		if(processSymbol(n)){
			Stump stump = super.create(n, new DuStump(DuStumpType.Define));
			printer.println(stump.getMethodStumpCode());
			enterScope = true;
			
			
		}
		// System.out.println("debug: v " + n.toString());
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(Parameter n, Object arg) {
	
		Symbol s = new Symbol(n.getId().getName(), n, scopeNode, namePosition,
				classNode, methodNode);
		if(s.equals(targetSymbol)){
			enterScope = true;
		}
		// System.out.println("debug: p " + n.toString());
		super.visit(n, arg);
		return symbolTable;
	}
	
	protected boolean processSymbol(VariableDeclarationExpr n){
		List<VariableDeclarator> varList = n.getVars();
		for (int i = 0; i < varList.size(); i++) {
			Symbol s = new Symbol(varList.get(i).getId().getName(), n,
					scopeNode, namePosition, classNode, methodNode);
			if(s.equals(targetSymbol)){
				return true;
			}
		}
		return false;
	}
	

}
