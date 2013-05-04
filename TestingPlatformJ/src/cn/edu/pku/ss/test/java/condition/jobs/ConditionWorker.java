package cn.edu.pku.ss.test.java.condition.jobs;


import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;


public final class ConditionWorker extends ConditionStumpWorker {
	public ConditionWorker(){

		//编译插桩后的代码
		this.add(new JavaCompiler());
		//遗传算法生成测试用例
		this.add(new CodnitionTestCaseGenerator());
		//测试覆盖率计算
		this.add(new JavaConditionReporter());
	}
}
