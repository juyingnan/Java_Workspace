package cn.edu.pku.ss.test.java.bp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.BinaryExpr.Operator;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.WhileStmt;
import cn.edu.pku.ss.test.cfg.graph.ControlLogic;
import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.cfg.graph.GraphNode;
import cn.edu.pku.ss.test.condition.ConditionTreeNode;
import cn.edu.pku.ss.test.condition.ConditionTreeNode.OperatorType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.IDStump;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.inject.monitors.IDMonitor;
import cn.edu.pku.ss.test.java.Const;
import cn.edu.pku.ss.test.java.jobs.visitors.SingleMethodVisitor;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class BPVisitor extends SingleMethodVisitor<Object, Object> {

	protected ControlLogic logic = null;
	protected Stack<Node> parents = null;
	protected Stack fgnId = null;
	protected ArrayList stmtLines = null;
	protected static HashSet<Class<? extends Node>> hasClass =  new HashSet<Class<? extends Node>>();
	//protected boolean eachLineMode = false;
		
	static{
		hasClass.add(IfStmt.class);
		hasClass.add(ForStmt.class);
		hasClass.add(WhileStmt.class);
		hasClass.add(DoStmt.class);
		hasClass.add(SwitchStmt.class);
		hasClass.add(TryStmt.class);
		hasClass.add(ReturnStmt.class);
		hasClass.add(ContinueStmt.class);
		hasClass.add(BreakStmt.class);
		hasClass.add(ThrowStmt.class);
		hasClass.add(BlockStmt.class);
	}
	
	public BPVisitor() {
		logic = new ControlLogic(this, IDStump.class);
		parents = new Stack<Node>();
		fgnId= new Stack();
		stmtLines = new ArrayList();
		this.getStumpType().setStumpManagerName(IDMonitor.class);
		this.setFixModifiers(true);
	}

	public FlowGraph getGraph() {
		return logic.getGraph();
	}

	@Override
	public boolean run(TestData data) {
		if (super.run(data)) {
			data.put(JobConst.MONITOR, this.getMonitorName());
			data.put(JobConst.CONTROL_FLOW_GRAPH, logic.getGraph());
			return true;
		} else {
			return false;
		}
	}

	
	public Object visit(MethodDeclaration n, Object arg) {
		parents.push(n);
		super.visit(n, arg);
		parents.pop();
		return null;
	}

	@Override
	public Object visit(IfStmt n, Object arg) {

		if (isInTargetMethod()) {
			parents.push(n);
			//Add begin line number
			Stump root = logic.createRoot(n, false, n.getBeginLine());
			printer.println(this.getStumpCode(root) + ";");
			if (stmtLines.size() != 0){
				logic.getGraph().getLast().setBeginLine((Integer)stmtLines.get(0));
			}else {
				logic.getGraph().getLast().setBeginLine(n.getBeginLine());
			}
			logic.getGraph().getLast().setEndLine(n.getBeginLine());
			

			printer.print("if (");
			ConditionTreeNode node = visitCondition(n.getCondition(), false);
			//n.getCondition().accept(this, arg);
			
			printer.print(") ");
			
			
			//add begin line number
			logic.createFirstChild(n.getThenStmt(), root, n.getThenStmt().getBeginLine());
			//Push the current flow graph node Id into the stack 
			//fgnId.push(logic.getGraph().getLast().getId());
			visitStmt(n.getThenStmt(), arg);
			logic.getGraph().getLast().setBeginLine(n.getThenStmt().getBeginLine());
			logic.getGraph().getLast().setEndLine(n.getThenStmt().getEndLine());
			
			if (n.getElseStmt() != null) {
				printer.print(" else ");                  
				//Add begin line number
				logic.createSecondChild(n.getElseStmt(), root, n.getElseStmt().getBeginLine());
			    //Push the current flow graph node Id into the stack 
				//fgnId.push(logic.getGraph().size()-1);
				visitStmt(n.getElseStmt(), arg);
				logic.getGraph().getLast().setBeginLine(n.getElseStmt().getBeginLine());
				logic.getGraph().getLast().setEndLine(n.getElseStmt().getEndLine());
			} else {
				logic.pushSecondChild(root);
			}
			parents.pop();
			stmtLines.clear();
			return null;
		} else
			return super.visit(n, arg);
	}

	/**
	 * For根节点
	 * */
	public Object visit(ForStmt n, Object arg) {

		if (isInTargetMethod()) {
			parents.push(n);
			printer.print("for (");
			visitForStmtHeaderInit(n, arg);
			//changed by me'''
			Stump root = logic.createRoot(n, true, n.getBeginLine());
			printer.print(this.getStumpCode(root));
			if (n.getCompare() != null) {
				printer.print(" && (");
				n.getCompare().accept(this, arg);
				printer.print(")");
			}
			printer.print("; ");
			visitForStmtHeaderUpdate(n, arg);
			printer.print(") ");
			//changed by me'''
			logic.createFirstChild(n.getBody(), root, n.getBeginLine());		
			visitStmt(n.getBody(), arg);
			logic.pushSecondChild(root, true);
			
			parents.pop();
			return null;
		} else {
			return super.visit(n, arg);
		}
	}

	/**
	 * While根节点
	 * */
	@Override
	public Object visit(WhileStmt n, Object arg) {
		if (isInTargetMethod()) {
			parents.push(n);
			// 访问while头部信息
			printer.print("while (");
			//Add begin line number
			Stump root = logic.createRoot(n, true, n.getBeginLine());
			printer.print(this.getStumpCode(root) + " && (");
			n.getCondition().accept(this, arg);
			printer.print(")) ");
			// 第一条路径
			//Add begin line number
			logic.createFirstChild(n.getBody(), root, n.getBeginLine());
			// 访问while的Body
			visitStmt(n.getBody(), arg);
			logic.pushSecondChild(root, true);
			parents.pop();
			return null;
		} else {
			return super.visit(n, arg);
		}

	}

    public Object visit(DoStmt n, Object arg) {
    	if (isInTargetMethod()) {
    		parents.push(n);
    		//Add begin line number
	    	Stump first = logic.createRoot(n, false, n.getBeginLine());
	    	
	        printer.print("do ");
	        this.visitStmt(n.getBody(), arg);      
	        printer.print(" while (");
	    	//Add begin line number
	        Stump root =  logic.createRoot(n, true, n.getBeginLine());
	        //Debug.println(logic.getGraph().getById(7));
	        logic.attachFirst(root.getId(), first.getId());
	        printer.print(getStumpCode(root) + " && (");
	        //Debug.println(logic.getGraph().getById(7));
	        n.getCondition().accept(this, arg);
	        printer.print("));");
	        logic.pushSecondChild(root, true);
	        //Debug.println(logic.getGraph().getById(7));
	        parents.pop();
	        return null;
    	} else {
			return super.visit(n, arg);
		}
    }
	
    public Object visit(SwitchStmt n, Object arg) {
    	if (isInTargetMethod()) {
    		parents.push(n);
    		//Add begin line number
    		Stump root = logic.createRoot(n, false, n.getBeginLine());
    		printer.println(this.getStumpCode(root) + ";");
			printer.print("switch(");
			n.getSelector().accept(this, arg);
			printer.println(") {");
			if (n.getEntries() != null) {
				printer.indent();
				for (SwitchEntryStmt e : n.getEntries()) {
					Stump c = null;
					if(e.getStmts()!=null){
						c = logic.createChild(e, root, n.getBeginLine());
					}
					e.accept(this, arg);
					if(e.getStmts()!=null){
						logic.pushNextChild(logic.getLast(e).getId());
					}
				}
				printer.unindent();
			}
			printer.print("}");
			logic.pushSecondChild(root);
			parents.pop();
	        return null;
    	} else {
			return super.visit(n, arg);
		}
    }
    
    public Object visit(SwitchEntryStmt n, Object arg) {
    	if (isInTargetMethod()) {
	        if (n.getLabel() != null) {
	            printer.print("case ");
	            n.getLabel().accept(this, arg);
	            printer.print(":");
	        } else {
	            printer.print("default:");
	        }
	        printer.println();
	        printer.indent();
	        if (n.getStmts() != null) {
	            for (Statement s : n.getStmts()) {
	                s.accept(this, arg);
	                printer.println();                
	            }
	            flushStump(n);
	        }
	        printer.unindent();
	        return null;
    	} else { 
    		return super.visit(n, arg);
    	}
    }
    
    
	/**
	 * Return根节点
	 * */
	@Override
	public Object visit(ReturnStmt n, Object arg) {
		if (this.isInTargetMethod()) {
			Stump stump = logic.getEndStump(n, n.getBeginLine());
			logic.getGraph().getLast().setBeginLine(n.getBeginLine());
			logic.getGraph().getLast().setEndLine(n.getEndLine());
			
			if (!stump.isFlushed()) {
				printer.println(this.getStumpCode(stump) + ";");
			}
		}
		return super.visit(n, arg);
	}
	
	/**
	 * Break根节点
	 * */
	@Override
	public Object visit(BreakStmt n, Object arg) {
		if(isInTargetMethod()) {
			Stump stump = logic.getLast(n);

    		if(!stump.isFlushed()){
    			printer.println(this.getStumpCode(stump) + ";");
    		} else {
    			outputError(n);
    		}	

		}
    	
    	return super.visit(n, arg);
    }
	
	/**
	 * Continue根节点
	 * */
	@Override
    public Object visit(ContinueStmt n, Object arg) {    	
    	if(isInTargetMethod()){     		
    		Stump stump = logic.getContinue(n);
    		if(!stump.isFlushed()){
    			printer.println(this.getStumpCode(stump) + ";");
    		} else {
    			outputError(n);
    		}
		}   	
    	return super.visit(n, arg);
    }
    
	/**
	 * Throw根节点
	 * */
	@Override
    public Object visit(ThrowStmt n, Object arg) {
		Stump stump = logic.getEndStump(n, n.getBeginLine());
		if (!stump.isFlushed()) {
			printer.println(this.getStumpCode(stump) + ";");
		}
		return super.visit(n, arg);
    }

	public Object visit(TryStmt n, Object arg) {
    	if (isInTargetMethod()) {
    		parents.push(n);    		
    		//Add begin line number
    		Stump root = logic.createRoot(n, false, n.getBeginLine());
    		printer.println(this.getStumpCode(root) + ";");
    		
    		logic.enterTry();
    		printer.print("try ");
            n.getTryBlock().accept(this, arg);  
            
            //Try 结束，开始 Catch
            logic.enterCatch();            
            Stump last = logic.getLast(n);
            ArrayList<Stump> catchs = new  ArrayList<Stump>();
            if (n.getCatchs() != null) {
                for (CatchClause c : n.getCatchs()) {
                	//Add begin line number
                	logic.meetCatch(logic.createRoot(c, false, n.getBeginLine()).getId());
                    c.accept(this, arg);   
                    catchs.add(logic.getLast(n));
                }
            }            
           
            
            if (n.getFinallyBlock() != null) {
            	
                printer.print(" finally ");
                n.getFinallyBlock().accept(this, arg);
            }
            
            logic.existCatch();
            parents.pop();
            logic.existTry();
            return null;
            
    	} else { 
    		return super.visit(n, arg);
    	}
    }

    public Object visit(CatchClause n, Object arg) {
    	if (isInTargetMethod()) {
	        printer.print(" catch (");
	        n.getExcept().accept(this, arg);
	        printer.print(") ");
	        
	        n.getCatchBlock().accept(this, arg);
	        return null;
    	} else { 
    		return super.visit(n, arg);
    	}
    }
	
	
	/**
	 * Block根节点
	 * */
	@Override
	public Object visit(BlockStmt n, Object arg) {
		if (isInTargetMethod()) {
			printer.println("{");
			if (n.getStmts() != null) {
				printer.indent();
				
				List<Statement> list = n.getStmts();
				stmtLines.clear();
				for(int i=0;i< list.size(); i++){
					Statement s = list.get(i);
					stmtLines.add(s.getBeginLine());
					stmtLines.add(s.getEndLine());
					if(logic.isInTry()){
						if(!hasClass.contains(s.getClass())){
							//Add begin line number
							Stump stump = logic.createTryStump(s, n.getBeginLine());
							printer.println(this.getStumpCode(stump) + ";");									
						}	
					}
					s.accept(this, arg);
					printer.println();
				}
				
				if(!logic.isInTry()){
					flushStump(n);
				} else {
					//Stump stump = logic.createRoot(n, false);
					//printer.printLn(this.getStumpCode(stump) + ";");	
				}
				
//				for (Statement s : n.getStmts()) {					
//					if(!hasClass.contains(s.getClass())){						
//						Stump stump = logic.createTryStump(s);
//						printer.printLn(this.getStumpCode(stump) + ";");
//								
//					}	
//					s.accept(this, arg);					
//					printer.printLn();
//				}			
//				//flushStump(n);
//				//if(!hasClass.contains(s.getClass())){
//					
//				//}	
				
				printer.unindent();
			}

			printer.print("}");
			return null;
		} else
			return super.visit(n, arg);
	}



	protected void flushStump(Node n) {
		Stump stump = null;
		if (parents.size() > 0
				&& parents.peek().getClass() == MethodDeclaration.class
				&& logic.getGraph().getLast()!=null
				&& !logic.getGraph().getLast().isEnd()) {
			stump = logic.getEndStump(n, n.getEndLine());//~~~~~~~~~~~~change beginline to endline
		} else {
			stump = logic.getLast(n);
		}

		if (!stump.isFlushed()) {
			if (n.getClass() != BlockStmt.class)
				printer.println();
			printer.println(this.getStumpCode(stump) + ";");
		}
	}
	
	public void outputError(Node n){
		printer.println("//Control flow node conflict at line: " 
				+ n.getBeginLine() + " column: " + n.getBeginColumn());
	}
	
	
	protected ConditionTreeNode  visitCondition(Expression expr, boolean isLoop){
		//System.out.println("debug: " + exp.getClass().toString() +":"+ BinaryExpr.class.toString());
		if (expr.getClass() == BinaryExpr.class) {
			BinaryExpr bexp = (BinaryExpr) expr;
			if (bexp.getOperator() == Operator.and
					|| bexp.getOperator() == Operator.or) {
				// 构造一个ConditionTreeNode根节点
				ConditionTreeNode root = new ConditionTreeNode();
				// 设置左边的节点
				ConditionTreeNode left = visitCondition(bexp.getLeft(), isLoop);
				root.setLeft(left);
				if (bexp.getOperator() == Operator.and) {
					printer.print(" && ");
					root.setOperator(OperatorType.And);
				} else {
					printer.print(" || ");
					root.setOperator(OperatorType.Or);
				}
				// 设置右边的节点
				root.setRight(visitCondition(bexp.getRight(), isLoop));
				return root;
			}
    		else{
    			int id = flushExpression(bexp, isLoop);
    			return new ConditionTreeNode(bexp.toString(), id);
    		}
    	}
		else if (expr.getClass() == EnclosedExpr.class) {
			// 构造一个只有左节点的节点
			ConditionTreeNode root = new ConditionTreeNode(OperatorType.Bracket);
			EnclosedExpr enExp = (EnclosedExpr) expr;
			// 打印左括号
			printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
			// 设置左边的节点
			root.setLeft(visitCondition(enExp.getInner(), isLoop));
			// 打印右括号
			printer.print(ConstText.SPACE + ConstText.BRACKET_RIGHT);
			return root;
		}
    	else{
    		int id = flushExpression(expr, isLoop);
    		return new ConditionTreeNode(expr.toString(), id);
    	}

	}
	
	
	
	protected int visitCondition(Expression expr, boolean isLoop, boolean isContinue){
		int id = 0;
		if(expr.getClass() == BinaryExpr.class){
    		BinaryExpr bexp = (BinaryExpr)expr;
    		
    		if(bexp.getOperator()== Operator.and ||
    				bexp.getOperator() == Operator.or){    			
 
    			
				if (bexp.getOperator() == Operator.and) {
					id += visitCondition(bexp.getLeft(),isLoop, true);
					printer.print(" && ");
				} else {
					id += visitCondition(bexp.getLeft(),isLoop, true && isContinue);
					printer.print(" || ");
				}
				id += visitCondition(bexp.getRight(),isLoop,  true && isContinue);

    			return id;
    		}
    		else{
    			id += flushExpression(bexp, isLoop);
    			return id;
    		}
    	}
    	else if(expr.getClass() == EnclosedExpr.class){
    		EnclosedExpr enExp = (EnclosedExpr)expr;
    		
    		//打印左括号
        	printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
        	id += visitCondition(enExp.getInner() ,isLoop, isContinue);
			//打印右括号
			printer.print( ConstText.SPACE + ConstText.BRACKET_RIGHT);
    		return id;
    	}
    	else{
    		id += flushExpression(expr, isLoop);
    		return id;
		}
	}

	protected int flushExpression(Expression expr,boolean isLoop) {
		//Log.Debug(expr.toString() + "-" + isContinue);
		Stump stump = null;
		int id =0;
		//false 表示第一次进入原子条件
		if(logic.isInCondition() == false) {
			logic.setIsInCondition(true);
			stump = logic.createRoot(expr, isLoop, expr.getBeginLine());
			//Log.Debug("STUMP ID:" + stump.getId());
			id = stump.getId();
		} else {			
			stump = logic.createNext(expr, expr.getBeginLine());
			id = stump.getId();
			//Log.Debug("STUMP ID:" + stump.getId());
		}
		//输出桩信息和表达式信息
		printer.print( ConstText.BRACKET_LEFT + this.getStumpCode(stump) + " && " 
				+ expr.toString() + ConstText.BRACKET_RIGHT);
		return id;
	}

	/**
	 * Statement根节点 , 对所有的Statement都加上{}
	 * */
	protected Object visitStmt(Statement stmt, Object arg) {

		if (stmt.getClass() != BlockStmt.class) {
			printer.print(" {");
			printer.indent();
			printer.println();
		}

		stmt.accept(this, arg);

		if (stmt.getClass() != BlockStmt.class) {
			flushStump(stmt);
			printer.println();
			printer.unindent();
			printer.print("}");
		}
		return null;
	}

}
