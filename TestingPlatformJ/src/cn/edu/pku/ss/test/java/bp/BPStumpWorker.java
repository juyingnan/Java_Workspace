package cn.edu.pku.ss.test.java.bp;

import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobFlow;

public class BPStumpWorker extends JobFlow {
	public BPStumpWorker(){
		this.add(new JavaParser());
		this.add(new BasisPathVisitor());
	}
	
	
}
