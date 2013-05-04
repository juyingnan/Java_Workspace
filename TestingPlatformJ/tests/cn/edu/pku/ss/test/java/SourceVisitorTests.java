package cn.edu.pku.ss.test.java;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import cn.edu.pku.ss.test.java.jobs.visitors.SourceVisitor;
import cn.edu.pku.ss.test.util.Log;

public class SourceVisitorTests {

	@Test
	public void test(){
		String src = "public class TT{public void test(int x){if(x>0)x++;else x--;}}";
		SourceVisitor sv = new SourceVisitor();
		try {
			sv.visit(JavaParser.parse(new ByteArrayInputStream(src.getBytes())), null);
			Log.println(sv.getSource());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
