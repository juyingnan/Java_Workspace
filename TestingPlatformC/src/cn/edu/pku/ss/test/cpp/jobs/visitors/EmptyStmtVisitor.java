package cn.edu.pku.ss.test.cpp.jobs.visitors;


import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement;

import cn.edu.pku.ss.test.cpp.ast.ASTHelper;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.util.Log;

public class EmptyStmtVisitor extends  SourceVisitor  implements IJob {

	public EmptyStmtVisitor(){
		super(true);
	}
	
	@Override
	public boolean run(TestData data) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int visit(CPPASTIfStatement n) {
		checkStatement(n.getThenClause());
		if(n.getElseClause()!=null) {
			checkStatement(n.getElseClause());
		}
		return super.visit(n);
	}
	
	public int visit(CPPASTWhileStatement n){
		checkStatement(n.getBody());
		return super.visit(n);
	}
	
	public int visit(CPPASTForStatement n){
		checkStatement(n.getBody());
		return super.visit(n);
	}
	
	public void checkStatement(IASTStatement n){
		if(n.getClass() != CPPASTCompoundStatement.class){
			System.out.println("Warn None {} after if/whle/for at offset " 
					+ ASTHelper.getPosition(n));		
		}
	}
	
	

}
