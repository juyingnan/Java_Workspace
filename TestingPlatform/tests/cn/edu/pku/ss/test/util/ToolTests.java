package cn.edu.pku.ss.test.util;

import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

public class ToolTests {

	@Test
	public void TestCoverage(){
		HashSet<Integer> set1 = new HashSet<Integer>();
		set1.add(1);
		set1.add(3);
		set1.add(5);
		set1.add(10);
		set1.add(20);
	
		HashSet<Integer> set2 = new HashSet<Integer>();
		set2.add(1);
		set2.add(2);
		set2.add(3);
		set2.add(4);
		set2.add(10);
		
		double expected = 3.0/5.0;
		double actual = Tools.canculateCoverage(set1, set2);
		
		Assert.assertEquals(expected, actual);
		
	}
	
	
}
