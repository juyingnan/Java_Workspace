package cn.edu.pku.ss.test.java.jobs.visitors;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;

import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.java.Const;
import cn.edu.pku.ss.test.java.util.ConstText;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.util.Log;

public class SingleMethodVisitor<R,A> extends StumpVisitor<R, A> {
	
	/**
	 * Ŀ�귽��
	 * */
	private String targetMethod;
	
	private String targetClass;
	
	private String target;
	
	/**
	 * �Ƿ��ڴ���״̬
	 * */
	private boolean doProcess;
	
	public SingleMethodVisitor(){
		super();
		doProcess = false;
	}
	
	
	/**
	 * ����Ŀ�귽������
	 * */
	public void setTargetMethod(String targetClass, String targetMethod) {
		super.interestes.add(targetClass);
		this.targetClass = targetClass;
		this.targetMethod = targetMethod;
		this.target = targetClass + ConstText.DOT + targetMethod;
	}

	/**
	 * ��ȡĿ����ͷ�������
	 * */
	public String getTarget() {
		return target;
	}
	
	/**
	 * ��ȡĿ�귽������
	 * */
	public String getTargetMethod(){
		return targetMethod;
	}
	
	
	/**
	 * ��ȡĿ��������
	 * */
	public String getTargetClass(){
		return targetClass;
	}
	
	/**
	 * ��ȡ����Ŀ�귽����Monitor����
	 * */
	public String getMonitorName(){
		return this.variableList.get(this.getTargetClass());
	}
	
	
	/**
	 * �����ĸ��ڵ�
	 * */
	@Override
	public R visit(MethodDeclaration n, A arg) {		
		if(enterTarget(n)){
			//��ʼ��
			doProcess = true;			
		}
		R r = super.visit(n, arg);
		doProcess = false;		
		return r;
	}
	
	protected boolean enterTarget(MethodDeclaration n){
		return target.equals(this.namePosition.getCurrentName() + ConstText.DOT + n.getName());
		
	}
	
	public boolean isInTargetMethod(){
		return doProcess;
	}
	
	@Override	
	public boolean run(TestData data){
		String tc = data.getStr(JobConst.TARGET_CLASS);
		String tm = data.getStr(JobConst.TARGET_METHOD);
		//Debug.println(tc + "." + tm);
		this.setTargetMethod(tc, tm);
		return super.run(data);		
	}
	
}
