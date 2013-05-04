package cn.edu.pku.ss.test.cfg.graph;

import org.junit.Test;

import cn.edu.pku.ss.test.util.IDValuePair;
import cn.edu.pku.ss.test.util.IDValuePairArray;

public class GraphNodeTests extends junit.framework.TestCase {
	
	
	public void testSetNext(){
		GraphNode n = new GraphNode();
		n.setNext(1);
		assertEquals(1, n.getFirstId());		
		n.setNext(2);
		assertEquals(2, n.getSecondId());	
		n.setNext(3);
		assertEquals(new Integer(3), n.getOthers().get(0).getId());
		n.setNext(1);
		assertEquals(1, n.getOthers().size());
	}
	

}
