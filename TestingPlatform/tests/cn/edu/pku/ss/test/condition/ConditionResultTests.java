package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class ConditionResultTests extends junit.framework.TestCase {
	

	public void testConstructor() {
		ConditionTreeNode n = new ConditionTreeNodeTests().get();	
		ConditionResult r = new ConditionResult(n.getAFalseTestCase(),3);
		Log.println(r);
		String expected = "[0, 0, 1]";
		assertEquals(expected, r.toString());
		
	}
	
		
}
