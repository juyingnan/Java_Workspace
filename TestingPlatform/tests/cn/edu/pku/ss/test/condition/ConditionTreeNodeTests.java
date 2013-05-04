package cn.edu.pku.ss.test.condition;


import java.util.ArrayList;

import org.junit.Test;

import cn.edu.pku.ss.test.condition.ConditionTreeNode.OperatorType;

public class ConditionTreeNodeTests extends junit.framework.TestCase {

	/**
	 * a &&(b || c) 
	 * 
	 * */ 
	public ConditionTreeNode get(){
		
		ConditionTreeNode root = new ConditionTreeNode();
		root.setOperator(OperatorType.And);
		root.setLeft(new ConditionTreeNode("a"));
		
		
		ConditionTreeNode braket = new ConditionTreeNode();
		braket.setOperator(OperatorType.Bracket);
		
		ConditionTreeNode bc = new ConditionTreeNode();
		bc.setOperator(OperatorType.Or);
		bc.setLeft(new ConditionTreeNode("b"));
		bc.setRight(new ConditionTreeNode("c"));
		
		braket.setLeft(bc);
		root.setRight(braket);
		
		return root;
	}
	
	/**
	 * 获得节点个数
	 * */	
	public void testGetCount()
	{
		ConditionTreeNode n = get();
		assertEquals(3, n.getCount());		
	}
	
	public void testToString(){
		ConditionTreeNode n = get();
		assertEquals("a && ( b || c )", n.toString());
	}
	
	/**
	 * 获得一个条件为真的测试用例
	 * */	
	public void testGetATrueTestCase()
	{
		ConditionTreeNode n = get();
		assertEquals("TFT", n.getATrueTestCase());
	}
	
	/**
	 * 获得一个条件为假的测试用例
	 * */	
	public void testGetAFalseTestCase()
	{		
		ConditionTreeNode n = get();
		assertEquals("FFT", n.getAFalseTestCase());
	}
	
	
	/**
	 * 获得真测试路径集合
	 * */	
	public void testGetTrueShortCuicus()
	{
		ConditionTreeNode n = get();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("TT-");
		expected.add("TFT");
		assertEquals(expected, n.getTrueShortCuicus());
	}
	
	/**
	 * 获得假测试路径集合
	 * */	
	public void testGetFalseShortCuicus()
	{		
		ConditionTreeNode n = get();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("F--");
		expected.add("TFF");
		assertEquals(expected, n.getFalseShortCuicus());		
	}
	
	/**
	 * 获得所有测试路径集合
	 * */	
	public void testGetAllShortCuicus()
	{		
		ConditionTreeNode n = get();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("F--");
		expected.add("TFF");
		expected.add("TT-");
		expected.add("TFT");
		assertEquals(expected, n.getAllShortCuicus());		
	}
	
	
	
	
}
