package cn.edu.pku.ss.test.java.condition.jobs;


import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobFlow;


public class ConditionStumpWorker extends JobFlow{
	
	public ConditionStumpWorker(){
		//������AST
		this.add(new JavaParser());
		//��׮��������
		this.add(new ConditionVisitor());
		
	}
}
