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

//�Ķ��
//����lastnodeid��catchnodeid�Ķ����catchnodeid�ĳ�ʼ��
//�Ľ�public Object visit(ExpressionStmt n, Object arg)����Ӧ��Ŀ�������TEAM 1��
//�Ľ�public Object visit(SwitchStmt n, Object arg)��public Object visit(SwitchEntryStmt n, Object arg)����Ӧswitch��TEAM 3��
//�Ľ�public Object visit(TryStmt n, Object arg)��public Object visit(CatchClause n, Object arg)����Ӧtrycatch��TEAM 3�� ���⣺try��������нṹ�ڵ���ܳɹ�����ֻ��˳����䣬�����Ϊ�޷����ɽڵ��������ͼ���ɳ���
//trycatch�Ĳ�׮���ܿ���ʵ�֣�����·�����Ի�δ��ʵ��
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
		 * �ж��Ƿ���Ŀ����� Ŀǰ����ʵ����ͨ��ֵ���ʽ�Ͷ���+��ֵ����Ŀ�����ʶ��
		 */
		// try
		// {
		// flag
		boolean isConditionalExpr = false;
		// ��ȡn�еı��ʽ
		Expression expression = n.getExpression();
		// �������ʽ
		ConditionalExpr cExpr = null;
		// �������ʽ�е�����
		BinaryExpr bExpr = null;
		// ��¼��һ�ڵ��endline
		int lastEndline = (logic.getGraph().size() == 0) ? 1 : logic.getGraph()
				.getLast().getEndline() + 1;
		int lastGraphSize = logic.getGraph().size();

		// ZY
		// ����Then��Else��Stmt
		ExpressionStmt CThenStmt = new ExpressionStmt();
		ExpressionStmt CElseStmt = new ExpressionStmt();

		// �˴�ֻ�չ˵���ֵ�붨��+��ֵ������������˿���ͨ��ͨ��������������������ӭ����
		if (expression != null)
		{
			// �����ʽΪ��ֵ���ʽ
			if (expression.getClass().equals(AssignExpr.class))
			{
				AssignExpr a = (AssignExpr) expression;
				if (a.getValue() != null)
				{
					// ����ֵ���ʽ��һ��Ϊ�������ʽ
					if (a.getValue().getClass().equals(ConditionalExpr.class))
					{
						isConditionalExpr = true;
						cExpr = (ConditionalExpr) a.getValue();
					}
				}
			}
			// �����ʽΪ�����������ʽ����ȡΪ��ֵ����ͨ�������ʽ
			else if (expression.getClass()
					.equals(VariableDeclarationExpr.class))
			{
				VariableDeclarationExpr a = (VariableDeclarationExpr) expression;
				// ��ȡ�������ʽ
				Expression e = a.getVars().get(0).getInit();
				if (e != null)
				{
					// ��Ϊ�������ʽ
					if (e.getClass().equals(ConditionalExpr.class))
					{
						/*
						 * ��ȡΪ��ֵ����ͨ�������ʽ
						 */

						// ��������������ʽ
						// ���帳ֵ���ʽ
						VariableDeclarationExpr Expr1 = new VariableDeclarationExpr();
						AssignExpr Expr2 = new AssignExpr();

						// �����������ʽ����
						// �������
						VariableDeclarator VD = new VariableDeclarator(a
								.getVars().get(0).getId());
						// �����������б�
						List<VariableDeclarator> VDL = new ArrayList<VariableDeclarator>();
						VDL.add(VD);
						// �������ͻ�ȡ
						Expr1.setType(a.getType());
						// ������ȡ
						Expr1.setVars(VDL);

						// ���帳ֵ���ʽ����
						// ��ֵ����=
						Expr2.setOperator(japa.parser.ast.expr.AssignExpr.Operator.assign);
						// ��ֵ=��߻�ȡ
						Expr2.setTarget(new japa.parser.ast.expr.NameExpr(a
								.getVars().get(0).getId().getName()));
						// ��ֵ=�ұ߻�ȡ
						Expr2.setValue(a.getVars().get(0).getInit());

						// ���ʽ�к�����
						Expr1.setBeginLine(n.getBeginLine());
						Expr1.setEndLine(n.getEndLine());
						Expr2.setBeginLine(n.getBeginLine());
						Expr2.setEndLine(n.getEndLine());

						// ���ʽ�Ž�Stmt��
						ExpressionStmt EStmt1 = new ExpressionStmt(Expr1);
						ExpressionStmt EStmt2 = new ExpressionStmt(Expr2);
						// �ݹ�visitģʽ����
						EStmt1.accept(this, arg);
						EStmt2.accept(this, arg);
						return null;
					}

				}
			}
		}

		if (isConditionalExpr)
		{
			// ��ȡ����
			// �˴�ֻ�չ˵������������һ�����Ż�û�����ŵ�������������˿���ͨ��ͨ��������������������ӭ����
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
			// ������ʼ, ��ʼ��״̬
			// logic.setIsInCondition(false);
			printer.print("if (");
			ConditionTreeNode node = visitCondition(bExpr, false);
			printer.print(") ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// ��һ�ڵ��Ŵ���
			logic.getGraph().getById(lastGraphSize).setBeginLine(lastEndline);

			// ���õ�һ�ӽڵ�
			logic.createFirstChild(n, n.getBeginLine());
			logic.getGraph().getLast()
					.setBeginLine(n.getExpression().getBeginLine());
			logic.getGraph().getLast()
					.setEndLine(n.getExpression().getEndLine());
			// logic.getGraph().getLast().setEndLine(1);

			// ���ú����ڵ�����
			logic.getGraph().getLast()
					.setFirstId(logic.getGraph().getLast().getId() + 2);

			// ZY
			// ��ȡ��ֵ���ʽ
			AssignExpr aExpr = (AssignExpr) expression;
			// ��ϸ�ֵ���ʽΪThen������
			aExpr.setValue(cExpr.getThenExpr());
			// ���˱��ʽ�ŵ�Then��
			CThenStmt.setExpression(aExpr);
			// ����thenStmt
			visitStmt(CThenStmt, arg);
			//

			// �ڶ��ӽڵ�
			printer.print(" else ");
			logic.createSecondChild(n, n.getBeginLine());
			logic.getGraph().getLast()
					.setBeginLine(n.getExpression().getBeginLine());
			logic.getGraph().getLast()
					.setEndLine(n.getExpression().getEndLine());
			// logic.getGraph().getLast().setEndLine(2);

			// ��������
			logic.getGraph().getLast()
					.setFirstId(logic.getGraph().getLast().getId() + 1);

			// ZY
			// ��ϸ�ֵ���ʽΪElse������
			aExpr.setValue(cExpr.getElseExpr());
			// ���˱��ʽ�ŵ�Else��
			CElseStmt.setExpression(aExpr);
			// ����ElseStmt
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
			// ���ڲ�����Ŀ������ı��ʽ��ֱ��������
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
			// ������ʼ, ��ʼ��״̬
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
	 * For���ڵ�
	 * */
	public Object visit(ForStmt n, Object arg)
	{

		if (isInTargetMethod())
		{
			parents.push(n);
			printer.print("for (");
			visitForStmtHeaderInit(n, arg);

			// ��������
			ConditionTreeNode node = this.visitCondition(n.getCompare(), true);
			printer.print("; ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// ====������������
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
	 * While���ڵ�
	 * */
	@Override
	public Object visit(WhileStmt n, Object arg)
	{
		if (isInTargetMethod())
		{
			parents.push(n);
			// ����whileͷ����Ϣ
			printer.print("while (");
			ConditionTreeNode node = this
					.visitCondition(n.getCondition(), true);
			printer.print(") ");
			logic.attachCondition(node);
			// logic.setIsInCondition(false);
			// ====������������

			// ��һ��·��
			logic.createFirstChild(n.getBody(), n.getBeginLine());
			// ����while��Body
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
	 * Return���ڵ�
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
	 * Break���ڵ�
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
	 * Continue���ڵ�
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
	 * Throw���ڵ�
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
	// // //Try ��������ʼ Catch
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
			n.getTryBlock().accept(this, arg);// ����try�����������

			if (n.getCatchs() != null)
			{
				// Log.Debug("catch.!!!!!!!!!!");
				// ��¼catchǰ�Ľڵ�id
				if (logic.getGraph().getLast() != null)
				{
					// Log.Debug("Graph is not empty.!!!!!!!!!!");
					lastnodeid = logic.getGraph().getLast().getId();
				}
				// ���δ���ÿ��catch
				for (CatchClause c : n.getCatchs())
				{

					Stump cat = logic.createRoot(c, false, c.getBeginLine());
					catchnodeid.add(cat.getId());// ����catch���id�����ں��洦��finallyʱ����
					// �����catch�ڵ��ǰ��ĵ�������
					// Log.Debug("catch3333.!!!!!!!!!!");
					logic.attachPreNext(logic.getGraph().getLast().getId() - 1,
							cat.getId());
					logic.getGraph().getById(cat.getId())
							.setBeginLine(c.getBeginLine());
					logic.getGraph().getById(cat.getId())
							.setEndLine(c.getEndLine());

					// ����catch�������
					c.accept(this, arg);
				}
			}
			if (n.getFinallyBlock() != null)
			{
				printer.print(" finally ");

				Stump fin = logic.createRoot(n.getFinallyBlock(), false, n
						.getFinallyBlock().getBeginLine());
				// ��ǰ��ÿһ��catch��֧������ϵ
				for (int i = 0; i < catchnodeid.size(); i++)
				{
					logic.attachPreNext(catchnodeid.get(i), fin.getId());
				}
				// ��ǰ��tryģ��ĵ㽨����ϵ
				logic.attachPreNext(lastnodeid, fin.getId());
				if (lastnodeid - 1 >= 0)
					logic.attachPreNext(lastnodeid - 1, fin.getId());
				// ����finally��
				logic.getGraph().getById(fin.getId())
						.setBeginLine(n.getFinallyBlock().getBeginLine());
				logic.getGraph().getById(fin.getId())
						.setEndLine(n.getFinallyBlock().getEndLine());
				// ����finally��������
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
	 * Block���ڵ�
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
				// ����һ��ConditionTreeNode���ڵ�
				ConditionTreeNode root = new ConditionTreeNode();
				// ������ߵĽڵ�
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
				// �����ұߵĽڵ�
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
			// ����һ��ֻ����ڵ�Ľڵ�
			ConditionTreeNode root = new ConditionTreeNode(OperatorType.Bracket);
			EnclosedExpr enExp = (EnclosedExpr) expr;
			// ��ӡ������
			printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
			// ������ߵĽڵ�
			root.setLeft(visitCondition(enExp.getInner(), isLoop));
			// ��ӡ������
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

			// ��ӡ������
			printer.print(ConstText.BRACKET_LEFT + ConstText.SPACE);
			id += visitCondition(enExp.getInner(), isLoop, isContinue);
			// ��ӡ������
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
		// false ��ʾ��һ�ν���ԭ������
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
		// ���׮��Ϣ�ͱ��ʽ��Ϣ
		printer.print(ConstText.BRACKET_LEFT + this.getStumpCode(stump)
				+ " && " + expr.toString() + ConstText.BRACKET_RIGHT);
		return id;
	}

	/**
	 * Statement���ڵ� , �����е�Statement������{}
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
