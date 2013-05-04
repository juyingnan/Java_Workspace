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

	
	// ���ͺͷ����б�������ͬʱ���¼
	protected ArrayList<CPPASTFunctionDefinition> functionList = new ArrayList<CPPASTFunctionDefinition> ();
	// Ҫ��׮�������б�
	protected ArrayList<String> interestes;
	// ��׮ʱÿ����ʹ�ò�ͬ�ı���,���� -- ������
    
	//protected Hashtable<String, String> variableList;
	//protected Hashtable<String, String> constructorInfo;
	// ��׮�������ͣ�����Import��Ϣ��StumpManager��������
	protected StumpDeclarationType stumpType;

	private String functionMonitor = "monitor_mid_";
	//����Ķ����ͷ�ļ�
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
	 * ��ʼ��������ÿ�α������ڵ�֮ǰ��Ҫ����һ�γ�ʼ������
	 * */
	public void initialize() {
	
		stumpList = new StumpTable();
		//variableList = new Hashtable<String, String>();
		//constructorInfo = new Hashtable<String, String>();
		this.functionMonitor += Tools.RANDOM.nextInt(Integer.MAX_VALUE);  
	}

	/**
	 * ע��Ҫ��׮������
	 * */
	public boolean register(String functionName) {
		if (!interestes.contains(functionName)) {
			interestes.add(functionName);
			return true;
		} else
			return false;

	}

	/**
	 * ���Ҫ��׮������
	 * */
	public boolean unregister(String className) {
		return interestes.remove(className);
	}

	/**
	 * ���Ҫ��׮������
	 * */
	public void clearRegistered() {
		interestes.clear();
		//variableList.clear();
	}

	/**
	 * ��÷��������б�
	 * */
	public ArrayList<CPPASTFunctionDefinition> getFunctionList() {
		return functionList;
	}

	/**
	 * ���ò�׮��Ϣ
	 * */
	public StumpDeclarationType getStumpType() {
		return stumpType;
	}

	/**
	 * ��ȡStump�б�
	 * */
	public StumpTable getStumps() {
		return stumpList;
	}

	/**
	 * ��ȡ��׮�ı����б�
	 * */
	//public Hashtable<String, String> getVariableList() {
	//	return variableList;
	//}

	public Stump create(Object node, Stump stump) {
		if (stump != null) {
			// ����׮����ؽڵ�
			if (node != null)
				stump.setNode(node);
			// ���÷���
			//stump.setCatalog(getCurrentFunction);

			try {
				// ����׮�б��������׮���б��
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

