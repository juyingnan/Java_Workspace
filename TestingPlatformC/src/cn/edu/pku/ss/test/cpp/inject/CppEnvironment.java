package cn.edu.pku.ss.test.cpp.inject;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.util.Tools;

public class CppEnvironment {
	
	private String monitorClassName = Const.FUNC_MONITOR_CLASS_DEF;
	private String envId = Const.FUNC_ENV_DEF;
	private String monitorClassId = Const.FUNC_MONITOR_CLASS_DEF;
	private String visitMethodId = Const.FUNC_VISIT_METHOD_ID_DEF;
	private String visitMethodName = Const.FUNC_VISIT_METHOD_NAME_DEF;
	private String visitMethodSig = Const.FUNC_VISIT_METHOD_SIGNATURE_DEF;
	private String methodCalled = Const.FUNC_METHOD_CALLED_DEF;
	private String monitorObject = Const.FUNC_OBJ_DEF;
	
	
	public static CppEnvironment getDefault(){
		return new CppEnvironment();
	}
	
	public static CppEnvironment getRandom(){
		CppEnvironment cppEnv = new CppEnvironment();
		//¶ÁÈ¡c++µ÷ÓÃJava
		int id = Tools.RANDOM.nextInt(Integer.MAX_VALUE);
		cppEnv.setEnvId(Const.FUNC_ENV_DEF);
		cppEnv.setMonitorClassName(Const.FUNC_MONITOR_CLASS_DEF + id);
		cppEnv.setMonitorClassId(Const.FUNC_MONITOR_CLASS_ID_DEF + id);
		cppEnv.setVisitMethodName(Const.FUNC_VISIT_METHOD_NAME_DEF);
		cppEnv.setVisitMethodId(Const.FUNC_VISIT_METHOD_ID_DEF + id);
		cppEnv.setVisitMethodSig(Const.FUNC_VISIT_METHOD_SIGNATURE_DEF);
		cppEnv.setMethodCalled(Const.FUNC_METHOD_CALLED_DEF);
		cppEnv.setMonitorObject(Const.FUNC_OBJ_DEF);
		return cppEnv;
	}
	
	
	public String getMonitorClassName() {
		return monitorClassName;
	}
	public void setMonitorClassName(String monitorClassName) {
		this.monitorClassName = monitorClassName;
	}
	public String getEnvId() {
		return envId;
	}
	public void setEnvId(String envId) {
		this.envId = envId;
	}
	public String getMonitorClassId() {
		return monitorClassId;
	}
	public void setMonitorClassId(String monitorClassId) {
		this.monitorClassId = monitorClassId;
	}
	public String getVisitMethodId() {
		return visitMethodId;
	}
	public void setVisitMethodId(String visitMethodId) {
		this.visitMethodId = visitMethodId;
	}
	public String getVisitMethodName() {
		return visitMethodName;
	}
	public void setVisitMethodName(String visitMethodName) {
		this.visitMethodName = visitMethodName;
	}
	public String getVisitMethodSig() {
		return visitMethodSig;
	}
	public void setVisitMethodSig(String visitMethodSig) {
		this.visitMethodSig = visitMethodSig;
	}
	public String getMethodCalled() {
		return methodCalled;
	}
	public void setMethodCalled(String methodCalled) {
		this.methodCalled = methodCalled;
	}
	public void setMonitorObject(String monitorObject) {
		this.monitorObject = monitorObject;
	}
	public String getMonitorObject() {
		return monitorObject;
	}
	
}
