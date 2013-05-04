package cn.edu.pku.ss.test.java.condition.jobs;


import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;


public final class ConditionWorker extends ConditionStumpWorker {
	public ConditionWorker(){

		//�����׮��Ĵ���
		this.add(new JavaCompiler());
		//�Ŵ��㷨���ɲ�������
		this.add(new CodnitionTestCaseGenerator());
		//���Ը����ʼ���
		this.add(new JavaConditionReporter());
	}
}
