package cn.edu.pku.ss.test.cpp.jobs.visitors;

import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.inject.CppEnvironment;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.util.Tools;

public class SingleFunctionVisitor extends StumpVisitor {

	private String targetFunctionName;
	private CPPASTFunctionDefinition targetFunction;
	private boolean isInTargetFunction;
	private boolean generateInjectCode = false;

	private CppEnvironment cppEnv = new CppEnvironment();
	
	public boolean run(TestData data){
		//处理待测目标函数名称
		this.setTargetFunctionName(data.getStr(Const.TARGET_FUNCTION_NAME));
		if(this.targetFunctionName.length()==0){
			data.getResult().addReport("Target function name empty!");
			return false;
		}
		
		if(data.containsArg(Const.CPP_ENVIRONMENT)){
			cppEnv = (CppEnvironment) data.get(Const.CPP_ENVIRONMENT);
		} else {
			//读取c++调用Java
			int id = Tools.RANDOM.nextInt(Integer.MAX_VALUE);
			cppEnv.setEnvId(data.getStr(Const.FUNC_ENV, Const.FUNC_ENV_DEF));
			cppEnv.setMonitorObject(data.getStr(Const.FUNC_OBJ, Const.FUNC_OBJ_DEF));
			cppEnv.setMonitorClassName(data.getStr(Const.FUNC_MONITOR_CLASS, Const.FUNC_MONITOR_CLASS_DEF + id));
			cppEnv.setMonitorClassId(data.getStr(Const.FUNC_MONITOR_CLASS_ID, Const.FUNC_MONITOR_CLASS_ID_DEF + id));
			cppEnv.setVisitMethodName(data.getStr(Const.FUNC_VISIT_METHOD_NAME, Const.FUNC_VISIT_METHOD_NAME_DEF));
			cppEnv.setVisitMethodId(data.getStr(Const.FUNC_VISIT_METHOD_ID, Const.FUNC_VISIT_METHOD_ID_DEF + id));
			cppEnv.setVisitMethodSig(data.getStr(Const.FUNC_VISIT_METHOD_SIGNATURE, Const.FUNC_VISIT_METHOD_SIGNATURE_DEF));
			cppEnv.setMethodCalled(data.getStr(Const.FUNC_METHOD_CALLED, Const.FUNC_METHOD_CALLED_DEF));
			data.put(Const.CPP_ENVIRONMENT, cppEnv);
		}
		
		if(super.run(data)){
			data.put(Const.TARGET_FUNCTION, this.getTargetFunction());
			return true;
		} else {
			return false;
		}
	}
	
	
	public void setTargetFunctionName(String targetFunctionName) {
		this.targetFunctionName = targetFunctionName;
		this.register(targetFunctionName);
	}

	public String getTargetFunctionName() {
		return targetFunctionName;
	}
	
	
	@Override
	public int visit(CPPASTFunctionDefinition n) {
		String name = n.getDeclarator().getName().toString();
		if(this.targetFunctionName.equals(name)){
			this.setTargetFunction(n);
			this.setInTargetFunction(true);
		}
		
		return super.visit(n);
		
	}
	
	@Override
	public int leave(CPPASTFunctionDefinition n) {
		String name = n.getDeclarator().getName().toString();
		if(this.targetFunctionName.equals(name)){
			this.setInTargetFunction(false);
		}
		
		return super.visit(n);
		
	}
	@Override
	public int visit(CPPASTCompoundStatement n) {
		printer.println("{");
		if(n.getStatements()!=null){
			 printer.indent();
			 injectConverter();			
			 super.visitStatements(n);
			 printer.unindent();			 
		}	        
	    printer.print("}");
		return PROCESS_SKIP;
	}
	
	protected void injectConverter(){		
		if (this.isInTargetFunction() && !this.generateInjectCode) {
			// 插入c++调用java的对象转换代码
			printer.println("jclass " + cppEnv.getMonitorClassId() + ";");
			printer.println("jmethodID " + cppEnv.getVisitMethodId() + ";");
			printer.println(cppEnv.getMonitorClassId() + " = "
					+ cppEnv.getEnvId() + "->FindClass(\""
					+ cppEnv.getMonitorClassName() + "\");");
			printer.println("if(" + cppEnv.getMonitorClassId() + "==0){");
			printer.indent();
			printer.println("printf(\"class initialize failed!\");");
			printer.unindent();
			printer.println("}");
			printer.println(cppEnv.getVisitMethodId() + " = "
					+ cppEnv.getEnvId() + "->GetMethodID("
					+ cppEnv.getMonitorClassId() + ",\""
					+ cppEnv.getVisitMethodName() + "\",\""
					+ cppEnv.getVisitMethodSig() + "\");");
			printer.println("if(" + cppEnv.getVisitMethodId() + "==0){");
			printer.indent();
			printer.println("printf(\"method initialize failed!\");");
			printer.unindent();
			printer.println("}");

			this.generateInjectCode = true;
		}
	}

	@Override
	public String getStumpCode(Stump stump) {
		stump.setFlushed(true);
		return cppEnv.getEnvId() + Const.CPP_MEMEBER + cppEnv.getMethodCalled()
				+ "(" + cppEnv.getMonitorObject() + ", "
				+ cppEnv.getVisitMethodId() + ", "
				+ stump.getParameterStumpCode() + ")";
	}
	
	protected void setInTargetFunction(boolean isInTargetFunction) {
		this.isInTargetFunction = isInTargetFunction;
	}

	public boolean isInTargetFunction() {
		return isInTargetFunction;
	}


	protected void setTargetFunction(CPPASTFunctionDefinition targetFunction) {
		this.targetFunction = targetFunction;
	}


	public CPPASTFunctionDefinition getTargetFunction() {
		return targetFunction;
	}


	
}
