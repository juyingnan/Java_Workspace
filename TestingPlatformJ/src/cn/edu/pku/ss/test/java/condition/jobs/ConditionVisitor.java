package cn.edu.pku.ss.test.java.condition.jobs;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.BinaryExpr.Operator;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.WhileStmt;


import cn.edu.pku.ss.test.condition.ConditionTree;
import cn.edu.pku.ss.test.condition.ConditionTreeNode;
import cn.edu.pku.ss.test.condition.ConditionTreeNode.OperatorType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.java.condition.ConditionMonitor;
import cn.edu.pku.ss.test.java.condition.ConditionStump;
import cn.edu.pku.ss.test.java.jobs.visitors.SingleMethodVisitor;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.jobs.JobConst;



public class ConditionVisitor extends SingleMethodVisitor<Object,Object> {
	
	private ArrayList<Integer> conditionInfo;
	private ConditionTree conditionTrees;
	private ConditionStumpStyle stumpStyle;
	private ArrayList<ArrayList<Stump>> conditionStack;
//	private HashSet<String> needMockMethodList;
//	private String needMockMethod;
	
	
	public ConditionVisitor(){
		super();
		this.getStumpType().setStumpManagerName(ConditionMonitor.class);
		this.stumpStyle = ConditionStumpStyle.SubInnerList;
	}

	public ArrayList<Integer> getConditionInfo() {
		return conditionInfo;
	}
	

	public ConditionTree getConditionTrees() {
		return conditionTrees;
	}
	
//	public HashSet<String> getNeedMockMethodList(){
//		return needMockMethodList;
//	}
	
//	public String getNeedMockMethod(){
//		return needMockMethod;
//	}

	@Override
	public Object visit(MethodDeclaration n, Object arg) {
		if (this.enterTarget(n)) {
			conditionInfo = new ArrayList<Integer>();			
			conditionStack = new ArrayList<ArrayList<Stump>>();
			conditionTrees = new ConditionTree();
		}
		Object rt = super.visit(n, arg);
		
		if (this.enterTarget(n)) {
			
			//Integer [] in = new Integer[]{2};
			
			StringBuilder sb = new StringBuilder();
			sb.append(" new Integer[] {");
			
			for(int i=0; i< conditionInfo.size()-1; i++){
				sb.append(conditionInfo.get(i)+ ", ");
			}
			if(conditionInfo.size()>0)
				sb.append(conditionInfo.get(conditionInfo.size()-1));
			sb.append("}");
			this.constructorInfo.put(this.namePosition.getFullClassName(), sb.toString());
			
			//Debug.println(constructorInfo);
		}
		
		return rt;
	}

	@Override
	public Object visit(IfStmt n, Object arg) {
		if (isInTargetMethod()) {
			
			if(stumpStyle ==ConditionStumpStyle.SubOutterList){
				visitCondition(n.getCondition());
			}
			
			printer.print("if (");
			
			if(stumpStyle ==ConditionStumpStyle.None || 
					stumpStyle ==ConditionStumpStyle.SubOutterList){
				n.getCondition().accept(this, arg);
			}
			else{
				visitCondition(n.getCondition());
			}			

			printer.print(") ");
			n.getThenStmt().accept(this, arg);
			if (n.getElseStmt() != null) {
				printer.print(" else ");
				n.getElseStmt().accept(this, arg);
			}
			return null;
		} else
			return super.visit(n, arg);
	}
	
	public Object visit(AssignExpr n, Object arg) { 
		n.getTarget().accept(this, arg);
        //printer.print(" ");
        switch (n.getOperator()) {
            case assign:
                printer.print("=");
                break;
            case and:
                printer.print("&=");
                break;
            case or:
                printer.print("|=");
                break;
            case xor:
                printer.print("^=");
                break;
            case plus:
                printer.print("+=");
                break;
            case minus:
                printer.print("-=");
                break;
            case rem:
                printer.print("%=");
                break;
            case slash:
                printer.print("/=");
                break;
            case star:
                printer.print("*=");
                break;
            case lShift:
                printer.print("<<=");
                break;
            case rSignedShift:
                printer.print(">>=");
                break;
            case rUnsignedShift:
                printer.print(">>>=");
                break;
        }
        //printer.print(" ");
        if(n.getValue().toString().endsWith("()")){
//        	System.out.println("visit assignExp in conditionVisitor**************** : " + n.getValue());
        	printer.print(n.getValue().toString());
//        	printer.print(";");
        }
        else{
        	n.getValue().accept(this, arg);
        }
        return null;
	}
	
//	public Object visit(MethodCallExpr n, Object arg) {
//        if (n.getScope() != null) {
//            n.getScope().accept(this, arg);
//            printer.print(".");
//        }
//        printTypeArgs(n.getTypeArgs(), arg);
//        printer.print(n.getName());
//        printer.print("(");
//        if (n.getArgs() != null) {
//            for (Iterator<Expression> i = n.getArgs().iterator(); i.hasNext();) {
//                Expression e = i.next();
//                e.accept(this, arg);
//                if (i.hasNext()) {
//                    printer.print(", ");
//                }
//            }
//        }
//        printer.print(")");
//        return null;
//		needMockMethodList.add("callB()");
//		printer.print("while (");
//		System.out.println("************MethodCallExpr in conditionVisitor");
//		System.out.println("In ConditionVisitor You need to do a mock object for method : " + n.getName());
//		File f = new File("C://callB.doc");
//		System.out.println(f.getPath());
//		needMockMethod = n.getName();
//		return null;
//    }

	@Override
	public Object visit(WhileStmt n, Object arg) {
		if (isInTargetMethod()) {
			
			if(stumpStyle ==ConditionStumpStyle.SubOutterList){
				visitCondition(n.getCondition());
//				printer.print(n.getCondition().toString());
				
			}
			
			printer.print("while (");


			if(stumpStyle ==ConditionStumpStyle.None || 
					stumpStyle ==ConditionStumpStyle.SubOutterList){
				n.getCondition().accept(this, arg);
			}
			else{
				visitCondition(n.getCondition());
//				printer.print(n.getCondition().toString());
			}			

			printer.print(") ");
			n.getBody().accept(this, arg);
			return null;
		} else
			return super.visit(n, arg);
	}

	@Override
	public Object visit(DoStmt n, Object arg) {
		if (isInTargetMethod()) {
			
			if(stumpStyle ==ConditionStumpStyle.SubOutterList){
				visitCondition(n.getCondition());
//				printer.print(n.getCondition().toString());
			}
			
			printer.print("do ");
			n.getBody().accept(this, arg);
			printer.print(" while (");


			if(stumpStyle ==ConditionStumpStyle.None || 
					stumpStyle ==ConditionStumpStyle.SubOutterList){
				n.getCondition().accept(this, arg);
			}
			else{
				visitCondition(n.getCondition());
//				printer.print(n.getCondition().toString());
			}			

			printer.print(");");
			return null;
		} else
			return super.visit(n, arg);
	}
	
	@Override
	public Object visit(ForStmt n, Object arg){
		if (isInTargetMethod()) {				
			if(stumpStyle ==ConditionStumpStyle.SubOutterList){
				visitCondition(n.getCompare());
//				printer.print(n.getCompare().toString());
			}			
		}
		return super.visit(n, arg);
	}

	@Override
	protected void visitForStmtHeaderCondition(ForStmt n, Object arg) {
		if (isInTargetMethod()) {
			if (n.getCompare() != null) {

				if(stumpStyle ==ConditionStumpStyle.None || 
						stumpStyle ==ConditionStumpStyle.SubOutterList){
					n.getCompare().accept(this, arg);
				}
				else{
//					visitCondition(n.getCompare());
					printer.print(n.getCompare().toString());
				}
				
			}
			printer.print("; ");
		} else {
			super.visit(n, arg);
		}
	}
    
    protected void visitCondition(Expression exp){
    	conditionInfo.add(0);
    	conditionStack.add(new ArrayList<Stump>());
    	if(stumpStyle ==ConditionStumpStyle.SubInner){
    		ConditionTreeNode root = visitSubCondition(exp);
    		conditionTrees.add(root);
    	}
    	else if(stumpStyle ==ConditionStumpStyle.Single){
    		ConditionStump stump = (ConditionStump)this.create(exp, new ConditionStump());
    		stump.setConditionId(conditionInfo.size() -1 );
    		printer.print(this.getStumpCode(stump) + " && (" + exp.toString() + ")");
    	}
    	else if(stumpStyle ==ConditionStumpStyle.SubInnerList){
    		ConditionTreeNode root = visitSubCondition(exp);
    		conditionTrees.add(root);  
    	
    		
    		ArrayList<Stump> list = conditionStack.get(conditionStack.size()-1);
    		for(int i =0; i <list.size() ; i++){
    			printer.print(this.getStumpCode(list.get(i)) + " && ");    			
    		} 	
    		printer.println();	
    		printer.print(" (" + exp.toString() + ")");
    	}
    	else if(stumpStyle ==ConditionStumpStyle.SubOutterList){
    		ConditionTreeNode root = visitSubCondition(exp);
    		conditionTrees.add(root);
    		
    		ArrayList<Stump> list = conditionStack.get(conditionStack.size()-1);
    		for(int i =0; i <list.size() ; i++){
    			printer.println(this.getStumpCode(list.get(i)) + ";");    			
    		} 		
    	
    		
    	}
    }
    
    
    
    protected ConditionTreeNode visitSubCondition(Expression exp){
    	//System.out.println("debug: " + exp.getClass().toString() +":"+ BinaryExpr.class.toString());
    	if(exp.getClass() == BinaryExpr.class){
    		BinaryExpr bexp = (BinaryExpr)exp;
    		
    		if(bexp.getOperator()== Operator.and ||
    				bexp.getOperator() == Operator.or){
    			//构造一个ConditionTreeNode根节点
    			ConditionTreeNode root = new ConditionTreeNode();    			
    			//设置左边的节点
    			ConditionTreeNode left= visitSubCondition(bexp.getLeft());
    			root.setLeft(left);
    			
    			//System.out.println("debug: " +left);
    			
    			if(bexp.getOperator()== Operator.and){
    				
    				if(stumpStyle ==ConditionStumpStyle.SubInner){
    					printer.print(" && ");
    				}
    				root.setOperator(OperatorType.And);
    			}
    			else{
    				if(stumpStyle ==ConditionStumpStyle.SubInner){
        				printer.print(" || ");
    				}
    				root.setOperator(OperatorType.Or);
    			}
    			//设置右边的节点
    			root.setRight(visitSubCondition(bexp.getRight()));
    			
    			return root;
    		}
    		else{
    			flushExpression(bexp);
    			return new ConditionTreeNode(bexp.toString());
    		}

    	}
    	else if(exp.getClass() == EnclosedExpr.class){
    		//构造一个只有左节点的节点
    		ConditionTreeNode root = new ConditionTreeNode(OperatorType.Bracket);    			
						
    		EnclosedExpr enExp = (EnclosedExpr)exp;
    		if(stumpStyle ==ConditionStumpStyle.SubInner){
    			//打印左括号
        		printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
    		}
    		
    		//设置左边的节点
			root.setLeft(visitSubCondition(enExp.getInner()));
			
			if(stumpStyle ==ConditionStumpStyle.SubInner){
				//打印右括号
				printer.print( ConstText.SPACE + ConstText.BRACKET_RIGHT);				
			}
			
		
    		return root;
    	}
    	else{
    		flushExpression(exp);
    		return new ConditionTreeNode(exp.toString());
    	}

    }


    
	protected void flushExpression(Expression exp) {
		updateCondition();
		if(stumpStyle ==ConditionStumpStyle.SubInner){
			ConditionStump stump = (ConditionStump)this.create(exp, new ConditionStump());
			//conditionCount.size() 表示有多少个条件
			// conditionCount.size()-1 表示条件的索引序号，从0开始
			stump.setConditionId(conditionInfo.size() - 1);
			//conditionCount.get(conditionCount.size() - 1) 表示当前大条件有多少个子条件
			//conditionCount.get(conditionCount.size() - 1) - 1 表示子条件的序号，从0开始
			stump.setSubId(conditionInfo.get(conditionInfo.size() - 1) -1);
			
			printer.print( ConstText.BRACKET_LEFT + this.getStumpCode(stump) + " && " 
					+ exp.toString() + ConstText.BRACKET_RIGHT);
		}
		else if(stumpStyle ==ConditionStumpStyle.SubInnerList ||
					stumpStyle ==ConditionStumpStyle.SubOutterList){
			ConditionStump stump =(ConditionStump)this.create(exp, new ConditionStump());
			//conditionCount.size() 表示有多少个条件
			// conditionCount.size()-1 表示条件的索引序号，从0开始
			stump.setConditionId(conditionInfo.size() - 1);
			//conditionCount.get(conditionCount.size() - 1) 表示当前大条件有多少个子条件
			//conditionCount.get(conditionCount.size() - 1) - 1 表示子条件的序号，从0开始
			stump.setSubId(conditionInfo.get(conditionInfo.size() - 1) -1);
			stump.setExpression(exp.toString());
			conditionStack.get(conditionStack.size()-1).add(stump);
		}		
	}
    
    
	protected void updateCondition(){
    	int c = conditionInfo.get(conditionInfo.size()-1);
    	conditionInfo.set(conditionInfo.size()-1, ++c);
    }


	public void setStumpStyle(ConditionStumpStyle stumpStyle) {
		this.stumpStyle = stumpStyle;
	}

	public ConditionStumpStyle getStumpStyle() {
		return stumpStyle;
	}
	
	@Override
	public boolean run(TestData data) {		
		if(super.run(data)){			
			//Debug.println(data.get(Const.AST));			
			data.put(JobConst.CONDITION_TREES, this.getConditionTrees());
			data.put(JobConst.MONITOR, this.getMonitorName());
//			data.put(JobConst.MOCKMETHOD, this.getNeedMockMethodList());
//			data.put(JobConst.MOCKMETHOD, this.getNeedMockMethod());
			return true;
		}
		else return false;
	}


	public enum ConditionStumpStyle{
		/**
		 * 不插桩
		 * */
		None,
		/**
		 * 只对每个条件的外层插桩
		 * */
		Single,
		/**
		 * 对每个子条件进行内部插桩
		 * */
		SubInner,
		/**
		 * 对每个子条件进行内部列表式插桩
		 * */
		SubInnerList,
		/**
		 * 外部插桩
		 * */
		SubOutterList
	}
    
    
	
}
