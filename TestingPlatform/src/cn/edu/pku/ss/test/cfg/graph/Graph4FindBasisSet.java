package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Stack;

public class Graph4FindBasisSet extends ArrayList<Node4FindBasisSet> {
	
	public Node4FindBasisSet getByID(int NodeID){
		Node4FindBasisSet node;
		int index = -1;
		for(int i = 0; i<this.size(); i++){
			if(this.get(i).id == NodeID){
				index = i;
				break;
			}
		}
		node = this.get(index);
		return node;
	}
	
	
//	Node node;
	String path="";
	String temp1="";
	Stack<Integer> temp = new Stack<Integer>();
	Stack<Integer> trace = new Stack<Integer>();
	
	public String findBasis(Node4FindBasisSet node){

		if(node.destOfDftEdg == -99){
//			temp1 += node.id;
//			path = temp + ",";
			trace.add(node.id);
			temp = (Stack<Integer>)trace.clone();
			while(!temp.empty()){
				path += temp.pop();
			}
			path += ",";
		}
		else if(!node.visited){
			node.visited = true;
			temp1 = ""+temp1+node.id;
			trace.add(node.id);
			findBasis(getByID(node.destOfDftEdg));
			int notDefaultEdge = node.destOfDftEdg;
			if (node.firstNode != node.destOfDftEdg && node.firstNode != -1){
				notDefaultEdge = node.firstNode;
				findBasis(getByID(notDefaultEdge));
			}
			else if(node.secondNode != node.destOfDftEdg && node.secondNode != -1){
				notDefaultEdge = node.secondNode;
				findBasis(getByID(notDefaultEdge));
			}
		}
		else{
			temp1 += node.id;
			trace.add(node.id);
			findBasis(getByID(node.destOfDftEdg)); 
		}
		trace.pop();
		return path;
	}
	
	
	public String toString(){
		String s = "";
		String temp = "";
		Node4FindBasisSet node;
		for(int i=0; i<this.size();i++){
			node = this.get(i);
			temp = "" + node.id + node.firstNode + node.secondNode + node.destOfDftEdg + node.visited + "\n";
			s += temp;
		}
		return s;
	}
}