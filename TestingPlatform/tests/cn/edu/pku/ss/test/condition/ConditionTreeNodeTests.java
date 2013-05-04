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
	 * ��ýڵ����
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
	 * ���һ������Ϊ��Ĳ�������
	 * */	
	public void testGetATrueTestCase()
	{
		ConditionTreeNode n = get();
		assertEquals("TFT", n.getATrueTestCase());
	}
	
	/**
	 * ���һ������Ϊ�ٵĲ�������
	 * */	
	public void testGetAFalseTestCase()
	{		
		ConditionTreeNode n = get();
		assertEquals("FFT", n.getAFalseTestCase());
	}
	
	
	/**
	 * ��������·������
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
	 * ��üٲ���·������
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
	 * ������в���·������
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
