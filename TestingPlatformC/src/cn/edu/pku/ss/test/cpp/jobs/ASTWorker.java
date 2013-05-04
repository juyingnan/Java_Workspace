package cn.edu.pku.ss.test.cpp.jobs;

import cn.edu.pku.ss.test.cpp.jobs.visitors.ASTViewVisitor;
import cn.edu.pku.ss.test.cpp.jobs.visitors.CppParser;
import cn.edu.pku.ss.test.jobs.JobFlow;

public class ASTWorker extends JobFlow{

	public ASTWorker(){
		this.add(new CppParser());
		this.add(new ASTViewVisitor());
	}
}
