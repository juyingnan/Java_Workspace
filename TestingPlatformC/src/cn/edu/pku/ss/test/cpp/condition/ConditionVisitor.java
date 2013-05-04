package cn.edu.pku.ss.test.cpp.condition;

import java.util.ArrayList;


import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement;

import cn.edu.pku.ss.test.condition.ConditionTree;
import cn.edu.pku.ss.test.condition.ConditionTreeNode;
import cn.edu.pku.ss.test.condition.ConditionTreeNode.OperatorType;
import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.inject.CppEnvironment;
import cn.edu.pku.ss.test.cpp.jobs.visitors.SingleFunctionVisitor;
import cn.edu.pku.ss.test.cpp.jobs.visitors.SourceVisitor;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.ValueStump;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class ConditionVisitor extends SingleFunctionVisitor{
	
	private ConditionTree conditionTree;
	private ArrayList<ValueStump> curStumpList;
	
	
	public ConditionVisitor(){
		conditionTree = new ConditionTree();
		curStumpList = new ArrayList<ValueStump>();
	}
	
	public boolean run(TestData data){
		
		if(super.run(data)){			
			data.put(JobConst.CONDITION_TREES, conditionTree);
			data.put(Const.SOURCE_WITH_MONITOR, this.getSource());
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public int visit(CPPASTIfStatement n) {		
		if(this.isInTargetFunction()){
			
			printer.print("if (");
			visitCondition(n.getConditionExpression());
			printer.print(") ");
		
			n.getThenClause().accept(this);

			if (n.getElseClause() != null) {
				if(n.getThenClause().getClass() != CPPASTCompoundStatement.class ){
					printer.println();
					printer.print("else ");
				} else {
					printer.print(" else ");
				}
				
				n.getElseClause().accept(this);			}
	
			return PROCESS_SKIP;			
		} else {
			return super.visit(n);
		}
	}
	
	@Override
	public int visit(CPPASTWhileStatement n) {
		if (this.isInTargetFunction()) {
			printer.print("while (");
			visitCondition(n.getCondition());
			printer.print(") ");
			n.getBody().accept(this);

			return PROCESS_SKIP;
		} else {
			return super.visit(n);
		}
	}
	
	@Override
	public int visit(CPPASTForStatement n) {
		if (this.isInTargetFunction()) {
			printer.print("for (");
			n.getInitializerStatement().accept(this);
			visitCondition(n.getConditionExpression());
			printer.print(";");
			n.getIterationExpression().accept(this);
			printer.print(") ");
			n.getBody().accept(this);
			return PROCESS_SKIP;
		} else {
			return super.visit(n);
		}
	}

	@Override
	public int visit(CPPASTDoStatement n) {
		if (this.isInTargetFunction()) {
			printer.print("do ");
			n.getBody().accept(this);
			printer.print(" while (");
			visitCondition(n.getCondition());
			printer.print(");");
			return PROCESS_SKIP;
		} else {
			return super.visit(n);
		}
	}
	

	public void visitCondition(IASTExpression expr){
		ConditionTreeNode node = visitSubCondition(expr);
		conditionTree.add(node);
		while(curStumpList.size()>0){
			printer.print( this.getStumpCode(curStumpList.get(0)) + " && ");
			curStumpList.remove(0);
		}
		printer.print( "( " + node.toString() + " )" );
		
		
	}
	
	public ConditionTreeNode visitSubCondition(IASTExpression expr){
		
		Class clazz = expr.getClass();
		
		if(clazz == CPPASTBinaryExpression.class){
			CPPASTBinaryExpression binExpr = (CPPASTBinaryExpression)expr;
			if(binExpr.getOperator() == IASTBinaryExpression.op_logicalAnd ||
					binExpr.getOperator() == IASTBinaryExpression.op_logicalOr ){
				//构造一个ConditionTreeNode根节点
    			ConditionTreeNode root = new ConditionTreeNode();    			
    			//设置左边的节点
    			root.setLeft(visitSubCondition(binExpr.getOperand1()));
    			if(binExpr.getOperator()== IASTBinaryExpression.op_logicalAnd){    				
    				root.setOperator(OperatorType.And);
    			}
    			else{
    				root.setOperator(OperatorType.Or);
    				
    			}
    			//设置右边的节点
    			root.setRight(visitSubCondition(binExpr.getOperand2()));
    			
    			return root;
			} else {
				addConditionStump(expr);
				//Debug.println(SourceVisitor.getASTCode(binExpr));
				return new ConditionTreeNode(SourceVisitor.getASTCode(binExpr));
			}
			
		} else if(clazz == CPPASTUnaryExpression.class && 
				((CPPASTUnaryExpression)expr).getOperator() == IASTUnaryExpression.op_bracketedPrimary ){
			//构造一个ConditionTreeNode根节点
			ConditionTreeNode root = new ConditionTreeNode();   
			root.setOperator(OperatorType.Bracket);
			//设置左边的节点
			ConditionTreeNode left= visitSubCondition(((CPPASTUnaryExpression)expr).getOperand());
			root.setLeft(left);
			return root;
			
		} else {
			//Debug.println(SourceVisitor.getASTCode(expr));
			addConditionStump(expr);
			return new ConditionTreeNode(SourceVisitor.getASTCode(expr));
		}
		
	}
	
	public void addConditionStump(IASTExpression expr){
		ValueStump stump = (ValueStump)this.create(expr, new ValueStump());
		stump.setValue(SourceVisitor.getASTCode(expr));
		curStumpList.add(stump);
	}

	
}
