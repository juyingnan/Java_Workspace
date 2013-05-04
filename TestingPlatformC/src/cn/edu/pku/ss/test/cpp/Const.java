package cn.edu.pku.ss.test.cpp;

public class Const {

	/**
	 * JNI转换C/C++ -> Java调用的固定参数 1,指示java Environment
	 * */
	public final static String FUNC_ENV_DEF = "jni_Env_jcctesting";
	/**
	 * JNI转换C/C++ -> Java调用的固定参数 2,指示调用的java对象
	 * */
	public final static String FUNC_OBJ_DEF = "jni_Obj_jcctesting";
	public final static String FUNC_ENV = "FunctionEnvironmentVariable";
	public final static String FUNC_OBJ = "FunctionMonitorVariable";
	public final static String FUNC_MONITOR_CLASS = "FunctionMonitorClass";
	public final static String FUNC_MONITOR_CLASS_DEF = "Monitor";
	public final static String FUNC_MONITOR_CLASS_ID = "FunctionMonitorClassID";
	public final static String FUNC_MONITOR_CLASS_ID_DEF = "monitor_class_";
	public final static String FUNC_VISIT_METHOD_ID = "FunctionVisitMethodID";
	public final static String FUNC_VISIT_METHOD_ID_DEF = "visit_method_";
	public final static String FUNC_VISIT_METHOD_NAME = "FunctionVisitMethodName";
	public final static String FUNC_VISIT_METHOD_NAME_DEF = "visit";
	public final static String FUNC_VISIT_METHOD_SIGNATURE = "FunctionVisitMethodSignature";
	public final static String FUNC_VISIT_METHOD_SIGNATURE_DEF = "(I)Z";
	public final static String FUNC_METHOD_CALLED = "FunctionMethodCalled";
	public final static String FUNC_METHOD_CALLED_DEF = "CallBooleanMethod";

	public final static String CPP_ENVIRONMENT = "CppEnvironment";
	
	public final static String SOURCE_ORGINAL = "SourceOriginal";
	public final static String SOURCE_WITH_MONITOR = "SourceWithMonitor";
	
	/**
	 * 目标方法抽象语法树节点
	 * */
	public final static String TARGET_FUNCTION = "TargetFunction";
	/**
	 * 目标方法名称
	 * */
	public final static String TARGET_FUNCTION_NAME = "TargetFunctionName";
	
	public final static String FUNCTION_LIST = "FunctionList";
	public final static String LIB_NAME = "LibName";
	public final static String LIB_PEFIX = "lib_jcctesting_";
	public final static String LIB_RUNTIME_PATH = "LibRuntimePath";
	public final static String LIB_RUNTIME_PATH_DEF = "c:\\Temp\\Testing\\";
	//public final static String LIB_PATH = "LibPath";
	
	public final static String MONITOR_TEMPLATE = "MonitorTemplate";
	public final static String MONITOR_TEMPLATE_DEF = "CppMonitorTemplateID.java";
	
	public final static String INCLUDE_PATH = "include";
	//public final static String MONITOR_NAME = "MonitorName";
	//public final static String MONITOR_NAME_PEFIX = "JcctestingMonitor";
	
	public final static String CPP_MEMEBER = "->";
}
