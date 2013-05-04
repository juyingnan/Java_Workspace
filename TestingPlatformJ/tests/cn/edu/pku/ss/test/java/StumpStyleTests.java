package cn.edu.pku.ss.test.java;

import java.io.File;

import org.junit.Test;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionVisitor;
import cn.edu.pku.ss.test.java.condition.jobs.ConditionVisitor.ConditionStumpStyle;
import cn.edu.pku.ss.test.util.Log;

public class StumpStyleTests {

	public ConditionVisitor getVisitor(CompilationUnit n, ConditionStumpStyle style){
		
		ConditionVisitor cv = new ConditionVisitor();
		cv.setTargetMethod("StumpStyleTest", "test3");
		cv.setStumpStyle(style);
		cv.visit(n, null);
		return cv;
	}
	
	@Test
	public void test(){
		String file = Setting.Base+ "StumpStyleTest.java";
		try {			
			CompilationUnit n = JavaParser.parse(new File(file));
			ConditionVisitor cv =getVisitor(n, ConditionStumpStyle.Single);
			Log.println("Single:");
			Log.println(cv.getSource());
			
			cv = getVisitor(n, ConditionStumpStyle.SubInner);
			Log.println("SubInner:");
			Log.println(cv.getSource());

			cv = getVisitor(n, ConditionStumpStyle.SubInnerList);
			Log.println("SubInnerList:");
			Log.println(cv.getSource());

			cv = getVisitor(n, ConditionStumpStyle.SubOutterList);
			Log.println("SubOutterList:");
			Log.println(cv.getSource());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
