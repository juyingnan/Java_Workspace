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

	// ��ȡBP������ͼ
	public FlowGraph getGraph()
	{
		return graph;
	}

	// �ж��ǲ���������֮��
	public boolean isInCondition()
	{
		return isInCondition;
	}

	// �趨�����Ŀ�ʼ�ͽ���
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
		// �߼�ջ
		logicStack = new Stack<BPStack>();
		// ��ʼ������,����һ���߼�ջ
		cStack = new BPStack();
		logicStack.push(cStack);
		// array = new ArrayList<BPCondition>();

	}

	public void SetLastStumpFlushed(boolean t)
	{
		cStack.getLastStump().setFlushed(t);
	}

	/**
	 * �½�һ�����ڵ�
	 * */
	public Stump createRoot(Object node, boolean isLoop, int lineNum)
	{
		Stump root = null;
		cStack.newPushConditions();
		// ����Ҫ�ж��Ƿ���Ҫ����һ��ȫ�µĽڵ�,ĳ��if����while�ڲ��ĵ�һ���ڵ㲻��Ҫ����
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
		// �ж��ǲ���ѭ��,�����ѭ��,��Ҫ��¼ѭ���ĵ�һ���ڵ�
		if (isLoop)
		{
			cStack.addLoop(root.getId());
		}

		return root;
	}

	/**
	 * �½������ĺ�̽ڵ�
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
				// ��������������
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
					// �������'-', ��ʾ
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
	 * ������һ���ӽڵ�
	 * */
	// Changed by me```
	public Stump createFirstChild(Object node, int lineNum)
	{
		// ���ȴ���һ���ڵ�
		Stump first = create(node, false, lineNum);
		attachFirstChild(first);
		cStack.setLastStump(first);
		return first;
	}

	/**
	 * ������Then�ڵ����һ���ڵ�󶨵�frist
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
	 * �����ڶ����ӽڵ�
	 * */
	public Stump createSecondChild(Object node, int lineNum)
	{
		// ���ȴ���һ���ڵ�
		Stump second = create(node, false, lineNum);

		ArrayList<BPCondition> array = cStack.getLastConditions();
		// ������Else�ڵ����һ���ڵ�󶨵�frist
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).isElse())
			{
				attachPreNext(array.get(i).getId(), second.getId());
			}
		}
		// if(second.getId()==11){
		// Log.Debug("wocao�ɹ�");
		// }
		cStack.setLastStump(second);
		return second;
	}

	public void pushSecondChild()
	{
		pushSecondChild(false, -1);
	}

	/**
	 * ��ȱ�ٵڶ����ӽڵ�Ľڵ���ջ
	 * */
	public void pushSecondChild(boolean isLoop, int loopId)
	{
		ArrayList<BPCondition> array = cStack.getLastConditions();
		// ������Else�ڵ����һ���ڵ���ջ
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
	 * ���������ڵ�
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
	 * ��ȡ���һ���ڵ�
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
	 * �Ƴ�һ�������ڵ�
	 * */
	public void removeConditions()
	{
		cStack.removeConditions();
	}

	/**
	 * ��¼û�к�̽ڵ�Ľڵ�
	 * */
	public void pushNeedChild(int id)
	{
		cStack.pushSecond(id);
		Log.Debug("��ջ:" + id);
	}

	/**
	 * ����һ��׮�ڵ�
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
	 * ���������ڵ�
	 * */
	public void attachPreNext(int previous, int next)
	{
		graph.getById(previous).setNext(next);
	}

}
