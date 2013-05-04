package cn.edu.pku.ss.test.java.symbols;

import java.util.List;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;


import cn.edu.pku.ss.test.java.jobs.visitors.StumpVisitor;

public class SymbolVisitor extends StumpVisitor<SymbolTable, Object> {

	protected SymbolTable symbolTable;
	protected ClassOrInterfaceDeclaration classNode;
	protected MethodDeclaration methodNode;
	protected Node scopeNode;
	protected BlockType blockType;

	public SymbolVisitor(){
		super();
	}
	
	@Override
	public SymbolTable visit(CompilationUnit n, Object arg) {
		symbolTable = new SymbolTable();
		blockType = BlockType.Empty;
		super.visit(n, arg);
		// System.out.println(symbolTable.size());
		return symbolTable;
	}

	@Override
	public SymbolTable visit(ClassOrInterfaceDeclaration n, Object arg) {
		classNode = n;
		scopeNode = n;
		blockType = BlockType.Empty;
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(MethodDeclaration n, Object arg) {
		methodNode = n;
		scopeNode = n;
		blockType = BlockType.EnterMethod;
		super.visit(n, arg);
		blockType = BlockType.Empty;
		return symbolTable;
	}

	@Override
	public SymbolTable visit(BlockStmt n, Object arg) {
		if (blockType == BlockType.EnterMethod) {
			blockType = BlockType.EnterBlock;
		} else if (blockType == BlockType.EnterBlock) {
			scopeNode = n;
		}
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(ForeachStmt n, Object arg) {
		scopeNode = n;
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(ForStmt n, Object arg) {
		scopeNode = n;
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(FieldDeclaration n, Object arg) {

		parseVariables(n, n.getVariables());
		// System.out.println("debug: f " + n.toString());
		super.visit(n, arg);
		n.getType();
		return symbolTable;
	}

	@Override
	public SymbolTable visit(VariableDeclarationExpr n, Object arg) {

		parseVariables(n, n.getVars());
		// System.out.println("debug: v " + n.toString());
		super.visit(n, arg);
		return symbolTable;
	}

	@Override
	public SymbolTable visit(Parameter n, Object arg) {

		Symbol s = new Symbol(n.getId().getName(), n, scopeNode, namePosition,
				classNode, methodNode);
		symbolTable.add(s);
		// System.out.println("debug: p " + n.toString());
		super.visit(n, arg);
		return symbolTable;
	}

	protected void parseVariables(Node n, List<VariableDeclarator> varList) {

		for (int i = 0; i < varList.size(); i++) {
			Symbol s = new Symbol(varList.get(i).getId().getName(), n,
					scopeNode, namePosition, classNode, methodNode);
			symbolTable.add(s);
		}
	}

	protected enum BlockType {
		Empty, EnterMethod, EnterBlock
	}

}
