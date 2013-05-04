package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import cn.edu.pku.ss.test.condition.ConditionTreeNode;
import cn.edu.pku.ss.test.inject.IStumpManager;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.util.Log;

public class BPControlLogic
{

	protected IStumpManager				manager			= null;

	protected Class<? extends Stump>	stumpClass		= null;
	protected FlowGraph					graph			= null;
	protected Stack<BPStack>			logicStack		= null;
	protected BPStack					cStack			= null;
	// protected ArrayList<BPCondition> array = null;
	protected boolean					isInCondition	= false;

	// 获取BP控制流图
	public FlowGraph getGraph()
	{
		return graph;
	}

	// 判断是不是在条件之中
	public boolean isInCondition()
	{
		return isInCondition;
	}

	// 设定条件的开始和结束
	public void setIsInCondition(boolean isIn)
	{
		this.isInCondition = isIn;
	}

	// protected Stack<ArrayList<Integer>> endTryStmts = null;
	public BPControlLogic(IStumpManager manager, Class<? extends Stump> stumpClass)
	{
		this.manager = manager;
		this.stumpClass = stumpClass;
		graph = new FlowGraph();
		// 逻辑栈
		logicStack = new Stack<BPStack>();
		// 初始化操作,加入一个逻辑栈
		cStack = new BPStack();
		logicStack.push(cStack);
		// array = new ArrayList<BPCondition>();

	}

	public void SetLastStumpFlushed(boolean t)
	{
		cStack.getLastStump().setFlushed(t);
	}

	/**
	 * 新建一个根节点
	 * */
	public Stump createRoot(Object node, boolean isLoop, int lineNum)
	{
		Stump root = null;
		cStack.newPushConditions();
		// 首先要判断是否需要创建一个全新的节点,某个if或者while内部的第一个节点不需要创建
		if (cStack.needCreateNew())
		{
			root = create(node, isLoop, lineNum);

		}
		else
		{
			root = cStack.getLastStump();
			root.setNode(node);
			graph.getById(root.getId()).setLoop(true);
			// Log.Debug("#$%^#$%^#$%^#$%^#$%");

		}
		ArrayList<BPCondition> array = cStack.getLastConditions();
		array.add(new BPCondition(root.getId()));
		cStack.setLastStump(root);
		cStack.attachChildren(root.getId(), graph);
		// 判断是不是循环,如果是循环,就要记录循环的第一个节点
		if (isLoop)
		{
			cStack.addLoop(root.getId());
		}

		return root;
	}

	/**
	 * 新建条件的后继节点
	 * */
	public Stump createNext(Object node, int lineNum)
	{
		// Changed by me```
		Stump next = create(node, false, lineNum);
		ArrayList<BPCondition> array = cStack.getLastConditions();
		array.add(new BPCondition(next.getId()));
		return next;
	}

	public void attachCondition(ConditionTreeNode n)
	{
		this.isInCondition = false;
		ArrayList<BPCondition> array = cStack.getLastConditions();
		if (n.getCount() == 1)
		{
			array.get(0).setThen(true);
			array.get(0).setElse(true);
		}
		else
		{

			ArrayList<String> ts = n.getTrueShortCuicus();
			System.out.println(ts);
			attachConditionSub(array, ts, true);
			ts = n.getFalseShortCuicus();
			System.out.println(ts);
			attachConditionSub(array, ts, false);
		}
	}

	private void attachConditionSub(ArrayList<BPCondition> array, ArrayList<String> ts, boolean isThen)
	{
		for (String s : ts)
		{
			Log.Debug(s);
			int i = 0;
			for (i = 0; i < s.length() - 1; i++)
			{
				GraphNode gn = graph.getById(array.get(i).getId());
				int start = i;
				// 处理连续的条件
				if (s.charAt(i + 1) != '-')
				{
					gn.setNext(array.get(i + 1).getId());
					Log.Debug("Attach1: " + gn.getId() + "->" + array.get(i + 1).getId());
					if (i + 1 == s.length() - 1)
					{

						array.get(i + 1).setThenElse(isThen);
						Log.Debug("Set Then1: " + array.get(i + 1).getId() + " value: " + isThen);
					}
					continue;
				}
				else
				{
					// 如果碰到'-', 表示
					while (i < s.length() - 1 && s.charAt(i + 1) == '-')
						i++;
					if (i == s.length() - 1)
					{
						if (s.charAt(i) != '-')
						{
							gn.setNext(array.get(i).getId());
							Log.Debug("Attach2: " + gn.getId() + "->" + array.get(i).getId());
							continue;
						}
						else
						{
							array.get(start).setThenElse(isThen);
							Log.Debug("Set Then2: " + array.get(start).getId() + " value: " + isThen);
							continue;
						}
					}
					else if (s.charAt(i + 1) != '-')
					{
						gn.setNext(array.get(i + 1).getId());
						Log.Debug("Attach3: " + gn.getId() + "->" + array.get(i + 1).getId());
						if (i + 1 == s.length() - 1)
						{
							array.get(i + 1).setThenElse(isThen);
							Log.Debug("Set Then3: " + array.get(i + 1).getId() + " value: " + isThen);
						}
						continue;
					}
					else
					{
						Log.Debug("ERROR!!!");
					}
				}

			}
		}
	}

	/**
	 * 创建第一个子节点
	 * */
	// Changed by me```
	public Stump createFirstChild(Object node, int lineNum)
	{
		// 首先创建一个节点
		Stump first = create(node, false, lineNum);
		attachFirstChild(first);
		cStack.setLastStump(first);
		return first;
	}

	/**
	 * 将所有Then节点的下一个节点绑定到frist
	 * */
	public void attachFirstChild(Stump first)
	{
		ArrayList<BPCondition> array = cStack.getLastConditions();
		for (int i = 0; i < array.size(); i++)
		{
			Log.Debug(array.get(i).getId() + " - " + array.get(i).isThen());
			if (array.get(i).isThen())
			{
				attachPreNext(array.get(i).getId(), first.getId());
			}
		}
	}

	/**
	 * 创建第二个子节点
	 * */
	public Stump createSecondChild(Object node, int lineNum)
	{
		// 首先创建一个节点
		Stump second = create(node, false, lineNum);

		ArrayList<BPCondition> array = cStack.getLastConditions();
		// 将所有Else节点的下一个节点绑定到frist
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).isElse())
			{
				attachPreNext(array.get(i).getId(), second.getId());
			}
		}
		// if(second.getId()==11){
		// Log.Debug("wocao成功");
		// }
		cStack.setLastStump(second);
		return second;
	}

	public void pushSecondChild()
	{
		pushSecondChild(false, -1);
	}

	/**
	 * 将缺少第二个子节点的节点入栈
	 * */
	public void pushSecondChild(boolean isLoop, int loopId)
	{
		ArrayList<BPCondition> array = cStack.getLastConditions();
		// 将所有Else节点的下一个节点入栈
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).isElse())
			{
				int id = array.get(i).getId();
				pushNeedChild(id);
				if (isLoop)
				{
					graph.getById(id).setLoopEnd(true);
					graph.getById(id).setLoopId(loopId);
				}
			}
		}
		if (isLoop)
		{
			cStack.attachLoop(loopId, graph);
		}

	}

	/**
	 * 创建结束节点
	 * */
	public Stump createEndStump(Object node, int lineNum)
	{
		Stump end = createRoot(node, false, lineNum);
		graph.getLast().setEnd(true);
		return end;
	}

	public Stump createAnyStump(Object node, int lineNum)
	{
		Stump end = createRoot(node, false, lineNum);
		graph.getLast().setEnd(false);
		return end;
	}

	/**
	 * 获取最后一个节点
	 * */
	public Stump getLastStump(Object n)
	{
		Stump s = cStack.getLastStump();
		s.setNode(n);
		return s;
	}

	// public void endLoop(int id){
	// cStack.attachLoop(id, graph);
	// }

	/**
	 * 移除一层条件节点
	 * */
	public void removeConditions()
	{
		cStack.removeConditions();
	}

	/**
	 * 记录没有后继节点的节点
	 * */
	public void pushNeedChild(int id)
	{
		cStack.pushSecond(id);
		Log.Debug("入栈:" + id);
	}

	/**
	 * 构造一个桩节点
	 * */
	protected Stump create(Object node, boolean isLoop, int lineNum)
	{
		Stump root = null;
		try
		{
			root = manager.create(node, stumpClass.newInstance());
			// Log.Debug("root id=" + root.getId());
			// Changed by me```
			graph.add(root.getId(), isLoop, lineNum);
			// root.setId(graph.getLast().getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.println(e.toString());
		}
		return root;
	}

	/**
	 * 关联两个节点
	 * */
	public void attachPreNext(int previous, int next)
	{
		graph.getById(previous).setNext(next);
	}

}
