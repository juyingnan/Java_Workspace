package cn.edu.pku.ss.test.cpp.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;

import cn.edu.pku.ss.test.cpp.Const;
import cn.edu.pku.ss.test.cpp.inject.CppEnvironment;
import cn.edu.pku.ss.test.cpp.inject.FunctionToMethod;
import cn.edu.pku.ss.test.cpp.jobs.visitors.CppParser;
import cn.edu.pku.ss.test.cpp.jobs.visitors.FunctionVisitor;
import cn.edu.pku.ss.test.cpp.jobs.visitors.SourceVisitor;
import cn.edu.pku.ss.test.data.TestData;
import cn.edu.pku.ss.test.jobs.JobConst;
import cn.edu.pku.ss.test.jobs.compilers.Compiler;
import cn.edu.pku.ss.test.jobs.compilers.CppCompiler;
import cn.edu.pku.ss.test.jobs.compilers.JavaCompiler;
import cn.edu.pku.ss.test.util.Log;
import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;
import cn.edu.pku.ss.test.util.Tools;

public class JNICompiler extends Compiler {

	private TestData data = null;
	
	public boolean compileConnector(String template, CppEnvironment cppEnv, 
			String libName, ArrayList<CPPASTFunctionDefinition> funcArr, String stumpedCode){		
		
		//第一步根据 模板生成一个java文件
		String code = template.replace("$Name$", cppEnv.getMonitorClassName());
		code = code.replace("$LibName$", libName);
		//抽取原函数声明，转换成Java声明，并且记录原函数的原型声明
		ArrayList<String> funcDeclars = new ArrayList<String>();

		String methods = "";
		for(int i=0;i< funcArr.size();i++){
			//Java的Wrapper里面的方法声明
			methods += FunctionToMethod.convert(funcArr.get(i)) + ";\r\n    ";
			//C++ lib里面源码中的方法声明
			FunctionVisitor sv = new FunctionVisitor();
			CPPASTFunctionDefinition cppDefi = funcArr.get(i);
			cppDefi.accept(sv);
			funcDeclars.add(sv.getSource());
			//Debug.println("xxx" + sv.getSource());
		}
		
		code = code.replace("$Methods$", methods);
		//写入零时目录
		String file = getSrc() + cppEnv.getMonitorClassName() + ".java";
		if(!IOUtil.write(file, code)){
			if(data!=null){
				data.getResult().addReport("Output wrapper failed!");
			}
		
			return false;
		}
		
		
		//第二步 编译这个Java文件		
		if(!JavaCompiler.compile(JavaCompiler.getClassPath(), getBin(), file,  getBin() + "warn.txt")){
			if(data!=null){
				data.getResult().addReport("Java wrapper compile failed!");
			}
			return false;
		}
		
		//String classFile = getBin() ;
		
		//第三步，调用javah 生成相应的头文件
		String[] args = new String[]{ "-classpath", getBin(),
				"-d", getSrc(), "-jni", cppEnv.getMonitorClassName()
		};
		com.sun.tools.javah.Main.main(args);
		
		
		//拷贝编译所需的头文件
		IOUtil.copyDir(PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMPLATE_PATH) + Const.INCLUDE_PATH, getSrc());
				
		
		//第三步，解析templateName.h
		File header = new File(getSrc()+ cppEnv.getMonitorClassName() + ".h");
		if(!header.exists()){
			if(data!=null){
				data.getResult().addReport("Could not find the header file :" + header.toString());				
			}
			return false;
		}
		
		//第三步遍历头文件
		FunctionVisitor funcVisitorHeader = new FunctionVisitor();
		//准备新Source文件
		String newSource = "#include \"" + cppEnv.getMonitorClassName() + ".h\"\r\n";
		newSource+=stumpedCode;

		CppParser.parse(header).accept(funcVisitorHeader);
		//设定相应的目标函数名称
		ArrayList<CPPASTFunctionDeclarator> list = funcVisitorHeader.getFuncDeclarators();
		for(int i=0;i< list.size();i++){
			//生成新的声明
			FunctionVisitor funcNew = new FunctionVisitor(cppEnv);
			funcNew.getFuncTargets().put(funcArr.get(i), list.get(i));
			funcArr.get(i).accept(funcNew);
			String newFuncDeclar = funcNew.getSource();
			Log.println("Old: " + funcDeclars.get(i));
			Log.println("New: " + newFuncDeclar);
			Log.println(newSource.indexOf(funcDeclars.get(i)));
			newSource = newSource.replace( funcDeclars.get(i), newFuncDeclar);			
		}
		
	
		//输出源码			
		String cpp = getSrc() + cppEnv.getMonitorClassName() + ".cpp";
		IOUtil.write(cpp, newSource);
		
		Log.println(cpp);
		Log.println(newSource);
		
		//编译C++代码
		String dll = getBin() + libName + ".dll";
		
		if(!CppCompiler.compileDll(dll + " " + cpp)){
			
			File dllFile = new File(dll);
			if(!dllFile.exists()){	
				if(data!=null){
					data.getResult().addReport("Cpp compile failed!");
				}
				return false;
			}		
		}		
		

		return true;
	}
	
	
	@Override
	public boolean compile(TestData data) {
		
		String template = data.getStr(Const.MONITOR_TEMPLATE);
		if(template.length()==0){
			template = IOUtil.read(PropertiesUtil.getInstance().readValue(PropertiesUtil.TEMPLATE_PATH) 
					+ Const.MONITOR_TEMPLATE_DEF);
			if(template==null || template.length()==0){
				data.getResult().addReport("Monitor template error!");
				return false;
			}
			data.put(Const.MONITOR_TEMPLATE, template);
		}
		//Cpp代码相关的变量名称类型等信息
		CppEnvironment cppEnv = (CppEnvironment) data.get(Const.CPP_ENVIRONMENT);
		
		String libName = data.getStr(Const.LIB_NAME);
		if(libName.length()==0){
			libName = Const.LIB_PEFIX + Tools.RANDOM.nextInt(Integer.MAX_VALUE);
			 data.put(Const.LIB_NAME, libName);
		}
		
		String stumpedCode = data.getStr(Const.SOURCE_WITH_MONITOR);
		if(stumpedCode.length()==0){
			data.getResult().addReport("Stumped Code error!");
			return false;
		}
		
		CPPASTFunctionDefinition funcDefi = (CPPASTFunctionDefinition) data.get(Const.TARGET_FUNCTION);
		if(funcDefi!=null){
			ArrayList<CPPASTFunctionDefinition> funcArr = new ArrayList<CPPASTFunctionDefinition>();
			funcArr.add(funcDefi);	
			this.data = data;
			if(this.compileConnector(template, cppEnv, libName, funcArr, stumpedCode)){
				data.put(JobConst.TARGET_CLASS, cppEnv.getMonitorClassName());
				data.put(JobConst.TARGET_METHOD, funcDefi.getDeclarator().getName().toString());
				
				String libRuntimePath = data.getStr(Const.LIB_RUNTIME_PATH);
				if(libRuntimePath.length()==0){
					libRuntimePath = PropertiesUtil.getInstance().readValue(PropertiesUtil.TESTING_PATH);
					if(libRuntimePath.length()==0){
						libRuntimePath = Const.LIB_RUNTIME_PATH_DEF;
					}
					data.put(Const.LIB_RUNTIME_PATH, libRuntimePath);
				}
				Log.println("Src: " + getBin() + libName + ".dll");
				Log.println("Target: " + libRuntimePath + libName + ".dll");
				if(IOUtil.copy(getBin() + libName + ".dll", libRuntimePath + libName + ".dll")){
					return true;
				} else{
					data.getResult().addReport("Copy runtime lib failed!");
					return false;
				}
			}else {
				data.getResult().addReport("JNICompiler failed!");
				return false;
			}
		} else {
			data.getResult().addReport("Function list error!");
			return false;
		}
		
	}

	
//	E:\Programs\Java\workspace\JCC\src>javah
//	用法：javah [选项] <类>
//
//	其中 [选项] 包括：
//
//	        -help                 输出此帮助消息并退出
//	        -classpath <路径>     用于装入类的路径
//	        -bootclasspath <路径> 用于装入引导类的路径
//	        -d <目录>             输出目录
//	        -o <文件>             输出文件（只能使用 -d 或 -o 中的一个）
//	        -jni                  生成 JNI样式的头文件（默认）
//	        -version              输出版本信息
//	        -verbose              启用详细输出
//	        -force                始终写入输出文件
//
//	使用全限定名称指定 <类>（例
//	如，java.lang.Object）。
	
}
