package cn.edu.pku.ss.test.cpp.jobs.visitors;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.inject.CppEnvironment;
import cn.edu.pku.ss.test.util.Log;

public class FunctionVisitor extends SourceVisitor{
	
	private ArrayList<CPPASTFunctionDeclarator> funcDeclarators = new ArrayList<CPPASTFunctionDeclarator>();	

	private Hashtable<CPPASTFunctionDefinition, CPPASTFunctionDeclarator> funcTargets 
					= new Hashtable<CPPASTFunctionDefinition , CPPASTFunctionDeclarator>();
	private static Hashtable<String, String> JniTypes = new Hashtable<String, String>();
	
	private CppEnvironment cppEnv = new CppEnvironment();
	
	static{
		//TODO：有待完善
		JniTypes.put("int", "jint");
		JniTypes.put("long", "jlong");
		JniTypes.put("short", "jshort");
		JniTypes.put("byte", "jbyte");
		JniTypes.put("float", "float");
		JniTypes.put("double", "jdouble");
		JniTypes.put("char", "jchar");
		JniTypes.put("boolean", "jbooleanArray");
		JniTypes.put("String", "jstring");		
		JniTypes.put("Object", "jobject");
		JniTypes.put("int[]", "jintArray");
		JniTypes.put("long[]", "jlongArray");
		JniTypes.put("short[]", "jshortArray");
		JniTypes.put("byte[]", "jbyteArray");
		JniTypes.put("float[]", "jfloatArray");
		JniTypes.put("double[]", "jdoubleArray");
		JniTypes.put("char[]", "jcharArray");
		JniTypes.put("Object[]", "jobjectArray");
		
	}
	
	public FunctionVisitor(){
		
	}
	
	public FunctionVisitor(CppEnvironment cppEnv){
		this.setCppEnv(cppEnv);
	}
	
	@Override
	public int visit(CPPASTFunctionDefinition n) {
		String name = n.getDeclarator().getName().toString();
		if(funcTargets.containsKey(n)){			
			setFunctionDeclarator(n,funcTargets.get(n));
			//n.getDeclSpecifier().accept(this);
			//((CPPASTSimpleDeclaration)funcTarget.get(name)).accept(this);
			//不输出Body
			//n.getBody().accept(this);			
		}
		else{
			//只输出声明信息
			n.getDeclSpecifier().accept(this);
			printer.print(" ");		
			n.getDeclarator().accept(this);	
		}
		
		return PROCESS_SKIP;
	}
	
	protected void setFunctionDeclarator(CPPASTFunctionDefinition oldFunc, CPPASTFunctionDeclarator newFuncDec){		
		printer.print("JNIEXPORT ");
		SourceVisitor sv = new SourceVisitor();
		oldFunc.getDeclSpecifier().accept(sv);
		String jtype = sv.getSource().trim();
		
		//TODO：有待完善
		if(JniTypes.containsKey(jtype)){
			printer.print(JniTypes.get(jtype).toString() + " ");
		}else{
			Log.println(jtype);
			printer.print(JniTypes.get("Object").toString() + " ");
		}
		
		printer.print("JNICALL ");
		//新的函数名称
		printer.print(newFuncDec.getName().toString());
		//固定参数
		printer.print("(");
		printer.print("JNIEnv * " + cppEnv.getEnvId() + ", ");
		printer.print("jobject " + cppEnv.getMonitorObject());
		//类型转换到Cpp
		CPPASTFunctionDeclarator oldFuncDec = (CPPASTFunctionDeclarator)oldFunc.getDeclarator();
		if(newFuncDec.getParameters().length>2){
			for(int i=0;i < oldFuncDec.getParameters().length; i++){
				printer.print(", ");
				newFuncDec.getParameters()[i+2].getDeclSpecifier().accept(this);	
				printer.print(" ");
				oldFuncDec.getParameters()[i].getDeclarator().accept(this);				
			}
		}
		printer.print(")");
		
		
	}


	@Override
	public int visit(CPPASTFunctionDeclarator n) {
		funcDeclarators.add(n);
		return super.visit(n);

	}

	
	public ArrayList<CPPASTFunctionDeclarator> getFuncDeclarators() {
		return funcDeclarators;
	}


	public void setFuncDeclarators(
			ArrayList<CPPASTFunctionDeclarator> funcDeclarators) {
		this.funcDeclarators = funcDeclarators;
	}

	
	

	public Hashtable<CPPASTFunctionDefinition, CPPASTFunctionDeclarator> getFuncTargets() {
		return funcTargets;
	}

	public void setFuncTargets(
			Hashtable<CPPASTFunctionDefinition, CPPASTFunctionDeclarator> funcTargets) {
		this.funcTargets = funcTargets;
	}

	public static void setJniTypes(Hashtable<String, String> jniTypes) {
		JniTypes = jniTypes;
	}

	public static Hashtable<String, String> getJniTypes() {
		return JniTypes;
	}

	public void setCppEnv(CppEnvironment cppEnv) {
		this.cppEnv = cppEnv;
	}

	public CppEnvironment getCppEnv() {
		return cppEnv;
	}
	
		
}
