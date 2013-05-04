package cn.edu.pku.ss.test.condition;

import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.util.Log;

public class ConditionResultArrayTests extends junit.framework.TestCase {
	

	public void testConstructor() {
		
		ConditionTreeNode n = new ConditionTreeNodeTests().get();
		ArrayList<String> a = n.getAllShortCuicus();
		Log.println(a);
		ConditionResultArray c = new ConditionResultArray(a,3);
		ConditionResultArray d = new ConditionResultArray(a,3);
		c.join(d);
		Log.println(c);
		String exp = "[[0, -1, -1, 0, -1, -1], [1, 0, 0, 1, 0, 0], [1, 1, -1, 1, 1, -1], [1, 0, 1, 1, 0, 1]]";	
		assertEquals(exp, c.toString());
	}

	
}
