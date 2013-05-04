package cn.edu.pku.ss.test.java.condition.jobs;


import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobFlow;


public class ConditionStumpWorker extends JobFlow{
	
	public ConditionStumpWorker(){
		//解析成AST
		this.add(new JavaParser());
		//插桩，条件树
		this.add(new ConditionVisitor());
		
	}
}
