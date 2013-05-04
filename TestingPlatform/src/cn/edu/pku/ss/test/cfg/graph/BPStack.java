package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Stack;

import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.util.Log;

public class BPStack {
	/**
	 * ���ջ���ڱ�����������Then��ת�Ľڵ�
	 * */
	//protected Stack<Integer> firstChildStack = null;
	/**
	 * ���ջ���ڱ�����������Else��ת�Ľڵ�
	 * */
	protected Stack<Integer> secondChildStack = null;
	/**
	 * ���ջ���ڱ���ȱ�ٵ���һ����̽ڵ�Ľڵ�
	 * */
	protected Stack<Stack<Integer>> nextNode = null;
	/**
	 * ���ջ�ﱣ��ѭ���ĵ�һ���ڵ�
	 * */
	protected Stack<Integer> loopStack = null;
	/**
	 * ���ջ�ﱣ������Try�ڵ㵱�еĽڵ�
	 * */
	protected ArrayList<ArrayList<Integer>> tryStmts = null;
	/**
	 * ���ջ�ﱣ������Catch�ڵ㵱�еĽڵ�
	 * */
	protected ArrayList<ArrayList<Integer>> catchStmts = null;
	/**
	 * ������һ���ڵ�
	 * */
	protected Stump lastStump = null;
	
	protected ArrayList<ArrayList<BPCondition>> conditions = null;
	
	public BPStack(){
		//firstChildStack = new Stack<Integer>();
		secondChildStack = new Stack<Integer>();
		nextNode = new Stack<Stack<Integer>>();
		loopStack = new Stack<Integer>();
		tryStmts = new ArrayList<ArrayList<Integer>>();
		catchStmts = new ArrayList<ArrayList<Integer>>();
		conditions = new ArrayList<ArrayList<BPCondition>>();
	}
	
	/**
	 * ����Ƿ���Ҫ����һ���ڵ�
	 * */
	public boolean needCreateNew(){
		return lastStump==null || lastStump.isFlushed();
	}
	
	/**
	 * �趨��һ���ڵ�
	 * */
	public void setLastStump(Stump stump){
		lastStump = stump;	
		if(stump == null){
			Log.Debug("mabi");
		}
	}
	
	/**
	 * ��ȡ��һ���ڵ�
	 * */
	public Stump getLastStump() {
		return lastStump;
	}
	
	/**
	 * ����һ������
	 * */
	public void newPushConditions(){
		conditions.add(new ArrayList<BPCondition>());
	}
	
	/**
	 * ���ӵ�������
	 * */
	public void pushCondition(BPCondition c){
		conditions.get(conditions.size()-1).add(c);
	}
	
	/**
	 * ��ȡһ������
	 * */
	public ArrayList<BPCondition> getLastConditions(){
		return conditions.get(conditions.size()-1);
	}
	
	/**
	 * �Ƴ�һ������
	 * */
	public void removeConditions() {
		conditions.remove(conditions.size()-1);
		
	}
	
	/**
	 * ����һ��ѭ���Ŀ�ʼ��
	 * */
	public void addLoop(int id) {
		this.loopStack.push(id);
	}

	/**
	 * ����ѭ���Ľ����ڵ�Ϳ�ʼ�ڵ�
	 * */
	public void attachLoop(int loopId, FlowGraph graph) {
		
		Log.Debug("attachLoop : " + loopId);
		
		//�����ڵ������2��, �����˳���ѭ���ж��
		GraphNode loopEnd1 = graph.getLast();
		fixLoopEnd(loopEnd1,loopId, graph);
		
		
		//Log.Debug("loopEnd1=" + loopEnd1);
		GraphNode loopEnd2 = null;
		//�����������ѭ����Ƕ����if(){}else{}
		//����ѭ�������ڵ��ص�Ϊ hasNext = false, ��û���κκ�̽ڵ�
		//Switch case �������Ϊ����,Ҳ����ÿ���
		//Switch case ���������ֹ����LoopEnd, ÿһ��case����һ��LoopEnd
		//BreakҲ�������⴦��
		//��Ϊʱ��ִ�,��ʱ��������Щ�����
		if(graph.size()>1 && 
				!graph.getLast2().hasNext()){
			loopEnd2 = graph.getLast2();
			fixLoopEnd(loopEnd2,loopId, graph);
		}

		
		
		
		loopStack.pop();
		
	}
	
	protected void fixLoopEnd(GraphNode node, int loopId, FlowGraph graph){
		if(!node.hasNext()){		
			node.setNext(loopStack.peek());
			//Log.Debug("=====" + node.getId() + "->" +loopStack.peek() );			
			
			//�������ĳ��ȱ�ٺ�̽ڵ�Ľڵ���ѭ���Ľ����������ڵ��Ƴ�
			if(secondChildStack.contains(node.getId())){
				secondChildStack.remove(new Integer(node.getId()));
				//Log.Debug("Remove from stack 1 : " + loopEnd1.getId());
				//secondChildStack.add(loopStack.peek());
			}
		} else {
			ArrayList<Integer> follows= node.getFollows();
			Log.Debug(follows);
			for(int i=0; i < follows.size(); i++){
				GraphNode gn = graph.getById(follows.get(i));
				if(gn.isLoop()){
					fixLoopEndRecursion(gn.getId(),loopId , graph);
					
				}
			}
		}
			
	}
	
	public void fixLoopEndRecursion(int loopId, int newLoopId, FlowGraph graph) {
		if(loopId == newLoopId) return;
		for(GraphNode gn: graph){
			if (gn.getLoopId() == loopId
					&& secondChildStack.contains(gn.getId())) {
				secondChildStack.remove(new Integer(gn.getId()));
				gn.setNext(newLoopId);

			} else if(gn.getLoopId() == loopId){
				ArrayList<Integer> follows= gn.getFollows();
				for(int i=1; i < follows.size(); i++){
					GraphNode gn2 = graph.getById(follows.get(i));
					if(gn2.isLoop()){
						fixLoopEndRecursion(gn2.getId(),newLoopId , graph);						
					}
				}
			}
		}		
		
	}


	protected void attachChildren(int id, FlowGraph graph) {

		while (secondChildStack.size() > 0) {
			int s = secondChildStack.pop();
			GraphNode n = graph.getById(s);
			if (!n.isContinue() && !n.isContinue()) {
				n.setNext(id);
			}
		}

		while (nextNode.size() > 0 && nextNode.peek().size() > 0) {
			int s = nextNode.peek().pop();
			GraphNode n = graph.getById(s);
			if (n != null && !n.isContinue() && !n.isContinue()) {
				n.setFirstId(id);
			}
		}
		if (nextNode.size() > 0 && nextNode.peek().size() == 0) {
			nextNode.pop();
		}

		// flushBreakCuntinueStump(id);
	}

//	public void pushFirst(int id) {		
//		firstChildStack.add(id);
//	}
	
	//public void pushSecond(int id) {
	//	secondChildStack.add(id);		
	//}


	public void pushSecond(int id) {
		secondChildStack.add(id);		
	}

	

	
}
