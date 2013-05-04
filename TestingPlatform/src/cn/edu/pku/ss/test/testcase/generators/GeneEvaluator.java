package cn.edu.pku.ss.test.testcase.generators;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;


import cn.edu.pku.ss.test.inject.monitors.Monitor;
import cn.edu.pku.ss.test.jobs.IJob;
import cn.edu.pku.ss.test.testcase.ExecuteResult;
import cn.edu.pku.ss.test.testcase.MethodParam;
import cn.edu.pku.ss.test.testcase.TestCaseExecutor;
import cn.edu.pku.ss.test.util.Log;

public class GeneEvaluator extends FitnessFunction {
	
	protected TestCaseExecutor executor;
	protected GeneGenerator generator;
	private Object target;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public GeneGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(GeneGenerator generator) {
		this.generator = generator;
	}

	public void setExecutor(TestCaseExecutor executor) {
		this.executor = executor;
	}

	public TestCaseExecutor getExecutor() {
		return executor;
	}
	
	
	@Override
	public double evaluate(IChromosome subject){
		Gene[] genes = subject.getGenes();	
		MethodParam testCase=GeneParam.createParam(executor.getTestMethod(), genes);
		if(executor!=null){
			ExecuteResult rt = executor.execute(testCase);
			//监控实际执行路径
			Object actual = ((Monitor)rt.getMonitor()).getResult();
			return generator.evaluate(target, actual);
		}
		else return 0;
	}

	//protected abstract double evaluate(Object target, Object actual);



}
