package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.condition.ConditionResult;
import cn.edu.pku.ss.test.condition.ConditionResultArray;
import cn.edu.pku.ss.test.util.Log;

public class ConditionTreeTests extends junit.framework.TestCase {
	
	public static ConditionTree get(){
		ConditionTree t = new ConditionTree();
		t.add( new ConditionTreeNodeTests().get());
		t.add( new ConditionTreeNodeTests().get());	
		return t;
	}
	

	public void testGenerateDCPath() {
		
		String expected = "[[1, 0, 1, 1, 0, 1], [1, 0, 1, 0, 0, 1], [0, 0, 1, 1, 0, 1], [0, 0, 1, 0, 0, 1]]";
		String actual = get().generateDCPath().toString();
		Log.println(actual);
		assertEquals(expected, actual);

	}
	
	@Test
	public void testGenerateCCPath(){
	
		String expected = "[[1, 1, 1, 1, 1, 1], [1, 1, 1, 0, 0, 0], [0, 0, 0, 1, 1, 1], [0, 0, 0, 0, 0, 0]]";
		String actual = get().generateCCPath().toString();
		Log.println(actual);
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void  testGenerateMCPath(){
		
		String expected = "[[0, -1, -1, 0, -1, -1], [0, -1, -1, 1, 0, 0], [0, -1, -1, 1, 1, -1], [0, -1, -1, 1, 0, 1], [1, 0, 0, 0, -1, -1], [1, 0, 0, 1, 0, 0], [1, 0, 0, 1, 1, -1], [1, 0, 0, 1, 0, 1], [1, 1, -1, 0, -1, -1], [1, 1, -1, 1, 0, 0], [1, 1, -1, 1, 1, -1], [1, 1, -1, 1, 0, 1], [1, 0, 1, 0, -1, -1], [1, 0, 1, 1, 0, 0], [1, 0, 1, 1, 1, -1], [1, 0, 1, 1, 0, 1]]";
		String actual = get().generateMCPath().toString();
		Log.println(actual);
		assertEquals(expected, actual);
	}
	

	public void  testGenerateAllPath() {
		
		String expected = "[[0, 0, 0, 0, 0, 0], [1, 0, 0, 0, 0, 0], [1, 0, 0, 0, 0, 0], [1, 1, 0, 0, 0, 0], [1, 0, 0, 0, 0, 0], [1, 0, 1, 0, 0, 0], [1, 1, 0, 0, 0, 0], [1, 1, 1, 0, 0, 0], [1, 0, 0, 0, 0, 0], [1, 0, 0, 1, 0, 0], [1, 0, 1, 0, 0, 0], [1, 0, 1, 1, 0, 0], [1, 1, 0, 0, 0, 0], [1, 1, 0, 1, 0, 0], [1, 1, 1, 0, 0, 0], [1, 1, 1, 1, 0, 0], [1, 0, 0, 0, 0, 0], [1, 0, 0, 0, 1, 0], [1, 0, 0, 1, 0, 0], [1, 0, 0, 1, 1, 0], [1, 0, 1, 0, 0, 0], [1, 0, 1, 0, 1, 0], [1, 0, 1, 1, 0, 0], [1, 0, 1, 1, 1, 0], [1, 1, 0, 0, 0, 0], [1, 1, 0, 0, 1, 0], [1, 1, 0, 1, 0, 0], [1, 1, 0, 1, 1, 0], [1, 1, 1, 0, 0, 0], [1, 1, 1, 0, 1, 0], [1, 1, 1, 1, 0, 0], [1, 1, 1, 1, 1, 0], [1, 0, 0, 0, 0, 0], [1, 0, 0, 0, 0, 1], [1, 0, 0, 0, 1, 0], [1, 0, 0, 0, 1, 1], [1, 0, 0, 1, 0, 0], [1, 0, 0, 1, 0, 1], [1, 0, 0, 1, 1, 0], [1, 0, 0, 1, 1, 1], [1, 0, 1, 0, 0, 0], [1, 0, 1, 0, 0, 1], [1, 0, 1, 0, 1, 0], [1, 0, 1, 0, 1, 1], [1, 0, 1, 1, 0, 0], [1, 0, 1, 1, 0, 1], [1, 0, 1, 1, 1, 0], [1, 0, 1, 1, 1, 1], [1, 1, 0, 0, 0, 0], [1, 1, 0, 0, 0, 1], [1, 1, 0, 0, 1, 0], [1, 1, 0, 0, 1, 1], [1, 1, 0, 1, 0, 0], [1, 1, 0, 1, 0, 1], [1, 1, 0, 1, 1, 0], [1, 1, 0, 1, 1, 1], [1, 1, 1, 0, 0, 0], [1, 1, 1, 0, 0, 1], [1, 1, 1, 0, 1, 0], [1, 1, 1, 0, 1, 1], [1, 1, 1, 1, 0, 0], [1, 1, 1, 1, 0, 1], [1, 1, 1, 1, 1, 0], [1, 1, 1, 1, 1, 1]]";
		String actual = get().generateAllPath().toString();
		Log.println(actual);
		assertEquals(expected, actual);
	}

	
	public void testToBin(){
		String expected = "00000100";
		String actual = ConditionTree.toBin(4, 8);
		assertEquals(expected, actual);
	}
}
