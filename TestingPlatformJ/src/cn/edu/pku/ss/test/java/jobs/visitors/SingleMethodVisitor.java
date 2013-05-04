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
	 * 目标方法
	 * */
	private String targetMethod;
	
	private String targetClass;
	
	private String target;
	
	/**
	 * 是否处于处理状态
	 * */
	private boolean doProcess;
	
	public SingleMethodVisitor(){
		super();
		doProcess = false;
	}
	
	
	/**
	 * 设置目标方法名称
	 * */
	public void setTargetMethod(String targetClass, String targetMethod) {
		super.interestes.add(targetClass);
		this.targetClass = targetClass;
		this.targetMethod = targetMethod;
		this.target = targetClass + ConstText.DOT + targetMethod;
	}

	/**
	 * 获取目标类和方法名称
	 * */
	public String getTarget() {
		return target;
	}
	
	/**
	 * 获取目标方法名称
	 * */
	public String getTargetMethod(){
		return targetMethod;
	}
	
	
	/**
	 * 获取目标类名称
	 * */
	public String getTargetClass(){
		return targetClass;
	}
	
	/**
	 * 获取监视目标方法的Monitor名称
	 * */
	public String getMonitorName(){
		return this.variableList.get(this.getTargetClass());
	}
	
	
	/**
	 * 方法的根节点
	 * */
	@Override
	public R visit(MethodDeclaration n, A arg) {		
		if(enterTarget(n)){
			//初始化
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
