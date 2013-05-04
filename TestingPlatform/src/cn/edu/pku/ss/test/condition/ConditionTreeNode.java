package cn.edu.pku.ss.test.condition;


import java.util.ArrayList;

import cn.edu.pku.ss.test.util.Log;

public class ConditionTreeNode {

	public static final String TRUE = "T";
	public static final String FALSE = "F";
	
	private ConditionTreeNode left;
	private ConditionTreeNode right;
	private ConditionTreeNode parent;
	private OperatorType operator;
	private String name;
	private int id;
	private int beginLine;
	private int endLine;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setBeginLine(int n) {
		this.beginLine = n;
	}
	
	public int getBeginLine(){
		return this.beginLine;
	}
	
	public void setEndLine(int n) {
		this.endLine = n;
	}
	
	public int getEndLine(){
		return this.endLine;
	}
	
	public int getFirstId(){
		
		if(this.getLeft()==null){
			//Log.Debug("ID: "  +  this.getId());
			return this.getId();
		} else {
			//Log.Debug("ID: "  +  this.getId());
			return this.getLeft().getFirstId();
		}
		
	}

	public ConditionTreeNode(){
		setOperator(OperatorType.None);
	}	
	
	public ConditionTreeNode(String name) {
		setName(name);
		setOperator(OperatorType.None);
	}
	
	public ConditionTreeNode(String name, int id) {
		setName(name);
		setOperator(OperatorType.None);
		//Log.Debug("ConditionTreeNode: " + id);
		setId(id);
	}


	public ConditionTreeNode(OperatorType operator) {
		setOperator(operator);
	}

	public ConditionTreeNode getLeft() {
		return left;
	}
	public void setLeft(ConditionTreeNode left) {
		this.left = left;
	}
	public ConditionTreeNode getRight() {
		return right;
	}
	public void setRight(ConditionTreeNode right) {
		this.right = right;
	}
	public ConditionTreeNode getParent() {
		return parent;
	}
	public void setParent(ConditionTreeNode parent) {
		this.parent = parent;
	}
	
	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}
	public OperatorType getOperator() {
		return operator;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	/**
	 * 获得一个条件为真的测试用例
	 * */
	public String getATrueTestCase()
	{
		switch (operator) {
		case None:
			return TRUE;
		case And:			
			return getLeft().getATrueTestCase() + getRight().getATrueTestCase();
		case Or:			
			return getLeft().getAFalseTestCase() + getRight().getATrueTestCase();
		case Bracket:
			return getLeft().getATrueTestCase();			
		}
		return "";
	}
	
	/**
	 * 获得一个条件为假的测试用例
	 * */
	public String getAFalseTestCase()
	{
		
		switch (operator) {
		case None:
			return FALSE;
		case And:			
			return getLeft().getAFalseTestCase() + getRight().getATrueTestCase();
		case Or:			
			return getLeft().getAFalseTestCase() + getRight().getAFalseTestCase();
		case Bracket:
			return getLeft().getAFalseTestCase();			
		}
		return "";
	}
	
	
	/**
	 * 获得真测试路径集合
	 * */
	public ArrayList<String> getTrueShortCuicus()
	{
		switch (operator) {
		case None:
			ArrayList<String> list=new ArrayList<String>();
			list.add(TRUE);
			return list;
		case And:	
		case Or:
			ArrayList<String> left=getLeft().getTrueShortCuicus();
			if(operator == OperatorType.Or)			{
				ArrayList<String> result=new ArrayList<String>();
				ArrayList<String> right=getRight().getNullList();
				result.addAll(getJoinList(left, right));
				result.addAll(getJoinList(getLeft().getFalseShortCuicus(),getRight().getTrueShortCuicus()));
				return result;
			}
			else if(operator == OperatorType.And)
			{
				ArrayList<String> right=getRight().getTrueShortCuicus();
				return getJoinList(left, right);
			}			
		case Bracket:
			return getLeft().getTrueShortCuicus();			
		}
		return null;
	}
	
	/**
	 * 获得假测试路径集合
	 * */
	public ArrayList<String> getFalseShortCuicus()
	{		
		switch (operator) {
		case None:
			ArrayList<String> list=new ArrayList<String>();
			list.add(FALSE);
			return list;
		case And:	
		case Or:			

			ArrayList<String> left=getLeft().getFalseShortCuicus();
			if(operator == OperatorType.Or)	
			{
				ArrayList<String> right=getRight().getFalseShortCuicus();
				return getJoinList(left, right);
			}
			else if(operator == OperatorType.And)
			{
				ArrayList<String> result=new ArrayList<String>();
				
				//left false right null
				ArrayList<String> right=getRight().getNullList();
				result.addAll(getJoinList(left, right));
				
				//left true right false
				result.addAll(getJoinList(getLeft().getTrueShortCuicus(),getRight().getFalseShortCuicus()));
				return result;
			}
			
		case Bracket:
			return getLeft().getFalseShortCuicus();			
		}
		return null;
		
	}
	
	/**
	 * 获得所有短路径节点集合
	 * */
	public ArrayList<String> getAllShortCuicus()
	{
		ArrayList<String> list=getFalseShortCuicus();
		list.addAll(getTrueShortCuicus());
		return list;
	}
	
	/**
	 * 获得空节点个数
	 * */
	public ArrayList<String> getNullList()
	{
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<getCount();i++)
		{
			sb.append("-");
		}
		String result= sb.toString();
		ArrayList<String> lrst=new ArrayList<String>();
		lrst.add(result);
		return lrst;
	}
	
	/**
	 * 获得节点个数
	 * */
	public int getCount()
	{
		switch (operator) {
		case None:
			return 1;
		case And:			
		case Or:
			return getLeft().getCount() + getRight().getCount();
		case Bracket:
			return getLeft().getCount();			
		}
		return 0;
	}
	
	
	/**
	 * 将当前条件转换为字符串
	 * */
	@Override
	public String toString() {
		switch (operator) {
		case None:
			return name;
		case And:			
			return getLeft() + " && " + getRight();
		case Or:
			return getLeft() + " || " + getRight();
		case Bracket:
			return "( " + getLeft() + " )";
			
		}
		return "";
	}
	
	
	/**
	 * 将当前条件转换为字符串
	 * */
	public void toStringList(ArrayList<String> arr) {
		switch (operator) {
		case None:
			arr.add(this.toString());
			break;
		case And:	
		case Or:
			getLeft().toStringList(arr);
			getRight().toStringList(arr);			
			break;
		case Bracket:
			getLeft().toStringList(arr);
			break;
			
		}
		//return arr;
	}

	
	/**
	 * 将两个列表合成一个新的列表
	 * */
	protected ArrayList<String> getJoinList(ArrayList<String> left,ArrayList<String> right)
	{
		ArrayList<String> result=new ArrayList<String>();
		for(int i=0;i<left.size();i++)
		{
			for(int j=0;j<right.size();j++)
			{
				result.add(left.get(i)+right.get(j));
			}
		}
		return result;
	}

	public enum OperatorType{
		None,		
		And,
		Or,
		Bracket,
	}
}
