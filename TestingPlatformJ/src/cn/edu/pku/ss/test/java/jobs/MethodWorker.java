package cn.edu.pku.ss.test.java.jobs;


import cn.edu.pku.ss.test.java.jobs.visitors.StumpVisitor;
import cn.edu.pku.ss.test.jobs.JobFlow;

public class MethodWorker extends JobFlow{

	public MethodWorker(){
		this.add(new JavaParser());
		this.add(new StumpVisitor());
	}
}