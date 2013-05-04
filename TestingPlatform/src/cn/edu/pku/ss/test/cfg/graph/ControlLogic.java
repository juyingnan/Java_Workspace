package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Stack;

import cn.edu.pku.ss.test.inject.IStumpManager;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.util.Log;


public class ControlLogic {

	protected IStumpManager manager = null;
	protected Stump lastStump = null;
	protected Class<? extends Stump> stumpClass = null;	
	protected FlowGraph graph = null;
	protected Stack<Integer> secondChildStack = null;
	protected Stack<Stack<Integer>> nextNode = null;
	protected Stack<Integer> loopStack = null;
	protected ArrayList<ArrayList<Integer>> tryStmts = null;
	protected ArrayList<ArrayList<Integer>> catchStmts = null;
	protected boolean isInCondition = false;
	protected BPStack cStack = null;
	protected Stack<BPStack> logicStack = null;
	
	//protected Stack<ArrayList<Integer>> endTryStmts = null;
	public ControlLogic(IStumpManager manager, Class<? extends Stump> stumpClass){
		this.manager = manager;
		this.stumpClass = stumpClass;
		graph = new FlowGraph();
		secondChildStack = new Stack<Integer>();
		nextNode = new Stack<Stack<Integer>>();
		loopStack = new Stack<Integer>();
		tryStmts = new ArrayList<ArrayList<Integer>>();
		catchStmts = new ArrayList<ArrayList<Integer>>();
		logicStack = new Stack<BPStack>();
		// 初始化操作,加入一个逻辑栈
		cStack = new BPStack();
		logicStack.push(cStack);
		//endTryStmts = new Stack<ArrayList<Integer>>();
	}
	
	//Changed by me```
	public Stump createTryStump(Object node, int lineNum){
		int id = lastStump.getId();
		Stump stump = createSingle(node, false,lineNum);
		Log.println(id + "-" + stump.getId());
		graph.getById(id).setNext(stump.getId());
		lastStump = stump;
		return stump;
	}

	//判断是不是在条件之中
	public boolean isInCondition(){
		return isInCondition;
	}
	
	//设定条件的开始和结束
	public void setIsInCondition(boolean isIn){
		this.isInCondition = isIn;		
	}
	//Changed by me```
	public Stump createSingle(Object node, boolean isLoop, int lineNum){
		Stump root = null;
		if (lastStump == null || lastStump.isFlushed()) {
			root = create(node);
			graph.add(root.getId(), isLoop, lineNum);
			//Changed by me```
			//graph.getById(root.getId()).setLineNum(lineNum);
			if(lastStump!=null){
				graph.setLastNext(root.getId());
				//graph.getById(lastStump.getId()).setNext(root.getId());
				attachChildren(root.getId());
			}	
			lastStump = root;
		} else {		
			//graph.setLastNext(root.getId());
			graph.getById(lastStump.getId()).setLoop(isLoop);	
			attachChildren(lastStump.getId());			
			root = lastStump;
		}	
		
		if(isLoop){		
			loopStack.push(root.getId());
		}
		
		
		
		return root;
	}
	
	//Changed by me```
	public Stump createRoot(Object node, boolean isLoop, int lineNum) {
		
		Stump root = createSingle(node,isLoop,lineNum);	
		nextNode.add(new Stack<Integer>());		
		return root;
		
	}
	
	/**
	 * 构造一个桩节点
	 * */
	protected Stump create(Object node, boolean isLoop, int lineNum) {
		Stump root = null;
		try {
			root = manager.create(node, stumpClass.newInstance());
			//Log.Debug("root id=" + root.getId());
			//Changed by me```
			graph.add(root.getId(), isLoop, lineNum);
			//root.setId(graph.getLast().getId());
		} catch (Exception e) {
			e.printStackTrace();
			Log.println(e.toString());
		}
		return root;
	}

	
	/**
	 * 新建条件的后继节点
	 * */
	public Stump createNext(Object node, int lineNum) {
		//Changed by me```
		Stump next = create(node, false, lineNum);
		ArrayList<BPCondition> array = cStack.getLastConditions();
		array.add(new BPCondition(next.getId()));
		return next;
	}

	public void attachFirst(int id, int first){
		graph.getById(id).setFirstId(first);
	}
	
	//Changed by me```
	public Stump createEnd(Object node, int lineNum){
		Stump root = create(node);
		graph.add(root.getId(), false, lineNum);
		graph.getById(root.getId()).setEnd(true);
		return root;
	}
	
	public Stump createChild(Object n, Stump root, int lineNum) {					
		// 第一条路径
		Stump first = create(n);
		graph.add(first.getId(), lineNum);
		GraphNode gn = graph.getById(root.getId());
		gn.setNext(first.getId());
		// 设置当前的桩，表示这个桩并未插入
		lastStump = first;
		return lastStump;
	}
	
	//Changed by me```
	public Stump createFirstChild(Object n, Stump root, int lineNum){
		return createFirstChild(n, root, false, lineNum);
	}

	//Changed by me```
	public Stump createFirstChild(Object n, Stump root, boolean isLoop, int lineNum){			
		//第一条路径
		Stump first = create(n);
		graph.add(first.getId(), isLoop, lineNum);
		graph.getById(root.getId()).setFirstId(first.getId());	
		
		//设置当前的桩，表示这个桩并未插入
		lastStump = first;
		return lastStump;
	}

	//Changed by me```
	public void createSecondChild(Object n, Stump root, int lineNum) {
		//第二条路径
		Stump second = create(n);
		graph.add(second.getId(), lineNum);
		graph.getById(root.getId()).setSecondId(second.getId());			
		//设置当前的桩，表示这个桩并未插入
		lastStump = second;
		
	}
	
	public void enterTry(){
		tryStmts.add(new ArrayList<Integer>());
	}

	public void existTry(){
		tryStmts.remove(tryStmts.size()-1);
	}
	
	public void enterCatch(){
		catchStmts.add(new ArrayList<Integer>());
	}
	
	public void existCatch(){
		catchStmts.remove(catchStmts.size()-1);
	}
	
	public boolean isInTry(){
		return !tryStmts.isEmpty();
	}
	
	public void addTryStmt(int id){
		if(tryStmts.size()>0){
			tryStmts.get(tryStmts.size()-1).add(id);
		}
	}
	
	public void endTryStmt(int id){
		
	}
	
	public void meetCatch(int id){
		Log.println("meet " + id);
		if(tryStmts.size()>0){
			for(Integer i: tryStmts.get(tryStmts.size()-1)){
				graph.getById(i).addOther(id, false);
			}
		}
	}
	
	public void existCatch(int id){
		//catchStmts.add(id);
	}
	
	public void meetFinal(int id){
		
	}
	
	public void pushSecondChild(Stump root){	
		pushSecondChild(root, false);
	}
	
	public void pushSecondChild(Stump root, boolean isLoop){
		this.secondChildStack.push(root.getId());		
		if(isLoop){
			loopStack.pop();
		}
	}


	public void pushNextChild(int id) {		
		if(nextNode.size()>0){
			nextNode.peek().push(id);
		}		
	}
	
	protected void attachChildren(int id) {
		
		while(secondChildStack.size()>0){
			int s = secondChildStack.pop();
			GraphNode n = graph.getById(s);
			if(!n.isContinue() && !n.isContinue()){
				n.setNext(id);
			}
		}
		
		while(nextNode.size()>0 && nextNode.peek().size()>0){
			int s = nextNode.peek().pop();
			GraphNode n = graph.getById(s);
			if(n!=null && !n.isContinue() && !n.isContinue()){
				n.setFirstId(id);	
			}
		}
		if(nextNode.size()>0 && nextNode.peek().size()==0){
			nextNode.pop();
		}
		
		//flushBreakCuntinueStump(id);
	}

	public FlowGraph getGraph() {
		return graph;
	}


	
	
	public Stump getContinue(Object n){
		Stump stump = getLastStump(n);
		if(!loopStack.isEmpty()){
			GraphNode node = graph.getById(stump.getId());
			node.setContinue(true);
			node.setFirstId(loopStack.peek());
			
		}	
		
		return stump;
	}
	
	
	
	public Stump getBreak(Object n){
		Stump stump = getLastStump(n);
		if(stump.isFlushed()){
			stump = create(n);
		}
		return stump;
	}
	
	public Stump getLast(Object n) {
		Stump stump = getLastStump(n);
		pushNextChild(stump.getId());
		return stump;
	}


	public Stump getEndStump(Object n, int lineNum) {
		
		Stump end = getLast(n);
		if(end.isFlushed()){			
			end = create(n);
			graph.add(end.getId(), lineNum);
			//graph.getLast().setEnd(true);
			graph.getById(lastStump.getId()).setNext(end.getId());
			attachChildren(end.getId());
		} else {
			//graph.getLast().setEnd(true);
		}
		Log.println(end.getId() + "  " + graph.size());
		GraphNode gn = graph.getById(end.getId());
		if(gn!=null)
			gn.setEnd(true);
		return end;
		
	}

	protected Stump create(Object n){
		try {
			Stump root =  this.manager.create(n, stumpClass.newInstance());
			if(!tryStmts.isEmpty()){
				//tryStmts的长度比catchStmts的长度要么大一位要么相等
				if(tryStmts.size() > catchStmts.size()){
					tryStmts.get(tryStmts.size()-1).add(root.getId());
				} else if(tryStmts.size()> 1) {
					tryStmts.get(tryStmts.size()-2).add(root.getId());
				}
				
			}
			return root;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected Stump getLastStump(Object n) {
		Stump stump = null;
		if(lastStump == null){
			Log.println("single stump");
			 stump = create(n);
		} else {
			lastStump.setNode(n);
			stump = lastStump;
		}		
		return stump;
	}



	
}
