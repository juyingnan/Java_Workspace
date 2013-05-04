 package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class FlowGraphTests extends junit.framework.TestCase {
	
	public FlowGraph get(){
		FlowGraph g = new FlowGraph();
		
		GraphNode n0 = new GraphNode(0);
		n0.setLoop(true);
		n0.setNext(1);
		n0.setNext(4);
		g.add(n0);
		
		GraphNode n1 = new GraphNode(1);
		n1.setNext(2);
		n1.setNext(4);
		g.add(n1);
		
		GraphNode n2 = new GraphNode(2);
		n2.setLoop(true);
		n2.setNext(3);
		n2.setNext(4);
		g.add(n2);
		
		
		GraphNode n3 = new GraphNode(3);
		n3.setNext(4);
		g.add(n3);
		
		GraphNode n4 = new GraphNode(4);
		n4.setEnd(true);
		g.add(n4);
		return g;
	}
	
	public void testGeneratePath(){
		FlowGraph fg = get();
		
		Log.println(fg);
		
		GraphPathArray gp = fg.generatPaths();
		
		String[] expected = new String[]{
				"0 1 2 3 2 0 4 {2=4, 0=4}true",
				"0 4 {}true",
				"0 1 0 4 {0=4}true",
				"0 1 2 0 4 {0=4}true"
		};
		
		for(int i=0; i < expected.length; i++){
			assertEquals(expected[i], gp.get(i).toString());
		}
		
	}
}
