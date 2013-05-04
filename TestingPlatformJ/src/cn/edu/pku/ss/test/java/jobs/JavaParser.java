package cn.edu.pku.ss.test.java.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import japa.parser.ParseException;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.jobs.JobConst;

public class JavaParser implements IJob {

	@Override
	public boolean run(TestData data) {
		try {
			japa.parser.ast.CompilationUnit ast = null;
			
			if(data.getSourceCode()!=null && data.getSourceCode().length()>0){
				ByteArrayInputStream stream = new ByteArrayInputStream(data.getSourceCode().getBytes());
				ast = japa.parser.JavaParser.parse(stream);
			}else{
				try {
					ast = japa.parser.JavaParser.parse(data.getSourceFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
			
			data.put(JobConst.AST, ast);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
