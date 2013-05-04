package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

import cn.edu.pku.ss.test.util.Log;

public class GraphPath extends ArrayList<Integer>{

	private Stack<Integer> loops = new Stack<Integer>();
	private Hashtable<Integer,Integer> loopEnds = new Hashtable<Integer,Integer>();
	private Hashtable<Integer,Integer> loopHistory = new Hashtable<Integer,Integer>();
	private boolean isNecessary = true;
	
	public GraphPath(){
		
	}
	
	public GraphPath(ArrayList<Integer> arr){
		for(int i=0; i< arr.size(); i++){
			this.add(arr.get(i));
		}
	}
	
	public Stack<Integer> getLoops(){
		return loops;
	}
	
	public Hashtable<Integer,Integer> getLoopEnds(){
		return loopEnds;
	}
	
	public Hashtable<Integer,Integer> getLoopHistory(){
		return loopHistory;
	}
	
	public void removeLoop(int id){
		if(loopEnds.containsKey(id)){
			loopHistory.put(id, loopEnds.get(id));
			loopEnds.remove(id);
		} 
	}
	
	@SuppressWarnings("unchecked")
	public GraphPath clone(){
		GraphPath gp = new GraphPath();
		//拷贝 value
		for(int i=0; i< this.size(); i++){
			gp.add(this.get(i));
		}
		//if(loopHistory.size()>0)
		//	gp.loopHistory = (Hashtable<Integer, Integer>) loopHistory.clone();
		//if(loopEnds.size()>0)
		//	gp.loopEnds = (Hashtable<Integer, Integer>) loopEnds.clone();
		//if(loops.size()>0)
		//	gp.loops = (Stack<Integer>) loops.clone();
		
		Enumeration<Integer> e = loopHistory.keys();
		while(e.hasMoreElements()){
			Integer key = e.nextElement();
			gp.loopHistory.put(key, loopHistory.get(key));
		}
		
		e = loopEnds.keys();
		while (e.hasMoreElements()) {
			Integer key = e.nextElement();
			gp.loopEnds.put(key, loopEnds.get(key));
		}

		e = loops.elements();
		while (e.hasMoreElements()) {
			gp.loops.add(e.nextElement());
		}
		
		gp.setNecessary(this.isNecessary());
		//Debug.println("----");
		//Debug.println(gp.loopHistory + " old: " +loopHistory);
		//Debug.println(gp.loopEnds + " old: " +loopEnds);
		//Debug.println(gp.loops + " old: " +loops);
		
		return gp;
	}
	
	public void setNecessary(boolean isNecessary) {
		this.isNecessary = isNecessary;
	}

	public boolean isNecessary() {
		return isNecessary;
	}
	
	public HashSet<Integer> getHashSet(){		
		HashSet<Integer> set = new HashSet<Integer>();
		
		for(int i=0; i< this.size(); i++){
			if(!set.contains(this.get(i))){
				set.add(this.get(i));
			}
		}
		
		return set;
	}
		
	
	@Override
	public String toString(){
		StringBuilder buff = new StringBuilder();
		
		for(int i=0;i< this.size(); i++){
			buff.append(this.get(i) + " ");
		}
		
		buff.append(this.loopHistory);
		buff.append(this.isNecessary);
		
		return buff.toString();
	}

	
	/**
	 * 检查路径的最后一个节点和followId的组合是否在历史记录里出现过,如果是,则返回false, 否则返回true,
	 * true表示可以走这条路
	 * */
	public boolean check(int followId) {
		int last = this.size()-1;
		//Log.Debug(this + " check: " + followId);
		for(int i=0; i<this.size()-1; i++){
			//查看这种组合是否出现,如果是,则表示 foolowId不再是可以走的一条路
			if(this.get(i) == this.get(last) && this.get(i+1)==followId){
				return false;
			}
			
		}
		
		return true;
	}


}
