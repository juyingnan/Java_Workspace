package cn.edu.pku.ss.test.cfg.graph;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class GraphPathTests extends junit.framework.TestCase {
	

	public void testClone(){
		GraphPath expected = new GraphPath();
		expected.add(1);expected.add(6);expected.add(7);expected.add(8); 
		expected.getLoopEnds().put(3, 5);
		expected.getLoopHistory().put(2, 4);
		expected.getLoops().push(4);
		
		GraphPath actual = expected.clone();
		assertEquals(expected, actual);		
		assertEquals(expected.getLoopEnds().get(3), actual.getLoopEnds().get(3));
		assertEquals(expected.getLoopHistory().get(2), actual.getLoopHistory().get(2));
		assertEquals(expected.getLoops().peek(), actual.getLoops().peek());
	}
	
	public void testHashSet(){
		
		HashSet<Integer> expected =new HashSet<Integer>();
		GraphPath actualPath = new GraphPath();
		
		expected.add(1);
		actualPath.add(1);
		
		expected.add(3);
		actualPath.add(3);
		
		expected.add(5);
		actualPath.add(5);
		
		expected.add(7);
		actualPath.add(7);
		
		assertEquals(expected, actualPath.getHashSet());
	}
}
