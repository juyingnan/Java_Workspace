package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Stack;

import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.util.Log;

public class BPStack {
	/**
	 * 这个栈用于保存向条件的Then跳转的节点
	 * */
	//protected Stack<Integer> firstChildStack = null;
	/**
	 * 这个栈用于保存向条件的Else跳转的节点
	 * */
	protected Stack<Integer> secondChildStack = null;
	/**
	 * 这个栈用于保存缺少第下一个后继节点的节点
	 * */
	protected Stack<Stack<Integer>> nextNode = null;
	/**
	 * 这个栈里保存循环的第一个节点
	 * */
	protected Stack<Integer> loopStack = null;
	/**
	 * 这个栈里保存所有Try节点当中的节点
	 * */
	protected ArrayList<ArrayList<Integer>> tryStmts = null;
	/**
	 * 这个栈里保存所有Catch节点当中的节点
	 * */
	protected ArrayList<ArrayList<Integer>> catchStmts = null;
	/**
	 * 保存上一个节点
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
	 * 检查是否需要创建一个节点
	 * */
	public boolean needCreateNew(){
		return lastStump==null || lastStump.isFlushed();
	}
	
	/**
	 * 设定上一个节点
	 * */
	public void setLastStump(Stump stump){
		lastStump = stump;	
		if(stump == null){
			Log.Debug("mabi");
		}
	}
	
	/**
	 * 获取上一个节点
	 * */
	public Stump getLastStump() {
		return lastStump;
	}
	
	/**
	 * 增加一层条件
	 * */
	public void newPushConditions(){
		conditions.add(new ArrayList<BPCondition>());
	}
	
	/**
	 * 增加单个条件
	 * */
	public void pushCondition(BPCondition c){
		conditions.get(conditions.size()-1).add(c);
	}
	
	/**
	 * 获取一层条件
	 * */
	public ArrayList<BPCondition> getLastConditions(){
		return conditions.get(conditions.size()-1);
	}
	
	/**
	 * 移除一层条件
	 * */
	public void removeConditions() {
		conditions.remove(conditions.size()-1);
		
	}
	
	/**
	 * 增加一个循环的开始点
	 * */
	public void addLoop(int id) {
		this.loopStack.push(id);
	}

	/**
	 * 关联循环的结束节点和开始节点
	 * */
	public void attachLoop(int loopId, FlowGraph graph) {
		
		Log.Debug("attachLoop : " + loopId);
		
		//结束节点最多有2个, 可以退出的循环有多个
		GraphNode loopEnd1 = graph.getLast();
		fixLoopEnd(loopEnd1,loopId, graph);
		
		
		//Log.Debug("loopEnd1=" + loopEnd1);
		GraphNode loopEnd2 = null;
		//两个的情况是循环中嵌套了if(){}else{}
		//两个循环结束节点特点为 hasNext = false, 即没有任何后继节点
		//Switch case 的情况更为复杂,也必须得考虑
		//Switch case 的情况将不止两个LoopEnd, 每一个case都是一个LoopEnd
		//Break也得做特殊处理
		//因为时间仓促,暂时不考虑这些情况了
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
			
			//如果发现某个缺少后继节点的节点是循环的结束则把这个节点移除
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
