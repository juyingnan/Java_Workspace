package cn.edu.pku.ss.test.cpp.jobs.visitors;


import java.util.ArrayList;
import java.util.Hashtable;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.inject.IStumpManager;
import cn.edu.pku.ss.test.inject.Stump;
import cn.edu.pku.ss.test.inject.StumpDeclarationType;
import cn.edu.pku.ss.test.inject.StumpTable;
import cn.edu.pku.ss.test.util.Tools;


public class StumpVisitor extends SourceVisitor implements IStumpManager {

	protected StumpTable stumpList = new StumpTable();

	
	// 类型和方法列表，遍历的同时会记录
	protected ArrayList<CPPASTFunctionDefinition> functionList = new ArrayList<CPPASTFunctionDefinition> ();
	// 要插桩的类名列表
	protected ArrayList<String> interestes;
	// 插桩时每个类使用不同的变量,类名 -- 变量名
    
	//protected Hashtable<String, String> variableList;
	//protected Hashtable<String, String> constructorInfo;
	// 插桩声明类型，包括Import信息和StumpManager类型声明
	protected StumpDeclarationType stumpType;

	private String functionMonitor = "monitor_mid_";
	//插入的额外的头文件
	protected ArrayList<String> aditionalHeaders = new ArrayList<String>();


	
	public ArrayList<String> getAditionalHeaders() {
		return aditionalHeaders;
	}

	public void setAditionalHeaders(ArrayList<String> aditionalHeaders) {
		this.aditionalHeaders = aditionalHeaders;
	}

	public StumpVisitor() {
		interestes = new ArrayList<String>();
		//variableList = new Hashtable<String, String>();
		stumpType = new StumpDeclarationType();
		initialize();
	}
	
	@Override
	public boolean run(TestData data) {
	
		if(super.run(data)){
			data.put(Const.FUNCTION_LIST, functionList);
			return true;
		} else {
			return false;
		}
		
	}

	/**
	 * 初始化操作，每次遍历根节点之前都要进行一次初始化操作
	 * */
	public void initialize() {
	
		stumpList = new StumpTable();
		//variableList = new Hashtable<String, String>();
		//constructorInfo = new Hashtable<String, String>();
		this.functionMonitor += Tools.RANDOM.nextInt(Integer.MAX_VALUE);  
	}

	/**
	 * 注册要插桩的类名
	 * */
	public boolean register(String functionName) {
		if (!interestes.contains(functionName)) {
			interestes.add(functionName);
			return true;
		} else
			return false;

	}

	/**
	 * 解除要插桩的类名
	 * */
	public boolean unregister(String className) {
		return interestes.remove(className);
	}

	/**
	 * 清空要插桩的类名
	 * */
	public void clearRegistered() {
		interestes.clear();
		//variableList.clear();
	}

	/**
	 * 获得方法类型列表
	 * */
	public ArrayList<CPPASTFunctionDefinition> getFunctionList() {
		return functionList;
	}

	/**
	 * 设置插桩信息
	 * */
	public StumpDeclarationType getStumpType() {
		return stumpType;
	}

	/**
	 * 获取Stump列表
	 * */
	public StumpTable getStumps() {
		return stumpList;
	}

	/**
	 * 获取插桩的变量列表
	 * */
	//public Hashtable<String, String> getVariableList() {
	//	return variableList;
	//}

	public Stump create(Object node, Stump stump) {
		if (stump != null) {
			// 设置桩的相关节点
			if (node != null)
				stump.setNode(node);
			// 设置分类
			//stump.setCatalog(getCurrentFunction);

			try {
				// 加入桩列表，并对这个桩进行编号
				stumpList.put(this.getFunction(), stump);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return stump;
	}

	protected String getFunction() {		
		return functionMonitor;
	}

	//public String getStumpCode(Stump stump) {
	//	stump.setFlushed(true);
		//return variableList.get(getFunction()) + Const.CPP_MEMEBER + stump.getStumpCode();
	//}
	
	public String getStumpCode(Stump stump) {
		stump.setFlushed(true);
		return getFunction() + Const.CPP_MEMEBER + stump.getMethodStumpCode();
	}
		
	@Override
	public void visitHeader(IASTTranslationUnit n){
		super.visitHeader(n);
		
		for(int i=0; i< aditionalHeaders.size(); i++){
			printer.println(aditionalHeaders.get(i));	
		}
		//printer.printLn();
		//printer.printLn("#include <jni.h>");	
		//printer.printLn("//Monitor variable" );
		//printer.printLn("JNIEnv * " + Const.FUNC_PARAM_ENV );
	
	}
	@Override
	public int visit(CPPASTFunctionDefinition n) {
		functionList.add(n);
		return super.visit(n);

	}

	

	
}

