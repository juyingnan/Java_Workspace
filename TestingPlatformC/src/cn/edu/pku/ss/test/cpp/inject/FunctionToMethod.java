package cn.edu.pku.ss.test.cpp.inject;

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;

import cn.edu.pku.ss.test.cpp.jobs.visitors.SourceVisitor;
import cn.edu.pku.ss.test.util.Log;

public class FunctionToMethod {
	
	/**
	 * C++  int test(int a, int b);
	 * ==>
	 * Java  public native int test(int a, int b);
	 * 
	 * */
	public static String convert(CPPASTFunctionDefinition fun){
		StringBuilder sb = new StringBuilder();
		SourceVisitor sv = new SourceVisitor();
		sb.append("public native ");
		fun.getDeclSpecifier().accept(sv);
		sb.append(sv.getSource());	
		sb.append(" ");
		sv = new SourceVisitor();
		fun.getDeclarator().accept(sv);
		sb.append(sv.getSource());	
		//Debug.println(sb.toString());
		return sb.toString();
	}
	
	//public static String getFuncDefinitionString(CPPASTFunctionDefinition fun){
		
	//}
	
	
	
}
