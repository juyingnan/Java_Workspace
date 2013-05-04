package cn.edu.pku.ss.test.java.bp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.VariableDeclarationExpr;
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
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;
import cn.edu.pku.ss.test.cfg.graph.BPCondition;
import cn.edu.pku.ss.test.cfg.graph.BPControlLogic;
import cn.edu.pku.ss.test.cfg.graph.ControlLogic;
import cn.edu.pku.ss.test.cfg.graph.FlowGraph;
import cn.edu.pku.ss.test.condition.ConditionTreeNode;
import cn.edu.pku.ss.test.condition.ConditionTreeNode.OperatorType;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.IDStump;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.inject.monitors.IDMonitor;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionVisitor.ConditionStumpStyle;
import cn.edu.pku.ss.test.java.jobs.visitors.SingleMethodVisitor;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

//改动项：
//加入lastnodeid和catchnodeid的定义和catchnodeid的初始化
//改进public Object visit(ExpressionStmt n, Object arg)以适应三目运算符（TEAM 1）
//改进public Object visit(SwitchStmt n, Object arg)和public Object visit(SwitchEntryStmt n, Object arg)以适应switch（TEAM 3）
//改进public Object visit(TryStmt n, Object arg)和public Object visit(CatchClause n, Object arg)以适应trycatch（TEAM 3） 问题：try里面必须有结构节点才能成功，若只有顺序语句，则会因为无法生成节点而导致流图生成出错
//trycatch的插桩功能可以实现，基本路径测试还未能实现
//2013-1-21
//Ju Yingnan
public class BasisPathVisitor extends SingleMethodVisitor<Object, Object>
{

	protected BPControlLogic						logic		= null;
	protected Stack<Node>							parents		= null;
	protected static HashSet<Class<? extends Node>>	hasClass	= new HashSet<Class<? extends Node>>();

	// WEI XIN TEAM ADDED
	protected static int							lastnodeid	= -1;
	protected static ArrayList<Integer>				catchnodeid;

	static
	{
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
		hasClass.add(ExpressionStmt.class);
		// WEI XIN TEAM ADDED
		catchnodeid = new ArrayList<Integer>();
	}

	public BasisPathVisitor()
	{
		logic = new BPControlLogic(this, IDStump.class);
		parents = new Stack<Node>();
		this.getStumpType().setStumpManagerName(IDMonitor.class);
		this.setFixModifiers(true);
	}

	public FlowGraph getGraph()
	{
		return logic.getGraph();
	}

	@Override
	public boolean run(TestData data)
	{
		if (super.run(data))
		{
			data.put(JobConst.MONITOR, this.getMonitorName());
			data.put(JobConst.CONTROL_FLOW_GRAPH, logic.getGraph());
			return true;
		}
		else
		{
			return false;
		}
	}

	public Object visit(MethodDeclaration n, Object arg)
	{
		parents.push(n);
		super.visit(n, arg);
		parents.pop();
		return null;
	}

	/*
	 * EXTENSION BY TEAM 1; Ju Yingnan/Zhang Yu/Wei Hongye/Wang Yangzhi/Weng
	 * Jiang
	 */
	@Override
	public Object visit(ExpressionStmt n, Object arg)
	{
		/*
		 * 判定是否三目运算符 目前可以实现普通赋值表达式和定义+赋值的三目运算符识别
		 */
		// try
		// {
		// flag
		boolean isConditionalExpr = false;
		// 提取n中的表达式
		Expression expression = n.getExpression();
		// 条件表达式
		ConditionalExpr cExpr = null;
		// 条件表达式中的条件
		BinaryExpr bExpr = null;
		// 记录上一节点的endline
		int lastEndline = (logic.getGraph().size() == 0) ? 1 : logic.getGraph()
				.getLast().getEndline() + 1;
		int lastGraphSize = logic.getGraph().size();

		// ZY
		// 定义Then和Else的Stmt
		ExpressionStmt CThenStmt = new ExpressionStmt();
		ExpressionStmt CElseStmt = new ExpressionStmt();

		// 此处只照顾到赋值与定义+赋值情况，若后来人可以通过通用语句满足所有情况，欢迎改正
		if (expression != null)
		{
			// 若表达式为赋值表达式
			if (expression.getClass().equals(AssignExpr.class))
			{
				AssignExpr a = (AssignExpr) expression;
				if (a.getValue() != null)
				{
					// 若赋值表达式下一层为条件表达式
					if (a.getValue().getClass().equals(ConditionalExpr.class))
					{
						isConditionalExpr = true;
						cExpr = (ConditionalExpr) a.getValue();
					}
				}
			}
			// 若表达式为变量声明表达式，提取为赋值和普通条件表达式
			else if (expression.getClass()
					.equals(VariableDeclarationExpr.class))
			{
				VariableDeclarationExpr a = (VariableDeclarationExpr) expression;
				// 提取条件表达式
				Expression e = a.getVars().get(0).getInit();
				if (e != null)
				{
					// 若为条件表达式
					if (e.getClass().equals(ConditionalExpr.class))
					{
						/*
						 * 提取为赋值和普通条件表达式
						 */

						// 定义变量声明表达式
						// 定义赋值表达式
						VariableDeclarationExpr Expr1 = new VariableDeclarationExpr();
						AssignExpr Expr2 = new AssignExpr();

						// 声明变量表达式构造
						// 变量表达
						VariableDeclarator VD = new VariableDeclarator(a
								.getVars().get(0).getId());
						// 构造变量表达列表
						List<VariableDeclarator> VDL = new ArrayList<VariableDeclarator>();
						VDL.add(VD);
						// 变量类型获取
						Expr1.setType(a.getType());
						// 变量获取
						Expr1.setVars(VDL);

						// 定义赋值表达式构造
						// 赋值符号=
						Expr2.setOperator(japa.parser.ast.expr.AssignExpr.Operator.assign);
						// 赋值=左边获取
						Expr2.setTarget(new japa.parser.ast.expr.NameExpr(a
								.getVars().get(0).getId().getName()));
						// 赋值=右边获取
						Expr2.setValue(a.getVars().get(0).getInit());

						// 表达式行号设置
						Expr1.setBeginLine(n.getBeginLine());
						Expr1.setEndLine(n.getEndLine());
						Expr2.setBeginLine(n.getBeginLine());
						Expr2.setEndLine(n.getEndLine());

						// 表达式放进Stmt中
						ExpressionStmt EStmt1 = new ExpressionStmt(Expr1);
						ExpressionStmt EStmt2 = new ExpressionStmt(Expr2);
						// 递归visit模式调用
						EStmt1.accept(this, arg);
						EStmt2.accept(this, arg);
						return null;
					}

				}
			}
		}

		if (isConditionalExpr)
		{
			// 提取条件
			// 此处只照顾到条件语句中有一层括号或没有括号的情况，若后来人可以通过通用语句满足所有情况，欢迎改正
			if (cExpr.getCondition().getClass().equals(EnclosedExpr.class))
			{
				EnclosedExpr enExpr = (EnclosedExpr) cExpr.getCondition();
				if (enExpr != null)
				{
					bExpr = (BinaryExpr) enExpr.getInner();
				}
			}
			else if (cExpr.getCondition().getClass().equals(BinaryExpr.class))
			{
				bExpr = (BinaryExpr) cExpr.getCondition();
			}

			parents.push(n);
			// 条件开始, 初始化状态
			// logic.setIsInCondition(false);
			printer.print("if (");
			ConditionTreeNode node = visitCondition(bExpr, false);
			printer.print(") ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// 第一节点标号处理
			logic.getGraph().getById(lastGraphSize).setBeginLine(lastEndline);

			// 设置第一子节点
			logic.createFirstChild(n, n.getBeginLine());
			logic.getGraph().getLast()
					.setBeginLine(n.getExpression().getBeginLine());
			logic.getGraph().getLast()
					.setEndLine(n.getExpression().getEndLine());
			// logic.getGraph().getLast().setEndLine(1);

			// 设置后续节点连接
			logic.getGraph().getLast()
					.setFirstId(logic.getGraph().getLast().getId() + 2);

			// ZY
			// 获取赋值表达式
			AssignExpr aExpr = (AssignExpr) expression;
			// 组合赋值表达式为Then中内容
			aExpr.setValue(cExpr.getThenExpr());
			// 将此表达式放到Then中
			CThenStmt.setExpression(aExpr);
			// 访问thenStmt
			visitStmt(CThenStmt, arg);
			//

			// 第二子节点
			printer.print(" else ");
			logic.createSecondChild(n, n.getBeginLine());
			logic.getGraph().getLast()
					.setBeginLine(n.getExpression().getBeginLine());
			logic.getGraph().getLast()
					.setEndLine(n.getExpression().getEndLine());
			// logic.getGraph().getLast().setEndLine(2);

			// 后续连接
			logic.getGraph().getLast()
					.setFirstId(logic.getGraph().getLast().getId() + 1);

			// ZY
			// 组合赋值表达式为Else中内容
			aExpr.setValue(cExpr.getElseExpr());
			// 将此表达式放到Else中
			CElseStmt.setExpression(aExpr);
			// 访问ElseStmt
			visitStmt(CElseStmt, arg);
			// logic.SetLastStumpFlushed(true);
			//

			// if(!logic.getGraph().getById(first.getId()).hasNext()){
			// logic.pushNextChild(first);
			// }

			logic.removeConditions();
			parents.pop();
		}
		else
		{
			// 对于不是三目运算符的表达式，直接输出语句
			printer.print(n.toString());
		}
		// }
		// catch (Exception e)
		// {
		// //
		// }
		// finally
		// {
		return null;
		// }
	}

	@Override
	public Object visit(IfStmt n, Object arg)
	{

		if (isInTargetMethod())
		{
			parents.push(n);
			// 条件开始, 初始化状态
			// logic.setIsInCondition(false);
			printer.print("if (");
			ConditionTreeNode node = visitCondition(n.getCondition(), false);
			printer.print(") ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			logic.createFirstChild(n.getThenStmt(), n.getBeginLine());
			visitStmt(n.getThenStmt(), arg);
			if (n.getElseStmt() != null)
			{
				printer.print(" else ");
				logic.createSecondChild(n.getElseStmt(), n.getBeginLine());
				visitStmt(n.getElseStmt(), arg);
				// if(!logic.getGraph().getById(first.getId()).hasNext()){
				// logic.pushNextChild(first);
				// }
			}
			else
			{
				logic.pushSecondChild();
			}
			logic.removeConditions();
			parents.pop();
			return null;
		}
		else
			return super.visit(n, arg);
	}

	/**
	 * For根节点
	 * */
	public Object visit(ForStmt n, Object arg)
	{

		if (isInTargetMethod())
		{
			parents.push(n);
			printer.print("for (");
			visitForStmtHeaderInit(n, arg);

			// 处理条件
			ConditionTreeNode node = this.visitCondition(n.getCompare(), true);
			printer.print("; ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// ====结束条件处理
			visitForStmtHeaderUpdate(n, arg);
			printer.print(") ");
			// logic.pushSecondChild();
			logic.createFirstChild(n.getBody(), n.getBeginLine());
			visitStmt(n.getBody(), arg);
			// Log.Debug("First ID: " + node.getFirstId());
			logic.pushSecondChild(true, node.getFirstId());

			logic.removeConditions();
			parents.pop();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	/**
	 * While根节点
	 * */
	@Override
	public Object visit(WhileStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			parents.push(n);
			// 访问while头部信息
			printer.print("while (");
			ConditionTreeNode node = this
					.visitCondition(n.getCondition(), true);
			printer.print(") ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// ====结束条件处理

			// 第一条路径
			logic.createFirstChild(n.getBody(), n.getBeginLine());
			// 访问while的Body
			visitStmt(n.getBody(), arg);
			logic.pushSecondChild(true, node.getFirstId());
			// logic.endLoop(loopId);
			logic.removeConditions();
			parents.pop();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}

	}

	public Object visit(DoStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			parents.push(n);
			Stump first = logic.createRoot(n, false, n.getBeginLine());

			printer.print("do ");
			this.visitStmt(n.getBody(), arg);
			printer.print(" while (");
			ConditionTreeNode node = this
					.visitCondition(n.getCondition(), true);
			printer.print("));");
			logic.attachCondition(node);
			logic.attachFirstChild(first);
			logic.pushSecondChild(true, node.getFirstId());
			logic.removeConditions();
			parents.pop();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	public Object visit(SwitchStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			parents.push(n);

			Expression left = n.getSelector();

			if (n.getEntries() != null)
			{
				printer.indent();

				ArrayList<IfStmt> ifList = new ArrayList<IfStmt>();
				List<SwitchEntryStmt> switchList = n.getEntries();
				boolean breakFlag[] = new boolean[switchList.size()];

				for (int i = 0; i < switchList.size(); i++)
				{
					for (int j = 0; j < switchList.get(i).getStmts().size(); j++)
					{
						if (switchList.get(i).getStmts().get(j).getClass() == BreakStmt.class)
						{
							breakFlag[i] = true;
						}
						else
						{
							breakFlag[i] = false;
						}
					}
				}

				for (int k = 0; k < switchList.size(); k++)
				{
					System.out.println(breakFlag[k]);
				}

				for (int i = 0; i < switchList.size(); i++)
				{
					if (!breakFlag[i])
					{
						List<Statement> stmts = switchList.get(i).getStmts();
						for (int j = i + 1; j < switchList.size(); j++)
						{
							if (!breakFlag[j])
							{
								stmts.addAll(switchList.get(j).getStmts());
							}
							else
							{
								stmts.addAll(switchList.get(j).getStmts());
								break;
							}
						}
						switchList.get(i).setStmts(stmts);
					}
					List<Statement> stmtsWithoutBreak = switchList.get(i)
							.getStmts();
					for (int k = 0; k < stmtsWithoutBreak.size(); k++)
					{
						if (stmtsWithoutBreak.get(k).getClass() == BreakStmt.class)
						{
							stmtsWithoutBreak.remove(k);
						}
					}
					switchList.get(i).setStmts(stmtsWithoutBreak);
				}

				Expression right = null;
				for (int i = switchList.size() - 2; i >= 0; i--)
				{
					right = switchList.get(i).getLabel();
					BinaryExpr bexp = new BinaryExpr(left, right,
							Operator.notEquals);
					if (i == switchList.size() - 2)
					{
						IfStmt ifEndStmt = new IfStmt(bexp,
								switchList.get(switchList.size() - 1),
								switchList.get(switchList.size() - 2));
						ifList.add(ifEndStmt);
					}
					else
					{
						IfStmt ifStmt = new IfStmt(bexp, ifList.get(switchList
								.size() - i - 3), switchList.get(i));
						ifList.add(ifStmt);
					}
				}

				ifList.get(ifList.size() - 1).accept(this, arg);
				for (int i = 0; i < switchList.size() - 1; i++)
				{
					logic.getGraph()
							.getById(i)
							.setBeginLine(
									switchList.get(i).getLabel().getBeginLine());
					logic.getGraph()
							.getById(i)
							.setEndLine(
									switchList.get(i).getLabel().getEndLine());
				}

				for (int i = 0; i < switchList.size(); i++)
				{
					logic.getGraph()
							.getById(i + switchList.size() - 1)
							.setBeginLine(
									switchList.get(switchList.size() - 1 - i)
											.getStmts().get(0).getBeginLine());
					logic.getGraph()
							.getById(i + switchList.size() - 1)
							.setEndLine(
									switchList
											.get(switchList.size() - 1 - i)
											.getStmts()
											.get(switchList
													.get(switchList.size() - 1
															- i).getStmts()
													.size() - 1).getEndLine());
				}

				printer.unindent();
			}

			parents.pop();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	public Object visit(SwitchEntryStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			if (n.getLabel() != null)
			{
				// n.getLabel().accept(this, arg);
			}
			else
			{
			}
			printer.indent();
			if (n.getStmts() != null)
			{
				for (Statement s : n.getStmts())
				{
					s.accept(this, arg);
				}
				// flushStump(n);
			}
			printer.unindent();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	/**
	 * Return根节点
	 * */
	@Override
	public Object visit(ReturnStmt n, Object arg)
	{
		if (this.isInTargetMethod())
		{
			Stump stump = logic.createEndStump(n, n.getBeginLine());
			printer.println(this.getStumpCode(stump) + ";");
		}
		return super.visit(n, arg);
	}

	/**
	 * Break根节点
	 * */
	@Override
	public Object visit(BreakStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			Stump stump = logic.createEndStump(n, n.getBeginLine());

			if (!stump.isFlushed())
			{
				printer.println(this.getStumpCode(stump) + ";");
			}
			else
			{
				outputError(n);
			}

		}

		return super.visit(n, arg);
	}

	/**
	 * Continue根节点
	 * */
	@Override
	public Object visit(ContinueStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			/*
			 * Stump stump = logic.getContinue(n); if(!stump.isFlushed()){
			 * printer.println(this.getStumpCode(stump) + ";"); } else {
			 * outputError(n); }
			 */
		}
		return super.visit(n, arg);
	}

	/**
	 * Throw根节点
	 * */
	@Override
	public Object visit(ThrowStmt n, Object arg)
	{
		/*
		 * Stump stump = logic.getEndStump(n); if (!stump.isFlushed()) {
		 * printer.println(this.getStumpCode(stump) + ";"); }
		 */
		return super.visit(n, arg);
	}

	// public Object visit(TryStmt n, Object arg)
	// {
	// // if (isInTargetMethod()) {
	// // parents.push(n);
	// //
	// // Stump root = logic.createRoot(n, false);
	// // printer.println(this.getStumpCode(root) + ";");
	// //
	// // logic.enterTry();
	// // printer.print("try ");
	// // n.getTryBlock().accept(this, arg);
	// //
	// // //Try 结束，开始 Catch
	// // logic.enterCatch();
	// // Stump last = logic.getLast(n);
	// // ArrayList<Stump> catchs = new ArrayList<Stump>();
	// // if (n.getCatchs() != null) {
	// // for (CatchClause c : n.getCatchs()) {
	// // logic.meetCatch(logic.createRoot(c, false).getId());
	// // c.accept(this, arg);
	// // catchs.add(logic.getLast(n));
	// // }
	// // }
	// //
	// //
	// // if (n.getFinallyBlock() != null) {
	// //
	// // printer.print(" finally ");
	// // n.getFinallyBlock().accept(this, arg);
	// // }
	// //
	// // logic.existCatch();
	// // parents.pop();
	// // logic.existTry();
	// // return null;
	// //
	// // } else {
	// return super.visit(n, arg);
	// // }
	// }
	//
	// public Object visit(CatchClause n, Object arg)
	// {
	// if (isInTargetMethod())
	// {
	// printer.print(" catch (");
	// n.getExcept().accept(this, arg);
	// printer.print(") ");
	//
	// n.getCatchBlock().accept(this, arg);
	// return null;
	// }
	// else
	// {
	// return super.visit(n, arg);
	// }
	// }

	public Object visit(TryStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			parents.push(n);
			printer.print("try");
			n.getTryBlock().accept(this, arg);// 处理try语句块里的内容

			if (n.getCatchs() != null)
			{
				// Log.Debug("catch.!!!!!!!!!!");
				// 记录catch前的节点id
				if (logic.getGraph().getLast() != null)
				{
					// Log.Debug("Graph is not empty.!!!!!!!!!!");
					lastnodeid = logic.getGraph().getLast().getId();
				}
				// 依次处理每个catch
				for (CatchClause c : n.getCatchs())
				{

					Stump cat = logic.createRoot(c, false, c.getBeginLine());
					catchnodeid.add(cat.getId());// 储存catch点的id，便于后面处理finally时访问
					// 将这个catch节点和前面的点连起来
					// Log.Debug("catch3333.!!!!!!!!!!");
					logic.attachPreNext(logic.getGraph().getLast().getId() - 1,
							cat.getId());
					logic.getGraph().getById(cat.getId())
							.setBeginLine(c.getBeginLine());
					logic.getGraph().getById(cat.getId())
							.setEndLine(c.getEndLine());

					// 处理catch里的语句块
					c.accept(this, arg);
				}
			}
			if (n.getFinallyBlock() != null)
			{
				printer.print(" finally ");

				Stump fin = logic.createRoot(n.getFinallyBlock(), false, n
						.getFinallyBlock().getBeginLine());
				// 和前面每一个catch分支建立联系
				for (int i = 0; i < catchnodeid.size(); i++)
				{
					logic.attachPreNext(catchnodeid.get(i), fin.getId());
				}
				// 和前面try模块的点建立联系
				logic.attachPreNext(lastnodeid, fin.getId());
				if (lastnodeid - 1 >= 0)
					logic.attachPreNext(lastnodeid - 1, fin.getId());
				// 设置finally点
				logic.getGraph().getById(fin.getId())
						.setBeginLine(n.getFinallyBlock().getBeginLine());
				logic.getGraph().getById(fin.getId())
						.setEndLine(n.getFinallyBlock().getEndLine());
				// 处理finally语句块内容
				n.getFinallyBlock().accept(this, arg);
			}
			parents.pop();
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	public Object visit(CatchClause n, Object arg)
	{
		if (isInTargetMethod())
		{
			printer.print(" catch (");
			n.getExcept().accept(this, arg);
			printer.print(") ");

			n.getCatchBlock().accept(this, arg);
			return null;
		}
		else
		{
			return super.visit(n, arg);
		}
	}

	/**
	 * Block根节点
	 * */
	@Override
	public Object visit(BlockStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			printer.println("{");
			if (n.getStmts() != null)
			{
				printer.indent();

				List<Statement> list = n.getStmts();
				for (int i = 0; i < list.size(); i++)
				{
					Statement s = list.get(i);
					// if(logic.isInTry()){
					// if(!hasClass.contains(s.getClass())){
					// Stump stump = logic.createTryStump(s);
					// printer.println(this.getStumpCode(stump) + ";");
					// }
					// }
					s.accept(this, arg);
					printer.println();
				}

				// if(!logic.isInTry()){
				flushStump(n);
				// } else {
				// Stump stump = logic.createRoot(n, false);
				// printer.printLn(this.getStumpCode(stump) + ";");
				// }

				// for (Statement s : n.getStmts()) {
				// if(!hasClass.contains(s.getClass())){
				// Stump stump = logic.createTryStump(s);
				// printer.printLn(this.getStumpCode(stump) + ";");
				//
				// }
				// s.accept(this, arg);
				// printer.printLn();
				// }
				// //flushStump(n);
				// //if(!hasClass.contains(s.getClass())){
				//
				// //}

				printer.unindent();
			}

			printer.print("}");
			return null;
		}
		else
			return super.visit(n, arg);
	}

	protected ConditionTreeNode visitCondition(Expression expr, boolean isLoop)
	{
		// System.out.println("debug: " + exp.getClass().toString() +":"+
		// BinaryExpr.class.toString());
		if (expr.getClass() == BinaryExpr.class)
		{
			BinaryExpr bexp = (BinaryExpr) expr;
			if (bexp.getOperator() == Operator.and
					|| bexp.getOperator() == Operator.or)
			{
				// 构造一个ConditionTreeNode根节点
				ConditionTreeNode root = new ConditionTreeNode();
				// 设置左边的节点
				ConditionTreeNode left = visitCondition(bexp.getLeft(), isLoop);
				root.setLeft(left);
				if (bexp.getOperator() == Operator.and)
				{
					printer.print(" && ");
					root.setOperator(OperatorType.And);
				}
				else
				{
					printer.print(" || ");
					root.setOperator(OperatorType.Or);
				}
				// 设置右边的节点
				root.setRight(visitCondition(bexp.getRight(), isLoop));
				return root;
			}
			else
			{
				int id = flushExpression(bexp, isLoop);
				return new ConditionTreeNode(bexp.toString(), id);
			}
		}
		else if (expr.getClass() == EnclosedExpr.class)
		{
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
		else
		{
			int id = flushExpression(expr, isLoop);
			return new ConditionTreeNode(expr.toString(), id);
		}

	}

	protected int visitCondition(Expression expr, boolean isLoop,
			boolean isContinue)
	{
		int id = 0;
		if (expr.getClass() == BinaryExpr.class)
		{
			BinaryExpr bexp = (BinaryExpr) expr;

			if (bexp.getOperator() == Operator.and
					|| bexp.getOperator() == Operator.or)
			{

				if (bexp.getOperator() == Operator.and)
				{
					id += visitCondition(bexp.getLeft(), isLoop, true);
					printer.print(" && ");
				}
				else
				{
					id += visitCondition(bexp.getLeft(), isLoop,
							true && isContinue);
					printer.print(" || ");
				}
				id += visitCondition(bexp.getRight(), isLoop,
						true && isContinue);

				return id;
			}
			else
			{
				id += flushExpression(bexp, isLoop);
				return id;
			}
		}
		else if (expr.getClass() == EnclosedExpr.class)
		{
			EnclosedExpr enExp = (EnclosedExpr) expr;

			// 打印左括号
			printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
			id += visitCondition(enExp.getInner(), isLoop, isContinue);
			// 打印右括号
			printer.print(ConstText.SPACE + ConstText.BRACKET_RIGHT);
			return id;
		}
		else
		{
			id += flushExpression(expr, isLoop);
			return id;
		}
	}

	protected int flushExpression(Expression expr, boolean isLoop)
	{
		// Log.Debug(expr.toString() + "-" + isContinue);
		Stump stump = null;
		int id = 0;
		// false 表示第一次进入原子条件
		if (logic.isInCondition() == false)
		{
			logic.setIsInCondition(true);
			stump = logic.createRoot(expr, isLoop, expr.getBeginLine());
			// Log.Debug("STUMP ID:" + stump.getId());
			id = stump.getId();
		}
		else
		{
			stump = logic.createNext(expr, expr.getBeginLine());
			id = stump.getId();
			// Log.Debug("STUMP ID:" + stump.getId());
		}
		// 输出桩信息和表达式信息
		printer.print(ConstText.BRACKET_LEFT + this.getStumpCode(stump)
				+ " && " + expr.toString() + ConstText.BRACKET_RIGHT);
		return id;
	}

	/**
	 * Statement根节点 , 对所有的Statement都加上{}
	 * */
	protected Object visitStmt(Statement stmt, Object arg)
	{

		if (stmt.getClass() != BlockStmt.class)
		{
			printer.print(" {");
			printer.indent();
			printer.println();
		}

		stmt.accept(this, arg);

		if (stmt.getClass() != BlockStmt.class)
		{
			flushStump(stmt);
			printer.println();
			printer.unindent();
			printer.print("}");
		}
		return null;
	}

	protected void flushStump(Node n)
	{
		Stump stump = null;
		if (parents.size() > 0
				&& parents.peek().getClass() == MethodDeclaration.class
				&& logic.getGraph().getLast() != null
				&& !logic.getGraph().getLast().isEnd())
		{
			stump = logic.createEndStump(n, n.getBeginLine());
		}
		else
		{
			stump = logic.getLastStump(n);
		}

		if (stump != null && !stump.isFlushed())
		{
			if (n.getClass() != BlockStmt.class)
				printer.println();
			logic.pushNeedChild(stump.getId());
			printer.println(this.getStumpCode(stump) + ";");
		}
	}

	public void outputError(Node n)
	{
		printer.println("//Control flow node conflict at line: "
				+ n.getBeginLine() + " column: " + n.getBeginColumn());
	}

}
