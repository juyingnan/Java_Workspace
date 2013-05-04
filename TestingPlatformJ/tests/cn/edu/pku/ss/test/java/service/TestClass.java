package cn.edu.pku.ss.test.java.service;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		test1(true, false, false, true);
		test1(false, true, false, true);
		test1(true, false, false, true);
		test1(true, false, true, false);
		test1(false, false, false, false);

	}

	public static void test1(boolean a,boolean b,boolean c,boolean d){
		if(((visit(0,a) && a) || (visit(1,b) && b) || (visit(2,c) && c)) && (visit(3,d) && d)){
			
		}
		System.out.println("");
	}
	
	public static boolean visit(int id, boolean value){
		System.out.print(id +"=" + value + " ");
		return true;
	}
	
}
