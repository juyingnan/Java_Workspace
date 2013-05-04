package cn.edu.pku.ss.test.java.bp;

import cn.edu.pku.ss.test.bp.BPTestCaseGenerator;
import cn.edu.pku.ss.test.java.jobs.JavaParser;
import cn.edu.pku.ss.test.jobs.JobFlow;
import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;

public class BPWorker extends BPStumpWorker {
	
	public BPWorker(){
		
		this.add(new JavaCompiler());
		this.add(new BPTestCaseGenerator());
		this.add(new JavaBPReporter());
	}
	
	
}
